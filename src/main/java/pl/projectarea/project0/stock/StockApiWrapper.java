package pl.projectarea.project0.stock;

import yahoofinance.Stock;

import java.time.LocalDateTime;
import java.util.List;

public class StockApiWrapper {
    private final Stock stock;
    private LocalDateTime lastAccessed;

    public Stock getStock() {
        return stock;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public StockApiWrapper(Stock stock ) {
        this.stock = stock;
        this.lastAccessed = LocalDateTime.now();
    }

    public StockApiWrapper(Stock stock, LocalDateTime lastAccessed) {
        this.stock = stock;
        this.lastAccessed = lastAccessed;
    }
}
