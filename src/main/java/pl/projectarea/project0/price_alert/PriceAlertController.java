package pl.projectarea.project0.price_alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projectarea.project0.stock_ticker.StockTicker;
import pl.projectarea.project0.stock_ticker.StockTickerService;
import pl.projectarea.project0.user.User;
import pl.projectarea.project0.user.UserService;
import pl.projectarea.project0.validator.PriceAlertValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class PriceAlertController {
    private static final Logger logger = LoggerFactory.getLogger(PriceAlertController.class);
    private final PriceAlertService priceAlertService;
    private final UserService userService;
    private final PriceAlertValidator priceAlertValidator;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService, UserService userService, PriceAlertValidator priceAlertValidator) {
        this.priceAlertService = priceAlertService;
        this.userService = userService;
        this.priceAlertValidator = priceAlertValidator;
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
    public String newAlert( Model model, Authentication authentication) {
            User user = userService.findByUsername(authentication.getName());
            PriceAlert priceAlert = new PriceAlert();
            priceAlert.setUser(user);
            List<StockTicker> tickers = priceAlertService.getTickers();
                model.addAttribute("alertForm", priceAlert);
                model.addAttribute("tickers", tickers);
        return "price_alert_new";
    }

    @PostMapping("/alerts/new")
    public String addAlert(@ModelAttribute("alertForm") PriceAlert priceAlert, BindingResult bindingResult, Model model){
        priceAlertValidator.validate(priceAlert, bindingResult);
            if(bindingResult.hasErrors()){
                logger.error(String.valueOf(bindingResult.getFieldError()));
                List<StockTicker> tickers = priceAlertService.getTickers();
                model.addAttribute("alertForm", priceAlert);
                model.addAttribute("tickers", tickers);
                return "price_alert_new";
            }
            priceAlertService.savePriceAlert(priceAlert);
            logger.debug(String.format("Product with id: %s successfully created.", priceAlert.getId()));
            return "redirect:/alerts";
    }

    @GetMapping("/alerts/edit/{id}")
    public String editAlert(@PathVariable("id") Long id,
                            Model model){
        PriceAlert alert = priceAlertService.findById(id);
        List<StockTicker> tickers = priceAlertService.getTickers();
        if (alert != null){
            model.addAttribute("alertForm", alert);
            model.addAttribute("tickers", tickers);
            return "price_alert";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/alerts/edit/{id}")
    public String updateAlert(@PathVariable("id") Long id,
                              @ModelAttribute("alertForm") @Valid PriceAlert alert,
                              BindingResult bindingResult,
                              Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("action", "editAlert");
            return "price_alert";
        }
        priceAlertService.updatePriceAlert(id, alert);
        return "redirect:/alerts";
        }

    @GetMapping(value = "/alerts/delete/{id}")
    public String deleteAlert(@PathVariable("id") Long id){
        PriceAlert alert = priceAlertService.findById(id);
        if(alert!=null){
            priceAlertService.deletePriceAlert(id);
            return "redirect:/alerts";
        } else {
            return "error/404";
        }
    }
}

