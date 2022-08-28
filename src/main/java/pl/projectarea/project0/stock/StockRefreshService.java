package pl.projectarea.project0.stock;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StockRefreshService {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<StockApiWrapper, Boolean> stockToRefresh;

    public StockRefreshService( ) {
        this.stockToRefresh = new HashMap<>();
        refreshPeriodInMinutes(1);
    }

    public boolean shouldRefresh(final StockApiWrapper stock){
        if(!stockToRefresh.containsKey(stock)){
            stockToRefresh.put(stock, false);
            return true;
        }
        return stockToRefresh.get(stock);
    }

    private void refreshPeriodInMinutes(int refreshPeriod){
        Duration refreshInMinutes = Duration.ofMinutes(refreshPeriod);
        scheduler.scheduleAtFixedRate(()->
                stockToRefresh.forEach((stock, value) ->{
                    if(stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshInMinutes))) {
                        System.out.println("Pobieranie aktualnego kursu waluty co "+ refreshPeriod +" minut: "+ stock.getStock().getSymbol());
                        stockToRefresh.remove(stock);
                        stock.setLastAccessed(LocalDateTime.now());
                        stockToRefresh.put(stock, true);
                    }
                }),0,refreshPeriod,TimeUnit.MINUTES);
    }
}
