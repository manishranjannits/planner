/**
 * 
 */
package com.trip.dayplanner;

import java.util.Comparator;

/**
 * @author ManishR
 *
 */
public class PriorityMeanComparator implements Comparator<TimeSlot> {

	@Override
	public int compare(TimeSlot a, TimeSlot b) {
		Float priority1 = a.getMeanPriority();
		Float priority2 = b.getMeanPriority();
		if(priority1 == null){
			priority1 = Float.MAX_VALUE;
		}else if(priority2 == null){
			priority2 = Float.MAX_VALUE;
		}
		return priority1.compareTo(priority2);
	}

}
