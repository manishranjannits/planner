package com.trip.dayplanner;

import java.util.List;

public class Itinerary {

	Integer totalDuration;
	List<TimeSlot> timeSlotList;
	
	public Integer getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}
	
	
	public List<TimeSlot> getTimeSlotList() {
		return timeSlotList;
	}
	public void setTimeSlotList(List<TimeSlot> timeSlotList) {
		this.timeSlotList = timeSlotList;
	}
	
}
