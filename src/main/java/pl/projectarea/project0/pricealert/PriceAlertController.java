package pl.projectarea.project0.pricealert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.projectarea.project0.stock.StockApiWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class PriceAlertController {

    private final PriceAlertRepository priceAlertRepository;

    @Autowired
    public PriceAlertController(PriceAlertRepository priceAlertRepository) {
        this.priceAlertRepository = priceAlertRepository;
    }

    @GetMapping(value = {"/alerts"})
    public String showAllAlerts(Model model){
        model.addAttribute("alerts", priceAlertRepository.findAll());
        return "alerts";
    }

    @GetMapping (path = "/alerts/{id}")
    public String getAlertById(@PathVariable("id") int id, Model model) throws IOException {
        Optional<PriceAlert> pa = priceAlertRepository.findById(id);
        PriceAlert object = pa.stream().findFirst().orElse(null);
        model.addAttribute("alert", object);
        return "alert";
    }
}
