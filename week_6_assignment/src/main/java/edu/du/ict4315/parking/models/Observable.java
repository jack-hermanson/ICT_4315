package edu.du.ict4315.parking.models;

import java.util.ArrayList;
import java.util.List;

public class Observable<TEvent> {
    protected final List<Observer<TEvent>> observers = new ArrayList<>();

    public final void notifyObservers(TEvent event) {
        for (var observer : this.observers) {
            observer.update(event);
        }
    }

    public final void abbObserver(Observer<TEvent> observer) {
        this.observers.add(observer);
    }

    public final void removeObserver(Observer<TEvent> observer) {
        this.observers.remove(observer);
    }
}
