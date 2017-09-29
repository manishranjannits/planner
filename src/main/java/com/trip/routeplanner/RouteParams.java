package com.trip.routeplanner;

public class RouteParams {

	Float distance;
	Float durationInHrs;
	TransportType transportType;
	
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public Float getDurationInHrs() {
		return durationInHrs;
	}
	public void setDurationInHrs(Float durationInHrs) {
		this.durationInHrs = durationInHrs;
	}
	public TransportType getTransportType() {
		return transportType;
	}
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
}
