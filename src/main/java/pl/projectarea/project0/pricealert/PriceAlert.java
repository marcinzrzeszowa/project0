package pl.projectarea.project0.pricealert;

import pl.projectarea.project0.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
maxPrice >=0, minPrice = nie ustawione - Alert zostanie zgłoszony po wzroście ceny powyżej wartości maxPrice
maxPrice = nie ustawione, minPrice <=0 - Alert zostanie zgłoszony po spodku ceny poniżej wartości minPrice
maxPrice >=0, minPrice <=0 - Alert zostanie zgłoszony po wzroście ceny powyżej wartości maxPrice lub po spodku ceny poniżej wartości minPrice
*/

@Entity
@Table(name="price_alert")
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ticker;
    private String description;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Boolean isActive;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User userId;


    public PriceAlert() {
    }

    public PriceAlert(String ticker, String description, BigDecimal maxPrice, BigDecimal minPrice, Boolean isActive, User user) {
        this.ticker = ticker;
        this.description = description;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.isActive = isActive;
        this.userId = user;
    }

    public PriceAlert(String ticker, String description, BigDecimal maxPrice, Boolean isActive, User user) {
        this(ticker,description,maxPrice,null,isActive,user);
    }

    public PriceAlert(String ticker, String description, Boolean isActive, BigDecimal minPrice, User user) {
        this(ticker,description,null,minPrice,isActive,user);
    }

    public int getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}
