package pl.projectarea.project0.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.projectarea.project0.price_alert.PriceAlert;
import pl.projectarea.project0.price_alert.PriceAlertService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.regex.Pattern;

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

        PriceAlert priceAlert = (PriceAlert) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ticker", "error.price-alert.ticker");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.price-alert.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxPrice", "error.price-alert.maxPrice");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minPrice", "error.price-alert.minPrice");


        if (priceAlert.getDescription().length() > 100) {
            errors.rejectValue("description", "error.price-alert.description.length");
        }

        BigDecimal minPrice = priceAlert.getMinPrice();
        BigDecimal maxPrice = priceAlert.getMaxPrice();

        if (isNumber(minPrice) && isNumber(maxPrice)) {

            if (maxPrice.compareTo(new BigDecimal(999999.999)) == 1 ) {
                errors.rejectValue("maxPrice", "error.price-alert.maxPrice.toBig");
            }
            if (maxPrice.compareTo(minPrice) == -1 || maxPrice.compareTo(BigDecimal.ZERO) == -1 ) {
                errors.rejectValue("maxPrice", "error.price-alert.maxPrice.toSmall");
            }
            if (minPrice.compareTo(maxPrice) == 1 || minPrice.compareTo(BigDecimal.ZERO) == -1 ) {
                errors.rejectValue("minPrice", "error.price-alert.minPrice.toBig");
            }
        }
          /* if (priceAlert.getMaxPrice() == null) {
            errors.rejectValue("maxPrice", "error.price-alert.notNumeric");
        }
        if (priceAlert.getMinPrice() == null) {
            errors.rejectValue("minPrice", "error.price-alert.notNumeric");
        }


        if (priceAlert.getMaxPrice() != null && isNumber(priceAlert.getMaxPrice()) == false) {
                errors.rejectValue("maxPrice", "error.price-alert.notNumeric");
        }

        if (priceAlert.getMinPrice() != null && isNumber(priceAlert.getMinPrice()) == false) {
            errors.rejectValue("minPrice", "error.price-alert.notNumeric");
        }*/
    }

    public boolean isNumber(BigDecimal number) {
        if (number == null) {
            return false;
        }
        String strNumber = number.toString();
        Pattern pattern = Pattern.compile("\\d+(\\.?\\d+)?");
        return pattern.matcher(strNumber).matches();
    }
}
