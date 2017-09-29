package com.trip.dayplanner;

import java.util.Comparator;

public class TimeSlotComparator implements Comparator<Activity>{

	@Override
	public int compare(Activity o1, Activity o2) {
		Integer timeSlotId1 = o1.getTimeSlot()!=null?o1.getTimeSlot().getId():Integer.MAX_VALUE;
		Integer timeSlotId2 = o2.getTimeSlot()!=null?o2.getTimeSlot().getId():Integer.MAX_VALUE;
		
		return timeSlotId1.compareTo(timeSlotId2);
	}


}
