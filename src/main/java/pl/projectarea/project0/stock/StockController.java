package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;

@Controller
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController( StockService stockService ) {
        this.stockService = stockService;
    }

    @GetMapping(value = {"/stocks"})
    public String getAvailableStocks(Model model){
        model.addAttribute("stocksList", stockService.loadAvailableStocksMap());
        model.addAttribute("stocks", stockService.findAllStocks());
        return "stocks";
    }
}
