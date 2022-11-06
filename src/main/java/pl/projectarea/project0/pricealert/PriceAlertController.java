package pl.projectarea.project0.pricealert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projectarea.project0.user.User;
import pl.projectarea.project0.user.UserService;

import java.security.Principal;
import java.util.Map;

@Controller
public class PriceAlertController {

    private final PriceAlertService priceAlertService;
    private final UserService userService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService, UserService userService) {
        this.priceAlertService = priceAlertService;
        this.userService = userService;
    }

    @GetMapping("/alerts")
    public String showUserAlerts(Model model, Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        if(user.getRole().equals("ROLE_ADMIN")){
            model.addAttribute("alerts", priceAlertService.readAllPriceAlerts());
            return "price_alerts";
        }
        model.addAttribute("alerts", priceAlertService.readUserPriceAlerts(user.getId()));
        return "price_alerts";
    }

    @GetMapping("/alerts/new")
    public String newAlert(Model model, Authentication authentication) {
            User user = userService.findByUsername(authentication.getName());
            PriceAlert priceAlert = new PriceAlert();
            priceAlert.setUser(user);
            model.addAttribute("alertForm", priceAlert);
            model.addAttribute("action", "newAlert");
            model.addAttribute("tickers", priceAlertService.getTickers());
        return "price_alert_new";
    }

    @PostMapping("/alerts/new")
    public String addAlert(@ModelAttribute("alertForm") PriceAlert priceAlert,
                           BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        return "price_alert_new";
    }
        priceAlertService.savePriceAlert(priceAlert);
        return "redirect:/alerts";
    }

    @GetMapping("/alerts/edit/{id}")
    public String editAlert(@PathVariable("id") Long id,
                              Model model){
        PriceAlert alert = priceAlertService.findById(id);
        String tickerIndex = alert.getTicker();
        Map<String,String> tickers = priceAlertService.getTickers();
        if (alert != null){
            model.addAttribute("alertForm", alert);
            model.addAttribute("action",
                    "editAlert");
            model.addAttribute("tickers", tickers);
            model.addAttribute("ticker", tickerIndex);
            return "price_alert";
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
            return "price_alerts";
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
}

