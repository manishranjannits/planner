package com.trip.dayplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ItineraryScoreCalculator  implements EasyScoreCalculator<ItinerarySolution> {

	@Override
	public HardSoftScore calculateScore(ItinerarySolution solution) {
		int hardScore = 0;
        int softScore = 0;
        
        for(TimeSlot timeSlot : solution.getTimeSlotList()){
        	float sumOfDuration = 0F;
        	int timeSlotDuration = timeSlot.getTotalDuration();
        	float duration = 0F;
        	for(Activity activity : solution.getActivityList()){
        		if(activity.getDurationInHrs() > timeSlotDuration){
        			activity.setTimeSlot(null);
        		}
        		if(activity.getTimeSlot()!=null && activity.getTimeSlot().getId().intValue() == timeSlot.getId().intValue()){
        			duration = activity.getDurationInHrs();
        			sumOfDuration += duration; 
        			if(sumOfDuration > timeSlotDuration){
                    	hardScore--;
//                    	activity.setTimeSlot(null);
                    }
        		}
        	}
        	
        	float timeAvailabile = timeSlotDuration - sumOfDuration;
        	//Hard Constraints
        	if(timeAvailabile < 0){
            	hardScore += timeAvailabile;
            }
//        	if(duration > 4){
//        		hardScore--;
//        	}
        	
        	
        }
        
        /*if(solution.getActivityList().size() > 3){
        	softScore--;
        }*/
        
        return HardSoftScore.valueOf(hardScore, softScore);
	}

}
