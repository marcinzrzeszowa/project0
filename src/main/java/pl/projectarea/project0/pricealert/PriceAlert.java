package pl.projectarea.project0.pricealert;

import pl.projectarea.project0.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @Column(name = "id")
    private Long id;


    // TODO Zmienic Ticker na klase
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "description")
    @NotNull
    @NotEmpty
    private String description;


    @Column(name = "max_price", length = 10000, precision = 10, scale = 3)
    private BigDecimal maxPrice;

    @Column(name = "min_price", length = 10000, precision = 10, scale = 3)
    private BigDecimal minPrice;

    @Column(name = "is_active")
    private Boolean isActive;

    //cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
    @ManyToOne()
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    public PriceAlert() {
    }
    public PriceAlert(String ticker, String description, BigDecimal maxPrice, BigDecimal minPrice, Boolean isActive, User user) {
        this.ticker = ticker;
        this.description = description;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.isActive = isActive;
        this.user = user;
    }

    public PriceAlert(String ticker, String description, BigDecimal maxPrice, Boolean isActive, User user) {
        this(ticker,description,maxPrice,BigDecimal.ZERO,isActive,user);
    }

    public PriceAlert(String ticker, String description, Boolean isActive, BigDecimal minPrice, User user) {
        this(ticker,description,BigDecimal.ZERO,minPrice,isActive,user);
    }

    public Long getId() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PriceAlert{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", description='" + description + '\'' +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", isActive=" + isActive +
                ", user=" + user +
                '}';
    }
}
