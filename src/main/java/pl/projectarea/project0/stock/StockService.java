package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.StringColor;
import pl.projectarea.project0.pricealert.PriceAlert;
import pl.projectarea.project0.pricealert.PriceAlertObserver;
import pl.projectarea.project0.pricealert.PriceAlertRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService implements PriceAlertObserver {

    private final PriceAlertRepository priceAlertRepository;
    private StockTicker stockTickers;
    private List<PriceAlert> cachePriceAlertList = new LinkedList<>();
    private boolean isActualAlertList = false;
    private static short loopDelayCounter =0;

    @Autowired
    public StockService(StockTicker stockTickers, PriceAlertRepository priceAlertRepository) {
        this.stockTickers = stockTickers;
        this.priceAlertRepository = priceAlertRepository;
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
        return stockTickers.getStockTickersMap();
    }

    private static short startLoopDelayCounter(){
        loopDelayCounter++;
        if(loopDelayCounter >= 3){
            loopDelayCounter=3;
        }
        return loopDelayCounter;
    }

    /* @Scheduled() priceAlertCheckLoop()
     * Co robi:
     *
     * Stany pętli dla wartości zmiennej counter:
     * counter = 0 - 2 - Przebiegi pętli potrzebny na inicjalizacji alertów cachePriceAlertList bezpośrednio z repozytorium priceAlertRepository, oraz
     *                   zapisanie ich kopii w cache;
     * counter >= 2     - Rozpoczęcie porównywania cen rynkowych stocksList (z StockService) z cenami cachePriceAlertList
     * counter >= 3    - Załadowanie ceny PriceAlert z cache nasłuchiwanie zmian ceny (flaga isActualAlertList = false) z PriceAlertService
     * */
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
            compareStocksPricesWithPriceAlertsList(stocksList,cachePriceAlertList);
        }
    }

    private Set<String> getDistinctTickersFromAlertList(List<PriceAlert> cachePriceAlertList) {
       Set<String> set = new HashSet<>();
        for(PriceAlert alert: cachePriceAlertList){
           if(!set.contains(alert.getTicker())){
               set.add(alert.getTicker());
           }
       }
        return set;
    }

    private void compareStocksPricesWithPriceAlertsList(List<StockApiWrapper> stocksList, List<PriceAlert> priceAlertsList) {

        System.out.println(StringColor.ANSI_GREEN+"compareStockPricesWithPriceAlertList() ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");
        // TODO dodac edycje Pricealert w petli zeby zmienila element w bazie a nie tylko w cache
        //TODO Querry z PriceAlert to User na email
        // TODO zmienic strukture danych aby nie wracalo NULL z getName() StockAPIWrapper
        for(StockApiWrapper stockElement: stocksList){
            for (PriceAlert priceAlert: priceAlertsList){
                if(stockElement.getSymbol().equals(priceAlert.getTicker())){
                    //sprawdzenie ceny maksymalnej
                    if(stockElement.getPrice().compareTo(priceAlert.getMaxPrice()) >= 0){
                        System.out.println("CENA POWYZEJ MAX ! ");
                        System.out.println(priceAlert.toString());
                    }else if(stockElement.getPrice().compareTo(priceAlert.getMaxPrice()) < 0){
                        // cena rynkowa niższa od ceny MAX alertu
                    }
                    //sprawdzenie ceny minimalnej
                    if(stockElement.getPrice().compareTo(priceAlert.getMinPrice()) >= 0){
                        // cena rynkowa wyższa od ceny MIN alertu
                    }else if(stockElement.getPrice().compareTo(priceAlert.getMinPrice()) < 0){
                        /* cena rynkowa jest nizsza od ceny MIN alertu
                         *
                         * wyslac info do obserwatorow ceny
                         *
                         * ustawic alert jako nie aktywny
                         *
                         * */
                        System.out.println("CENA PONIZEJ MIN ! ");
                        System.out.println(priceAlert.toString());
                        priceAlert.setActive(false);
                    }
                }
            }
        }
        System.out.println(StringColor.ANSI_GREEN+"************************************************");
        System.out.println(StringColor.ANSI_RESET);
    }

    private List<StockApiWrapper> loadStocks(Set<String> tickersSet) {
        System.out.println(StringColor.ANSI_PURPLE+"loadStockPrices() ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");
        List<StockApiWrapper> list = findStocks(tickersSet);
        for(StockApiWrapper element: list){
            System.out.println(element.getSymbol() + " " + element.getPrice());
        }
        System.out.println(StringColor.ANSI_PURPLE+"************************************************");
        System.out.println(StringColor.ANSI_RESET);
        return list;
    }

    private void showAlertsList(List<PriceAlert> list){
        System.out.println(StringColor.ANSI_CYAN+"PriceAlert ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");
        for(PriceAlert alert: list){
                System.out.println(alert.getId() +": "+alert.getTicker() +", max: "+ alert.getMaxPrice() +" ,min: "+ alert.getMinPrice());
        }
        System.out.println(StringColor.ANSI_CYAN+"************************************************");
        System.out.println(StringColor.ANSI_RESET);
    }

    public List<StockApiWrapper> findAllStocks() {
        return stockTickers.getStockTickers()
                    .stream()
                    .map(this::findStock)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    private List<StockApiWrapper> findStocks(Set<String> tickers) {
        return tickers.stream()
                    .map(this::findStock)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    private List<PriceAlert> readPriceAlertList(){
        cachePriceAlertList = priceAlertRepository.findAll();
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

   /* public BigDecimal findPrice(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();
    }

    public BigDecimal findLastChangeInPercent(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
    }

    public BigDecimal findChangeFrom200AvgInPercent(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200InPercent();
    }
    public BigDecimal findPreviousClose(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPreviousClose();
    }
    public BigDecimal findGetChange(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChange();
    }
    public BigDecimal findDayHigh(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getDayHigh();
    }
    public BigDecimal findDayLow(final StockApiWrapper stock) throws IOException{
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getDayLow();
    }
*/

}
