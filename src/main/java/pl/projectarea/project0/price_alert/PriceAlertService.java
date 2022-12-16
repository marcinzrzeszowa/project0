package pl.projectarea.project0.price_alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.stock.StockService;
import pl.projectarea.project0.stock_ticker.StockTicker;

import pl.projectarea.project0.stock_ticker.StockTickerService;

import java.util.List;

@Service
public class PriceAlertService implements PriceAlertObservable{

    public final PriceAlertRepository priceAlertRepository;
    private final StockService stockService;
    private final StockTickerService stockTickerService;

    @Autowired
    public PriceAlertService(PriceAlertRepository priceAlertRepository, StockService stockService, StockTickerService stockTickerService) {
        this.priceAlertRepository = priceAlertRepository;
        this.stockService = stockService;
        this.stockTickerService = stockTickerService;
    }

    public PriceAlert findById(Long id){
        return priceAlertRepository.findById(id).get();
    }
    public List<PriceAlert> readAllPriceAlerts(){
        return priceAlertRepository.findAll();
    }

    public List<PriceAlert> readUserPriceAlerts(Long id){
        return priceAlertRepository.findByUserId(id);
    }


    public void savePriceAlert(PriceAlert priceAlert) {
        priceAlertRepository.save(priceAlert);
        notifyChangeInPriceAlertsList(stockService);
    }

    public void deletePriceAlert(Long id){
        if(priceAlertRepository.existsById(id)){
            priceAlertRepository.deleteById(id);
            notifyChangeInPriceAlertsList(stockService);
        }
    }

    public List<StockTicker> getTickers(){
        return stockTickerService.getAllStockTickers();
    }

    public PriceAlert updatePriceAlert(Long id, PriceAlert alert) {
        PriceAlert priceAlert = priceAlertRepository.findById(id)
                .map(element->{
                    element.setTicker(alert.getTicker());
                    element.setDescription(alert.getDescription());
                    element.setMaxPrice(alert.getMaxPrice());
                    element.setMinPrice(alert.getMinPrice());
                    element.setActive(alert.getActive());
                    element.setNotifyAlertId((alert.getNotifyAlertId()));
                    element.setToNotify(alert.getToNotify());
                    element.setRelated(alert.getRelated());
                    return priceAlertRepository.save(element);
                }).orElseThrow(()->new IllegalStateException());
        notifyChangeInPriceAlertsList(stockService);
        return priceAlert;
    }

    @Override
    public void notifyChangeInPriceAlertsList(PriceAlertObserver observer) {
        observer.setNotCurrentPriceAlertsList();
    }

}
