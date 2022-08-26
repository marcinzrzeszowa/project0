package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.util.List;

@Controller
public class StockController {

    private final StockService currencyService;

    @Autowired
    public StockController(StockService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = {"/stock"})
    public String getExchangeRates(Model model)  throws IOException{
        model.addAttribute("currencies", currencyService.getCurrenciesList());
        return "stock";
    }

    @GetMapping (path = "/stock/{ticker}")
    public String getExchangeRate(@PathVariable("ticker") String ticker, Model model) throws IOException {
        List<StockJson> currency = currencyService.getExchangeRates(ticker);
        model.addAttribute("currency", currency);
        return "show";
    }


}
