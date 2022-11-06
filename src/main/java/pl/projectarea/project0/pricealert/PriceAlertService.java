package pl.projectarea.project0.pricealert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.stock.StockService;
import pl.projectarea.project0.stock.StockTicker;

import java.util.List;
import java.util.Map;

@Service
public class PriceAlertService implements PriceAlertObservable{

    public final PriceAlertRepository priceAlertRepository;
    private final StockService stockService;
    private final StockTicker stockTickers;

    @Autowired
    public PriceAlertService(PriceAlertRepository priceAlertRepository, StockService stockService, StockTicker stockTickers) {
        this.priceAlertRepository = priceAlertRepository;
        this.stockService = stockService;
        this.stockTickers = stockTickers;
    }

    public PriceAlert findById(Long id){
        return priceAlertRepository.findById(id).stream().findFirst().orElseThrow(()->new RuntimeException("Nie ma alertu o takim ID"));
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

    public Map<String,String> getTickers(){
        return stockTickers.getStockTickersMap();
    }

/*    public String getTickerIndex(String value){
        Map<String,String> map = stockTickers.getStockTickersMap();
        String result ="";
        for(Map.Entry entry: map.entrySet()){
            if(entry.getValue().equals(value)) {
                result = entry.getKey().toString();
            }
        }
        return result;
    }*/

    public PriceAlert updatePriceAlert(Long id, PriceAlert alert) {
        PriceAlert priceAlert = priceAlertRepository.findById(id)
                .map(element->{
                    element.setTicker(alert.getTicker());
                    element.setDescription(alert.getDescription());
                    element.setMaxPrice(alert.getMaxPrice());
                    element.setMinPrice(alert.getMinPrice());
                    element.setActive(alert.getActive());
                    return priceAlertRepository.save(element);
                }).orElseThrow(()->new IllegalStateException());

        notifyChangeInPriceAlertsList(stockService);
        return priceAlert;
    }

    @Override
    public void notifyChangeInPriceAlertsList(PriceAlertObserver observer) {
        observer.checkPriceAlertsList();
    }


}
