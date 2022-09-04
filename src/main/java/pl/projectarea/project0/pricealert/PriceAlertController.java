package pl.projectarea.project0.pricealert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceAlertController {

    private final PriceAlertRepository priceAlertRepository;

    @Autowired
    public PriceAlertController(PriceAlertRepository priceAlertRepository) {
        this.priceAlertRepository = priceAlertRepository;
    }

    @GetMapping(value = {"/alerts"}) //ok
    public List<PriceAlert> showAlerts() {
        return priceAlertRepository.findAll();
    }
}
