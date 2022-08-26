package pl.projectarea.project0.stock;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class StockRefreshService {

    private final Map<TimeRefreshWrapper, Boolean> currenciesToRefresh;
    private static final ScheduledExecutorService timeSchedule = Executors.newScheduledThreadPool(1);
    private static final Duration refreshPeriod = Duration.ofMinutes(5);

    public StockRefreshService() {
        this.currenciesToRefresh = new HashMap<>();
    }

   /* public void runRefreshTimer(){
        timeSchedule.scheduleAtFixedRate(()-> currenciesToRefresh.forEach(
                ((timeWrappedCurrency, value) ->{
                   if(timeWrappedCurrency.getLastUpdate().isBefore(LocalDateTime.now().minus(refreshPeriod))) {
                       System.out.println("Pobieranie aktualnego kursu waluty ");
                       currenciesToRefresh.remove(timeWrappedCurrency);
                       currenciesToRefresh.put(timeWrappedCurrency, true));
                    }

                });)
    }*/

}
