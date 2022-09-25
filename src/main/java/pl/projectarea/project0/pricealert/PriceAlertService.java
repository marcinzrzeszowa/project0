package pl.projectarea.project0.pricealert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.stock.StockService;

import java.util.List;

@Service
public class PriceAlertService implements PriceAlertObservable{

    public final PriceAlertRepository priceAlertRepository;
    private final StockService stockService;

    @Autowired
    public PriceAlertService(PriceAlertRepository priceAlertRepository, StockService stockService) {
        this.priceAlertRepository = priceAlertRepository;
        this.stockService = stockService;
    }

    public PriceAlert findById(Long id){
        return priceAlertRepository.findById(id).stream().findFirst().orElseThrow(()->new RuntimeException("Nie ma alertu o takim ID"));
    }
    public List<PriceAlert> readAllPriceAlerts(){
        return priceAlertRepository.findAll();
    }

    public void savePriceAlert(PriceAlert priceAlert) {
        priceAlertRepository.save(priceAlert);
        notifyChangeInPriceAlertsList(stockService);
    }

    public void editAlert(){
        notifyChangeInPriceAlertsList(stockService);
    }

    public void deletePriceAlert(Long id){
        if(priceAlertRepository.existsById(id)){
            priceAlertRepository.deleteById(id);
            notifyChangeInPriceAlertsList(stockService);
        }
    }

    @Override
    public void notifyChangeInPriceAlertsList(PriceAlertObserver observer) {
        observer.checkPriceAlertsList();
    }


}
