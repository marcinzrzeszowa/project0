package pl.projectarea.project0.pricealert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projectarea.project0.article.Article;

import java.io.IOException;
import java.util.Map;

@Controller
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @GetMapping("/alerts")
    public String showAllAlerts(Model model){
        model.addAttribute("alerts", priceAlertService.readAllPriceAlerts());
        return "alerts";
    }

  /*  @GetMapping ("/alerts/{id}")
    public String getAlertById(@PathVariable("id") Long id, Model model) throws IOException {
        model.addAttribute("alert", priceAlertService.findById(id));
        return "alert";
    }*/

    @GetMapping("/alerts/new")
    public String newAlert(Model model) {
        model.addAttribute("alertForm", new PriceAlert());
        model.addAttribute("action", "newAlert");
        model.addAttribute("tickers", priceAlertService.getTickers());
        return "newalert";
    }

    @PostMapping("/alerts/new")
    public String addAlert(@ModelAttribute("alertForm") PriceAlert priceAlert,
                           Model model,
                           BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        model.addAttribute("action","newAlert");
        return "alert";
    }
        priceAlertService.savePriceAlert(priceAlert);
        return "redirect:/alerts";
    }

    @GetMapping("/alerts/edit/{id}")
    public String editAlert(@PathVariable("id") Long id,
                              Model model){
        PriceAlert alert = priceAlertService.findById(id);
        //Map<String,String> tickers = priceAlertService.getTickers();
        if (alert != null){
            //String ticker = tickers.get(alert.getTicker());
            model.addAttribute("alertForm", alert);
            model.addAttribute("action", "editAlert");
           // model.addAttribute("ticker", ticker);
            return "newalert";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/alerts/edit/{id}")
    public String updateAlert(@PathVariable("id") Long id,
                              @ModelAttribute("alertForm")PriceAlert alert,
                              BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("action", "editAlert");
            return "alerts";
        }
        priceAlertService.updatePriceAlert(id, alert);
        return "redirect:/alerts";
        }

    @GetMapping(value = "/alerts/delete/{id}")
    public String deleteAlert(@PathVariable("id") Long id){
        PriceAlert alert = priceAlertService.findById(id);
        if(alert!=null){
            priceAlertService.deletePriceAlert(id);
        }
        return "redirect:/alerts";
    }


/*    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue="true") boolean state){
        return ResponseEntity.ok(repository.findByDone(state));
    }

 */
}

