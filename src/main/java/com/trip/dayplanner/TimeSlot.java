package com.trip.dayplanner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;


public class TimeSlot {
	
	//Variables which will not vary during solving
	Integer id;
	Integer totalDuration;
	
	//Variables to help with data after solving is complete
	List<Activity> activityList;
	Float meanPriority;
	Float totalCost;
	Float totalDurationEval;
	
	public Integer getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Activity> getActivityList() {
		if(activityList == null){
			//return empty collection;
			return new ArrayList<Activity>();
		}
		return activityList;
	}
	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}
	public Float getMeanPriority() {
		if(meanPriority == null){
			if(!CollectionUtils.isEmpty(activityList)){
				int totalPriority = 0;
				int totalActivity = 0;
				for (Activity activity : activityList) {
					totalPriority += activity.getPriority();
					totalActivity++;
				}
				meanPriority = ((float)totalPriority/(float)totalActivity);
			}
		}
		return meanPriority;
	}
	public void setMeanPriority(Float meanPriority) {
		this.meanPriority = meanPriority;
	}
	public Float getTotalCost() {
		if(totalCost == null){
			if(!CollectionUtils.isEmpty(activityList)){
				totalCost = 0F;
				for (Activity activity : activityList) {
					totalCost = totalCost + activity.getCost();
				}
			}
		}
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public Float getTotalDurationEval() {
		if(totalDurationEval == null){
			if(!CollectionUtils.isEmpty(activityList)){
				totalDurationEval = 0F;
				for (Activity activity : activityList) {
					totalDurationEval = totalDurationEval + activity.getDurationInHrs();
				}
			}
		}
		return totalDurationEval;
	}
	public void setTotalDurationEval(Float totalDurationEval) {
		this.totalDurationEval = totalDurationEval;
	}
	
}
