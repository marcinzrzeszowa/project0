package pl.projectarea.project0.price_alert;

public interface PriceAlertObservable {
    void notifyChangeInPriceAlertsList(PriceAlertObserver observer);
}
