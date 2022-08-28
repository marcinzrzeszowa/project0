package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRefreshService refreshService;
    private static Map<String,String> stockAvailable = createStockAvailableMap();

    private static Map<String, String> createStockAvailableMap() {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("BTC-USD","BTC/USD");
        myMap.put("ETH-USD","ETH/USD");
        myMap.put("DOT-USD","DOT/USD");
        myMap.put("PLN=X","USD/PLN");
        myMap.put("EURPLN=X","EUR/PLN");
        myMap.put("GBPPLN=X","GBP/PLN");
        myMap.put("CHFPLN=X","CHF/PLN");
        myMap.put("EUR=X","USD/EUR");
        myMap.put("JPY=X","JPY/USD");
        myMap.put("GC=F","Gold/USD");
        myMap.put("SI=F","Silver/USD");
        myMap.put("BZ=F","Oil/USD");
        myMap.put("^GSPC","S&P500");
        return myMap;
    }

    @Autowired
    public StockService(StockRefreshService refreshService) {
        this.refreshService = refreshService;
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
        System.out.println(findPrice(new StockApiWrapper(new Stock("BTC-USD"))));
    }

    public List<StockApiWrapper> findStocks(final List<String> tickers){
        return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
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
