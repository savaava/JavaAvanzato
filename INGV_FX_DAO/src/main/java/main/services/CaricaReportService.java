package main.services;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.models.INGEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

public class CaricaReportService extends Service<Integer> {
    public String url;
    private final boolean loadModeFromDB;

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int limit;
    private final Double minMag;
    private final Double maxMag;
    private String location;

    private final ObservableList<INGEvent> ingEvList;

    public CaricaReportService(boolean loadModeFromDB, LocalDate startDate, LocalDate endDate, int limit, Double minMag, Double maxMag, String location, ObservableList<INGEvent> ingEvList){
        this.loadModeFromDB = loadModeFromDB;

        this.startDate = startDate;
        this.endDate = endDate;
        this.limit = limit;
        this.minMag = minMag;
        this.maxMag = maxMag;
        this.location = location;
        updateUrl();

        this.ingEvList = ingEvList;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call()  {
                INGEventDAO ingEventDAO = new INGEventDAOPostGres();
                Collection<INGEvent> eventsTmp = new ArrayList<>();
                int iterations = 0;

                if(!loadModeFromDB){ /* caricamento dati dal Web Server */
                    if(startDate==null || endDate==null)
                        return 0;

                    URL u = null;
                    try {
                        u = new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try(BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()))) {
                        List<String> lines = in.lines().skip(1).collect(Collectors.toList());

                        long totalIngEv = lines.size();
                        if (totalIngEv == 0)
                            return 0;

                        for(String ingEvLine : lines){
                            INGEvent ingEvent = loadIngEvLine(ingEvLine);
                            if(!ingEvList.contains(ingEvent)) {
                                eventsTmp.add(ingEvent);
                                //ingEvList.add(ingEvent);
                            }
                            updateProgress(iterations, totalIngEv);
                            //Thread.sleep(10);
                            iterations++;
                        }

                        if(!eventsTmp.isEmpty()) {
                            ingEventDAO.insert(eventsTmp);
                            ingEvList.addAll(eventsTmp);
                        }

                        return iterations;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }else{ /* caricamento dati dal DB */
                    try {
                        eventsTmp = ingEventDAO.getFilteredEvents(startDate, endDate, limit, minMag, maxMag, location);

                        //ingEvList.addAll(eventsTmp);

                        int totalIngEv = eventsTmp.size();
                        for(INGEvent ingEvent : eventsTmp){
                            ingEvList.add(ingEvent);
                            updateProgress(iterations, totalIngEv);
                            Thread.sleep(10);
                            iterations++;
                        }

                        return iterations;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return 0;
                    }
                }
            }
        };
    }

    private void updateUrl() {
        url = "http://webservices.ingv.it/fdsnws/event/1/query?starttime="+startDate+"&endtime="+endDate+"&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit="+limit;
    }

    private INGEvent loadIngEvLine(String ingEvLine){
        String[] ingEvLineSplit = ingEvLine.split("\\|");

        Double lat = ingEvLineSplit[2].isEmpty() ? -1:Double.parseDouble(ingEvLineSplit[2]);
        Double lon = ingEvLineSplit[3].isEmpty() ? -1:Double.parseDouble(ingEvLineSplit[3]);
        Double depthKm = ingEvLineSplit[4].isEmpty() ? -1:Double.parseDouble(ingEvLineSplit[4]);
        Double mag = ingEvLineSplit[10].isEmpty() ? -1: Double.parseDouble(ingEvLineSplit[10]);

        return new INGEvent(
                ingEvLineSplit[0], LocalDateTime.parse(ingEvLineSplit[1]), lat, lon,
                depthKm, ingEvLineSplit[5], ingEvLineSplit[6], ingEvLineSplit[7],
                ingEvLineSplit[8], ingEvLineSplit[9], mag, ingEvLineSplit[11],
                ingEvLineSplit[12], ingEvLineSplit[13]
        );
    }
}
