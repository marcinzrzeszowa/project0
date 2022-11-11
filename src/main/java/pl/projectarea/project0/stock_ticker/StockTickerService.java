package pl.projectarea.project0.stock_ticker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTickerService {
    private final StockTickerRepository stockTickerRepository;

    @Autowired
    public StockTickerService(StockTickerRepository stockTickerRepository) {
        this.stockTickerRepository = stockTickerRepository;
    }

    public StockTicker findById(Long id){
        return stockTickerRepository.findById(id).stream().findFirst().orElseThrow(()->new RuntimeException("Nie ma takiego symbolu"));
    }

    public List<StockTicker> readAllStockTickers(){
        return stockTickerRepository.findAll();
    }

    public void savePriceAlert(StockTicker stockTicker) {
        stockTickerRepository.save(stockTicker);
    }

    public void deletePriceAlert(Long id){
        if(stockTickerRepository.existsById(id)){
            stockTickerRepository.deleteById(id);
        }
    }
}
