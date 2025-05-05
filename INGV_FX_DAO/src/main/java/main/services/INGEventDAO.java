package main.services;

import main.models.INGEvent;

import java.time.LocalDate;
import java.util.Collection;

public interface INGEventDAO {
    void insert(Collection<INGEvent> c) throws Exception;
    Collection<INGEvent> listAll(int limit) throws Exception;
    Collection<INGEvent> getFilteredEvents(LocalDate startDate, LocalDate endDate, int limit, Double magnitudeMin, Double magnitudeMax, String location) throws Exception;
}
