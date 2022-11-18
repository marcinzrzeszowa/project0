package pl.projectarea.project0.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.projectarea.project0.price_alert.PriceAlert;
import pl.projectarea.project0.price_alert.PriceAlertService;

@Component
public class PriceAlertValidator implements Validator {
    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertValidator(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @Override
    public boolean supports(Class<?> paclass) {
        return PriceAlert.class.equals(paclass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PriceAlert priceAlert = (PriceAlert)target;

        //Not empty ticker
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"ticker", "error.price-alert.ticker");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","error.price-alert.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"maxPrice", "error.price-alert.maxPrice");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"minPrice", "error.price-alert.minPrice");


    }
}
