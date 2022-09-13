package pl.projectarea.project0.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.projectarea.project0.stock.StockApiWrapper;
import pl.projectarea.project0.stock.StockService;


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
        model.addAttribute("stocks", stockService.findStocks());
        return "stocks";
    }
    @GetMapping (path = "/stocks/{ticker}")
    public String getExchangeRate(@PathVariable("ticker") String ticker, Model model) throws IOException {
        StockApiWrapper saw = stockService.findStock(ticker);
        model.addAttribute("stockApiWrapper", saw);
        return "show";
    }
   /* @GetMapping(value = {"/stock"})
    public String getExchangeRates(Model model)  throws IOException{
        model.addAttribute("currencies", stockServiceOld.getCurrenciesList());
        return "stock";
    }


    }
*/
  /* @GetMapping(value = {"/stock"})
   public List<StockApiWrapper> getExchangeRates( )  throws IOException{
       return stockService.findStocks();

   }*/

}
