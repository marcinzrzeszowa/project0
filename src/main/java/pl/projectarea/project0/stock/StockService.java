package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.email.EmailService;
import pl.projectarea.project0.price_alert.PriceAlert;
import pl.projectarea.project0.price_alert.PriceAlertObserver;
import pl.projectarea.project0.price_alert.PriceAlertRepository;
import pl.projectarea.project0.stock_ticker.StockTickerMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService implements PriceAlertObserver {

    private final PriceAlertRepository priceAlertRepository;
    private final EmailService emailService;
    private StockTickerMap stockTickersMap;
    private List<PriceAlert> cachePriceAlertList = new LinkedList<>();
    private boolean isActualAlertList = false;
    private static short loopDelayCounter =0;

    @Autowired
    public StockService(StockTickerMap stockTickersMap, PriceAlertRepository priceAlertRepository, EmailService emailService) {
        this.stockTickersMap = stockTickersMap;
        this.priceAlertRepository = priceAlertRepository;
        this.emailService = emailService;
    }

    public StockApiWrapper findStock(final String ticker){
        try{
            return StockApiWrapper.getInstance(ticker);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,String> loadAvailableStocksMap(){
        return stockTickersMap.getStockTickersMap();
    }

    private static short startLoopDelayCounter(){
        loopDelayCounter++;
        if(loopDelayCounter >= 3){
            loopDelayCounter=3;
        }
        return loopDelayCounter;
    }

    /**
    @Scheduled()
    priceAlertCheckLoop()
    Zadanie: Tworzy pętlę do porównywania ceny waloru z giełdy, z ceną alertu dodaną przez użytkownika, w celu wysłania powiadomienia o zmianie.
    Wartość liczbowa zmiennej counter, odzwierciedla aktualny stan pętli i dzieli ją na etapy.
    counter = 0 - 2 - Przebiegi pętli potrzebny na inicjalizacji alertów cachePriceAlertList bezpośrednio z repozytorium priceAlertRepository, oraz zapisanie ich kopii w cache
    counter >= 2    - Rozpoczęcie porównywania cen rynkowych stocksList (z StockService) z cenami cachePriceAlertList
    counter >= 3    - Załadowanie ceny PriceAlert z cache i nasłuchiwanie zmian ceny z PriceAlertService (isActualAlertList = false spowoduje załadowanie cen z priceAlertRepository)
     **/
    @Scheduled(fixedRate = 10000) //60 000 - 1min
    public void priceAlertCheckLoop() throws IOException {
        short counter = startLoopDelayCounter();
        if(counter<3){
            cachePriceAlertList = readPriceAlertList();
        }else{
            cachePriceAlertList = loadPriceAlertList();
        }
        if(counter>=2){
            showAlertsList(cachePriceAlertList);
            Set<String> tickersSet = getDistinctTickersFromAlertList(cachePriceAlertList);
            List<StockApiWrapper> stocksList = loadStocks(tickersSet);
            comparePricesAndSentNotifications(stocksList,cachePriceAlertList);
        }
    }

    private void comparePricesAndSentNotifications(List<StockApiWrapper> stocksList, List<PriceAlert> priceAlertsList) {

        ArrayList<PriceAlert>listOfPriceAlertsToBeSent = new ArrayList<>();
        Map<Long,BigDecimal> currentPrices = new HashMap<>();

        for(StockApiWrapper stockElement: stocksList){
            for (PriceAlert priceAlert: priceAlertsList){
                if(stockElement.getSymbol().equals(priceAlert.getTicker())){
                    BigDecimal stockPrice = stockElement.getPrice().setScale(3, RoundingMode.CEILING);
                    BigDecimal maxPrice = priceAlert.getMaxPrice();
                    BigDecimal minPrice = priceAlert.getMinPrice();

                    if(maxPrice.compareTo(BigDecimal.ZERO) !=0 && stockPrice.compareTo(maxPrice) == 1 ) {
                        listOfPriceAlertsToBeSent.add(priceAlert);
                        currentPrices.put(priceAlert.getId(),stockElement.getPrice());
                    }else if(minPrice.compareTo(BigDecimal.ZERO) !=0  && stockPrice.compareTo(minPrice) == -1){
                        if(!listOfPriceAlertsToBeSent.contains(priceAlert)){
                            listOfPriceAlertsToBeSent.add(priceAlert);
                            currentPrices.put(priceAlert.getId(),stockElement.getPrice());
                        }
                    }
                }
            }
        }
        sendNotification(listOfPriceAlertsToBeSent, currentPrices);
    }

    private void sendNotification(List<PriceAlert> toBeSendList, Map<Long, BigDecimal> currentPrices){
        if(!toBeSendList.isEmpty()){
            for (PriceAlert element: toBeSendList){
                BigDecimal currentPrice;
                PriceAlert repoAlert = priceAlertRepository.findById(element.getId()).get();
                    if(repoAlert != null){
                        currentPrice = currentPrices.get(repoAlert.getId());
                        repoAlert.setActive(false);
                        priceAlertRepository.save(repoAlert);
                        sendPriceAlertToUser(repoAlert, currentPrice);
                };
            }
        }
        checkPriceAlertsList();
    }

    private void sendPriceAlertToUser(PriceAlert priceAlert, BigDecimal currentPrice){
        emailService.send(priceAlert, currentPrice);
    }

    private Set<String> getDistinctTickersFromAlertList(List<PriceAlert> cachePriceAlertList) {
        Set<String> set = new HashSet<>();
        for(PriceAlert alert: cachePriceAlertList){
            if(!set.contains(alert.getTicker())){
                set.add(alert.getTicker().getSymbol());
            }
        }
        return set;
    }

    private void showAlertsList(List<PriceAlert> list){
        for(PriceAlert alert: list){
                System.out.println(alert.getId() +": "+alert.getTicker() +", max: "+ alert.getMaxPrice() +" ,min: "+ alert.getMinPrice());
        }
    }

    public List<StockApiWrapper> findAllStocks() {
        return stockTickersMap.getStockTickers()
                    .stream()
                    .map(this::findStock)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    private List<StockApiWrapper> loadStocks(Set<String> tickers) {
        return tickers.stream()
                    .map(this::findStock)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    private List<PriceAlert> readPriceAlertList(){
        cachePriceAlertList = priceAlertRepository.findByIsActive(true); //findAll();
        isActualAlertList = true;
        return cachePriceAlertList;
     }

     private List<PriceAlert> loadPriceAlertList() {
         if (!isActualAlertList) {
             return readPriceAlertList();
         }
         return cachePriceAlertList;
     }

    @Override
    public void checkPriceAlertsList() {
        isActualAlertList = false;
    }
}
