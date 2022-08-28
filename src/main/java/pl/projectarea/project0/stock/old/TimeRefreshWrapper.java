package pl.projectarea.project0.stock.old;

import pl.projectarea.project0.stock.old.StockJson;

import java.time.LocalDateTime;
import java.util.List;

public class TimeRefreshWrapper {

    //private final CurrencyJson currencyJson;
    private StockJson currencyJson;  //stock API
    private LocalDateTime lastUpdate;

    public TimeRefreshWrapper() {
        //this.currencyJson = currencyJson;
        this.lastUpdate = LocalDateTime.now();
    }

    public TimeRefreshWrapper(StockJson currencyJsonElement) {
        this.currencyJson = currencyJsonElement;
        this.lastUpdate = LocalDateTime.now();
    }

    public TimeRefreshWrapper(List<StockJson> currencyJsonList) {

        this.lastUpdate = LocalDateTime.now();
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public StockJson getCurrencyJson() {
        return currencyJson;
    }

    public void setCurrencyJson(StockJson currencyJson) {
        this.currencyJson = currencyJson;
    }
}

