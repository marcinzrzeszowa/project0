package pl.projectarea.project0.price_alert;

import pl.projectarea.project0.stock_ticker.StockTicker;
import pl.projectarea.project0.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @Column(name = "description")
    @NotBlank(message = "Podaj opis")
    private String description;

    @Column(name = "max_price", length = 100000, precision = 10, scale = 3)
    @Min(value=0, message="Cena maksymalna jest za mała")
    @Max(value=100000, message="Cena maksymalna jest za duża")  //TODO sprawdzic dlugosc jaka ma byc z precyzja, DoDac cena max > cena min validator,
    private BigDecimal maxPrice;

    @Column(name = "min_price", length = 100000, precision = 10, scale = 3)
    @Min(value=0, message="Cena minimalna jest za mała")
    @Max(value=1000000, message="Cena minimalna jest za duża")
    private BigDecimal minPrice;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_related")
    private Boolean isRelated;

    @Column(name = "is_to_notify")
    private Boolean isToNotify;

    @Column(name = "notify_alert_id")
    private Long notifyAlertId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "price_alerts")
    @NotNull
    private StockTicker ticker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public PriceAlert() {
    }

    private PriceAlert(StockTicker ticker, User user, String description, BigDecimal maxPrice, BigDecimal minPrice, Boolean isActive, Boolean isToNotify, Boolean isRelated, Long notifyAlertId) {
        this.description = description;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.isActive = isActive;
        this.isRelated = isRelated;
        this.isToNotify = isToNotify;
        this.notifyAlertId = notifyAlertId;
        this.ticker = ticker;
        this.user = user;
    }

    // PA
    private PriceAlert(StockTicker ticker, User user, String description, BigDecimal maxPrice, BigDecimal minPrice) {
        this(ticker, user, description, maxPrice, minPrice, true, true, false, Long.valueOf(-1));
    }
    public static PriceAlert newObj(StockTicker ticker, User user, String description, BigDecimal maxPrice, BigDecimal minPrice){
        return new PriceAlert(ticker,user, description, maxPrice,  minPrice);
    }

    // PA. Just price MAX
    private PriceAlert(StockTicker ticker, User user, String description, BigDecimal maxPrice) {
        this(ticker, user, description, maxPrice, BigDecimal.ZERO, true, true, false, Long.valueOf(-1));
    }
    public static PriceAlert newObjJustMaxPrice(StockTicker ticker, User user, String description, BigDecimal maxPrice){
        return new PriceAlert(ticker,user, description, maxPrice, maxPrice);
    }

    // PA . Just price MIN
    private PriceAlert(StockTicker ticker, User user, BigDecimal minPrice, String description) {
        this(ticker, user, description, BigDecimal.ZERO, minPrice, true, true, false, Long.valueOf(-1));
    }
    public static PriceAlert newObjJustMinPrice(StockTicker ticker, User user, BigDecimal minPrice, String description){
        return new PriceAlert(ticker,user, minPrice, description);
    }

    // PA With relation
    private PriceAlert(StockTicker ticker, User user, String description, BigDecimal maxPrice, BigDecimal minPrice, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        this(ticker, user, description, maxPrice, minPrice, true, isToNotify, isRelated, notifyAlertId);
    }
    public static PriceAlert newObjRelated(StockTicker ticker, User user, String description, BigDecimal maxPrice, BigDecimal minPrice, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        return new PriceAlert(ticker, user, description, maxPrice, minPrice, isToNotify, isRelated, notifyAlertId);
    }

    // PA With relation. Just price MAX
    private PriceAlert(StockTicker ticker, User user, String description, BigDecimal maxPrice, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        this(ticker, user, description, maxPrice, BigDecimal.ZERO, true, isToNotify, isRelated, notifyAlertId);
    }
    public static PriceAlert newObjRelatedJustMaxPrice(StockTicker ticker, User user, String description, BigDecimal maxPrice, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        return new PriceAlert(ticker, user, description, maxPrice, isToNotify, isRelated, notifyAlertId);
    }

    // PA With relation. Just price MIN
    private PriceAlert(StockTicker ticker, User user, BigDecimal minPrice, String description, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        this(ticker, user, description, BigDecimal.ZERO, minPrice, true, isToNotify, isRelated, notifyAlertId);
    }
    public static PriceAlert newObjRelatedJustMinPrice(StockTicker ticker, User user, BigDecimal minPrice, String description, Boolean isToNotify, Boolean isRelated, Long notifyAlertId){
        return new PriceAlert(ticker, user, minPrice, description, isToNotify, isRelated, notifyAlertId);
    }

    public Long getId() {
        return id;
    }

    public StockTicker getTicker() {
        return ticker;
    }

    public void setTicker(StockTicker ticker) {
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

    public Boolean getRelated() {
        return isRelated;
    }

    public void setRelated(Boolean related) {
        isRelated = related;
    }

    public Boolean getToNotify() {
        return isToNotify;
    }

    public void setToNotify(Boolean toNotify) {
        isToNotify = toNotify;
    }

    public Long getNotifyAlertId() {
        return notifyAlertId;
    }

    public void setNotifyAlertId(Long notifyAlertId) {
        this.notifyAlertId = notifyAlertId;
    }

}
