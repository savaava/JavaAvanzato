package main.services;

import main.models.INGEvent;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class INGEventDAOPostGres implements INGEventDAO {
    private final String URL="jdbc:postgresql://<indirizzo>:<porta>/<db>";
    private final String USER="username";
    private final String PASS="password";

    @Override
    public void insert(Collection<INGEvent> c) throws Exception {
        if(c.isEmpty()) return;

        try(
                Connection conn = DriverManager.getConnection(URL,USER,PASS);
                Statement stmt = conn.createStatement();
        ){
            StringBuilder queryInsert = new StringBuilder("INSERT INTO ingevent VALUES ");
            for(INGEvent event : c){
                queryInsert.append("(");
                queryInsert.append(
                        String.join(",",
                                "'"+event.getEventID()+"'",
                                "'"+Timestamp.valueOf(event.getTime())+"'",
                                "'"+ event.getLatitude() +"'",
                                "'"+ event.getLongitude() +"'",
                                "'"+ event.getDepthKm() +"'",
                                "'"+event.getAuthor()+"'",
                                event.getCatalog().isEmpty() ? "NULL" : "'"+event.getCatalog()+"'",
                                event.getContributor().isEmpty() ? "NULL" : "'"+event.getContributor()+"'",
                                event.getContributorId().isEmpty() ? "NULL" : "'"+event.getContributorId()+"'",
                                "'"+event.getMagType()+"'",
                                "'"+ event.getMagnitude() +"'",
                                "'"+event.getMagAuthor()+"'",
                                "'"+event.getEventLocationName().replaceAll("'","''")+"'",
                                "'"+event.getEventType()+"'")
                );
                queryInsert.append("),");
            }
            queryInsert.setCharAt(queryInsert.length()-1,';');

            //System.out.println(queryInsert.toString());
            stmt.executeUpdate(queryInsert.toString());
        }
    }

    @Override
    public Collection<INGEvent> listAll(int limit) throws Exception {
        Collection<INGEvent> events = new ArrayList<>();

        try(
                Connection conn = DriverManager.getConnection(URL,USER,PASS);
                Statement stmt = conn.createStatement();
        ){
            String query = "SELECT * FROM ingevent";
            if(limit!=-1)
                query = query+" LIMIT "+limit+";";
            else
                query = query+";";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                INGEvent ingEventCurr = getEventFromOccurency(rs);
                events.add(ingEventCurr);
            }
        }

        return events;
    }

    private INGEvent getEventFromOccurency(ResultSet rs) throws SQLException {
        String eventID = rs.getString("eventid");
        LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
        double lat = rs.getDouble("latitude");
        double lon = rs.getDouble("longitude");
        double depthKm = rs.getDouble("depthkm");
        String author = rs.getString("author");
        String catalog = rs.getString("catalog");
        String contributor = rs.getString("contributor");
        String contributorID = rs.getString("contributorid");
        String magType = rs.getString("magtype");
        double magnitude = rs.getDouble("magnitude");
        String magAuthor = rs.getString("magauthor");
        String eventLocationName = rs.getString("eventlocationname");
        String eventType = rs.getString("eventtype");

        return new INGEvent(
                eventID,time,lat,lon,depthKm,author,catalog,contributor,contributorID,magType,magnitude,magAuthor,eventLocationName,eventType
        );
    }

    @Override
    public Collection<INGEvent> getFilteredEvents(LocalDate startDate, LocalDate endDate, int limit, Double magnitudeMin, Double magnitudeMax, String location) throws Exception {
        if(startDate==null && endDate==null && magnitudeMin==null && magnitudeMax==null && location==null){
            return listAll(limit);
        }

        Collection<INGEvent> events = new ArrayList<>();

        try(
                Connection conn = DriverManager.getConnection(URL,USER,PASS);
                Statement stmt = conn.createStatement();
        ){
            StringBuffer query = new StringBuffer("SELECT * FROM ingevent WHERE 1=1");

            if(startDate!=null)
                query.append(" AND time>='").append(startDate).append("'");

            if(endDate!=null)
                query.append(" AND time<='").append(endDate).append("T23:59:59'");

            if(magnitudeMin!=null)
                query.append(" AND magnitude>=").append(magnitudeMin);

            if(magnitudeMax!=null)
                query.append(" AND magnitude<=").append(magnitudeMax);

            if(location!=null)
                query.append(" AND eventlocationname='").append(location.replaceAll("'","''")).append("'");

            if(limit!=-1)
                query.append(" LIMIT ").append(limit).append(";");

            ResultSet rs = stmt.executeQuery(query.toString());
            while(rs.next()){
                INGEvent ingEventCurr = getEventFromOccurency(rs);
                events.add(ingEventCurr);
            }
        }

        return events;
    }
}
