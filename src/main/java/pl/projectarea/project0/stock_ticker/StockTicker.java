package pl.projectarea.project0.stock_ticker;

import pl.projectarea.project0.price_alert.PriceAlert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Table(name="stock_ticker")
public class StockTicker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Podaj nazwe")
    private String name;

    @Column(name = "symbol")
    @NotBlank(message = "Podaj symbol")
    private String symbol;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TickerType type;

    @Column(name = "price_alerts")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy ="ticker")
    private Set<PriceAlert> priceAlerts;

    public StockTicker() {
    }

    public StockTicker(String symbol, String name, TickerType type) {
        this.name = name;
        this.symbol = symbol;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TickerType getType() {
        return type;
    }

    public void setType(TickerType type) {
        this.type = type;
    }

    public Set<PriceAlert> getPriceAlert() {
        return priceAlerts;
    }

    public void setPriceAlert(Set<PriceAlert> priceAlert) {
        this.priceAlerts = priceAlert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockTicker)) return false;
        StockTicker that = (StockTicker) o;
        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode() +21;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
