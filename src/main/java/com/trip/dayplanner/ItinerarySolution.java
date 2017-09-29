package com.trip.dayplanner;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class ItinerarySolution {

	List<Activity> activityList;
	List<TimeSlot> timeSlotList;
	Itinerary itinerary;
	private HardSoftScore score;
	
	@PlanningEntityCollectionProperty
	public List<Activity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}
	
	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}
	public void setScore(HardSoftScore score) {
		this.score = score;
	}
	
	public Itinerary getItinerary() {
		return itinerary;
	}
	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}
	
	@ValueRangeProvider(id="timeSlotRange")
	@ProblemFactCollectionProperty
	public List<TimeSlot> getTimeSlotList() {
		return timeSlotList;
	}
	public void setTimeSlotList(List<TimeSlot> timeSlotList) {
		this.timeSlotList = timeSlotList;
	}
	
}
