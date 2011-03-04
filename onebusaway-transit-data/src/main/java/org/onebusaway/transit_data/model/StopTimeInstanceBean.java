package org.onebusaway.transit_data.model;

import java.io.Serializable;
import java.util.List;

public class StopTimeInstanceBean implements Serializable {

  private static final long serialVersionUID = 3L;

  private long serviceDate;

  private long arrivalTime;

  private long departureTime;

  private String serviceId;

  private String tripId;

  private String stopHeadsign;

  private List<String> groupIds;

  public long getServiceDate() {
    return serviceDate;
  }

  public void setServiceDate(long serviceDate) {
    this.serviceDate = serviceDate;
  }

  public long getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(long arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public long getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(long departureTime) {
    this.departureTime = departureTime;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String scheduleId) {
    this.serviceId = scheduleId;
  }

  public String getTripId() {
    return tripId;
  }

  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  public String getStopHeadsign() {
    return stopHeadsign;
  }

  public void setStopHeadsign(String stopHeadsign) {
    this.stopHeadsign = stopHeadsign;
  }

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
  }
}
