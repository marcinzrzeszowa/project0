package pl.projectarea.project0.stock;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockApiWrapper {
    private final Stock stock;
    private LocalDateTime lastAccessed;

    private StockApiWrapper(Stock stock ) {
        this.stock = stock;
        this.lastAccessed = LocalDateTime.now();
    }

    public static StockApiWrapper getInstance(String ticker) throws IOException, InterruptedException {
            Stock stock;
            int tryLoadStockCounter=1;

            do{
                stock = YahooFinance.get(ticker);
                if(stock==null){
                    Thread.currentThread().sleep(3000);
                    tryLoadStockCounter++;
                }
            }while(stock==null & tryLoadStockCounter<3);
        return new StockApiWrapper(stock);
    }

    private Stock getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return getStock().getQuote().getPrice();
    }

    public String getName() {
      return getStock().getName();
    }

    public String getSymbol() {
        return getStock().getSymbol();
    }

    public BigDecimal getPreviousClose() throws IOException {
        return getStock().getQuote().getPreviousClose();
    }
    public BigDecimal getChangeInPercent() throws IOException {
        return getStock().getQuote().getChangeInPercent();
    }
    public BigDecimal getChangeFromAvg200InPercent() throws IOException {
        return getStock().getQuote().getChangeFromAvg200InPercent();
    }
    public BigDecimal getChangeFromAvg50InPercent() throws IOException {
        return getStock().getQuote().getChangeFromAvg50InPercent();
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
}
