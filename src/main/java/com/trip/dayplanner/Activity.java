package com.trip.dayplanner;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity(difficultyComparatorClass = ItineraryDifficultyComparator.class)
public class Activity {

	private String name;
	private String description;
	private float durationInHrs;
	private TimeSlot timeSlot;
	private int priority;
	private float cost;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getDurationInHrs() {
		return durationInHrs;
	}
	public void setDurationInHrs(float durationInHrs) {
		this.durationInHrs = durationInHrs;
	}
	
	@PlanningVariable(valueRangeProviderRefs = {"timeSlotRange"})
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
}
