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
        return stockTickerRepository.findById(id).get();
    }

    public List<StockTicker> getAllStockTickers(){
        return stockTickerRepository.findAll();
    }
}
