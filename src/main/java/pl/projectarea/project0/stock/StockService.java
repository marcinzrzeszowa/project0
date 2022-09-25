package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.StringColor;
import pl.projectarea.project0.pricealert.PriceAlert;
import pl.projectarea.project0.pricealert.PriceAlertObserver;
import pl.projectarea.project0.pricealert.PriceAlertRepository;
import pl.projectarea.project0.pricealert.PriceAlertService;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService implements PriceAlertObserver {

    private final StockRefreshService refreshService;
    private final PriceAlertRepository priceAlertRepository;
    //TODO availableTickersList zamienić na availableTickersMap
    private static final List<String> availableTickersList = Arrays.asList("BTC-USD","ETH-USD","DOT-USD","PLN=X","EURPLN=X","GBPPLN=X","CHFPLN=X","EUR=X","GC=F","SI=F","BZ=F","^GSPC");
    private static final Map<String,String> availableTickersMap = new HashMap<>();

    private List<PriceAlert> cachePriceAlertList = new ArrayList<>();
    private boolean isActualAlertList = false;
    private static short loopDelayCounter =0;  //Opoznienie riceAlertCheckLoop() powoduje ze po drugiej petli wlancza sie ladowanie listy a cache

    @Autowired
    public StockService(StockRefreshService refreshService, PriceAlertRepository priceAlertRepository) {
        this.refreshService = refreshService;
        this.priceAlertRepository = priceAlertRepository;
        initAvailableTickersMap();
    }

    private static void initAvailableTickersMap(){
        availableTickersMap.put("BTC-USD","BTC/USD");
        availableTickersMap.put("ETH-USD","ETH/USD");
        availableTickersMap.put("DOT-USD","DOT/USD");
        availableTickersMap.put("PLN=X","PLN/USD");
        availableTickersMap.put("EURPLN=X","EUR/PLN");
        availableTickersMap.put("GBPPLN=X","GBP/PLN");
        availableTickersMap.put("CHFPLN=X","CHF/PLN");
        availableTickersMap.put("EUR=X","USD/EUR");
        availableTickersMap.put("GC=F","Złoto/USD");
        availableTickersMap.put("SI=F","Srebro/USD");
        availableTickersMap.put("BZ=F","Ropa/USD");
        availableTickersMap.put("^GSPC","S&P500");
    }

    public StockApiWrapper findStock(final String ticker){
        try{
            return new StockApiWrapper(YahooFinance.get(ticker));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,String> loadAvailableStocksMap(){
        return availableTickersMap;
    }

    private static short startLoopDelayCounter(){
        loopDelayCounter++;
        if(loopDelayCounter >= 3){
            loopDelayCounter=3;
        }
        return loopDelayCounter;
    }

    //60 000 - 1min
    @Scheduled(fixedRate = 10000)
    public void priceAlertCheckLoop() throws IOException {
        short counter = startLoopDelayCounter();

        //System.out.println("START isActualAlertList " +isActualAlertList);
        //System.out.println("START startLoopDelayCounter " + counter );

        if(counter<3){
            cachePriceAlertList = readPriceAlertList();
        }else{
            cachePriceAlertList = loadPriceAlertList();
        }

        if(counter>=2){
            if(!cachePriceAlertList.isEmpty()){
                showAlertsList(cachePriceAlertList);
            }else{
                System.out.println("Brak alertów w liście");
            }

            loadStockPrices();

            compareStockPricesWithPriceAlertList();
        }
    }

    private void compareStockPricesWithPriceAlertList() {

        System.out.println(StringColor.ANSI_GREEN+"compareStockPricesWithPriceAlertList() ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");

        System.out.println(StringColor.ANSI_GREEN+"************************************************");
        System.out.println(StringColor.ANSI_RESET);
    }

    private void loadStockPrices() {
        System.out.println(StringColor.ANSI_RED+"loadStockPrices() ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");

        System.out.println(StringColor.ANSI_RED+"************************************************");
        System.out.println(StringColor.ANSI_RED);
    }

    private void showAlertsList(List<PriceAlert> list){
        System.out.println(StringColor.ANSI_CYAN+"PriceAlert ******* "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+" *******");
        for(PriceAlert alert: list){
            System.out.println(alert.getId() +": "+alert.getTicker() +", max: "+ alert.getMaxPrice() +" ,min: "+ alert.getMinPrice());
        }
        System.out.println(StringColor.ANSI_CYAN+"************************************************");
        System.out.println(StringColor.ANSI_RESET);
    }

    public List<StockApiWrapper> findStocks() {
        return availableTickersList.stream()
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


    public BigDecimal findPrice(final StockApiWrapper stock) throws IOException{
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


}
