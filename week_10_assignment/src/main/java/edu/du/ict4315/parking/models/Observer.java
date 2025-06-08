package edu.du.ict4315.parking.models;

public interface Observer<TEvent> {
    void update(TEvent event);
}
