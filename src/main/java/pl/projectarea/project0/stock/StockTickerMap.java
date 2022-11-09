package pl.projectarea.project0.stock;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StockTickerMap {

    private static final Map<String, String> stockTickerMap = new HashMap();

    public StockTickerMap() {
        stockTickerMap.put("PLN=X","PLN/USD");
        stockTickerMap.put("EURPLN=X","EUR/PLN");
        stockTickerMap.put("GBPPLN=X","GBP/PLN");
        stockTickerMap.put("CHFPLN=X","CHF/PLN");
        stockTickerMap.put("EUR=X","USD/EUR");
        stockTickerMap.put("GC=F","Złoto/USD");
        stockTickerMap.put("SI=F","Srebro/USD");
        stockTickerMap.put("BZ=F","Ropa/USD");
        stockTickerMap.put("^GSPC","S&P500");
        stockTickerMap.put("BTC-USD","BTC/USD");
        stockTickerMap.put("ETH-USD","ETH/USD");
        stockTickerMap.put("DOT-USD","DOT/USD");
    }

    public Map<String, String> getStockTickersMap() {
        return stockTickerMap;
    }

    public Set<String> getStockTickers(){
        return stockTickerMap.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> el: stockTickerMap.entrySet() ){
            sb.append(el.getKey()).append(" = ").append(el.getValue()).append("\n");
        }
        return sb.toString();
    }

}
