package main.models;

import java.time.LocalDateTime;

public class INGEvent {

    private String eventID;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double depthKm;
    private String author;
    private String catalog;
    private String contributor;
    private String contributorId;
    private String magType;
    private double magnitude;
    private String magAuthor;
    private String eventLocationName;
    private String eventType;

    public INGEvent(String eventID, LocalDateTime time, double latitude, double longitude, double depthKm, String author, String catalog, String contributor, String contributorId, String magType, double magnitude, String magAuthor, String eventLocationName, String eventType) {
        this.eventID = eventID;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depthKm = depthKm;
        this.author = author;
        this.catalog = catalog;
        this.contributor = contributor;
        this.contributorId = contributorId;
        this.magType = magType;
        this.magnitude = magnitude;
        if(magAuthor != null) this.magAuthor = magAuthor;
        else this.magAuthor = "--";
        this.eventLocationName = eventLocationName;
        this.eventType = eventType;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDepthKm() {
        return depthKm;
    }

    public void setDepthKm(double depthKm) {
        this.depthKm = depthKm;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getMagAuthor() {
        return magAuthor;
    }

    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj.getClass() != INGEvent.class)
            return false;

        INGEvent objINGEvent = (INGEvent)obj;
        return objINGEvent.getEventID().equals(eventID);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(eventID).append("|");
        sb.append(time).append("|");
        sb.append(latitude).append("|");
        sb.append(longitude).append("|");
        sb.append(depthKm).append("|");
        sb.append(author).append("|");
        sb.append(catalog).append("|");
        sb.append(contributor).append("|");
        sb.append(magType).append("|");
        sb.append(magnitude).append("|");
        sb.append(magAuthor).append("|");
        sb.append(eventLocationName).append("|");
        sb.append(eventType).append("|");
        return sb.toString();
    }
}
