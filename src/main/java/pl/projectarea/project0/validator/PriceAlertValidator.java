package pl.projectarea.project0.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.projectarea.project0.pricealert.PriceAlert;
import pl.projectarea.project0.pricealert.PriceAlertService;

@Component
public class PriceAlertValidator implements Validator {
    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertValidator(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PriceAlert.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PriceAlert priceAlert = (PriceAlert) target;

        //Not empty ticker
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"ticker", "error.price-alert.ticker");

        //ticker contains in Tickers Map
        if(priceAlertService.getTicker(priceAlert.getTicker()) != null){
            errors.rejectValue("ticker", "error.price-alert.no-ticker");
        }

    }
}
