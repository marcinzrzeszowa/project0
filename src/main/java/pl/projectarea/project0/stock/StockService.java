package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRefreshService refreshService;
    private static final List<String> availableTickersList = Arrays.asList("BTC-USD","ETH-USD","DOT-USD","PLN=X","EURPLN=X","GBPPLN=X","CHFPLN=X","EUR=X","GC=F","SI=F","BZ=F","^GSPC");
    private static final Map<String,String> availableTickersMap = new HashMap<>();
    @Autowired
    public StockService(StockRefreshService refreshService) {
        initAvailableTickersMap();
        this.refreshService = refreshService;
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

    //10 000 - 10sec
    //60 000 - 1min
    //300 000- 5min
    @Scheduled(fixedRate = 60000)
    public void Refresh() throws IOException {

        List<StockApiWrapper> stockApiWrapperList = findStocks();
        for (StockApiWrapper saw: stockApiWrapperList){
            if(!saw.equals(null)){
                Stock s = saw.getStock();
                System.out.println(s.getName() +" = "+s.getQuote().getPrice());
            }
        }
    }

    public Map<String,String> loadAvailableStocks(){
        return availableTickersMap;
    }

  /*  public List<String > loadAvailableStockList(){
        return availableTickersList;
    }*/

    public List<StockApiWrapper> findStocks(){
        return availableTickersList.stream()
                .map(this::findStock)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
}
