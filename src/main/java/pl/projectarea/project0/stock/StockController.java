package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.projectarea.project0.stock.old.StockJson;
import pl.projectarea.project0.stock.old.StockServiceOLD;


import java.io.IOException;
import java.util.List;

@Controller
public class StockController {

    private final StockServiceOLD stockService;

    @Autowired
    public StockController(StockServiceOLD stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = {"/stock"})
    public String getExchangeRates(Model model)  throws IOException{
        model.addAttribute("currencies", stockService.getCurrenciesList());
        return "stock";
    }

    @GetMapping (path = "/stock/{ticker}")
    public String getExchangeRate(@PathVariable("ticker") String ticker, Model model) throws IOException {
        List<StockJson> currency = stockService.getExchangeRates(ticker);
        model.addAttribute("currency", currency);
        return "show";
    }


}
