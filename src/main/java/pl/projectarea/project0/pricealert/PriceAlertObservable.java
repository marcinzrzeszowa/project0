package pl.projectarea.project0.pricealert;

public interface PriceAlertObservable {
    void notifyChangeInPriceAlertsList(PriceAlertObserver observer);
}
