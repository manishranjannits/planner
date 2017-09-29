/**
 * 
 */
package com.trip.dayplanner;

import java.util.Comparator;

/**
 * @author ManishR
 *
 */
public class PriorityComparator implements Comparator<Activity> {

	@Override
	public int compare(Activity a, Activity b) {
		Integer priority1 = a.getPriority();
		Integer priority2 = b.getPriority();
		if(priority1 == null){
			priority1 = Integer.MAX_VALUE;
		}else if(priority2 == null){
			priority2 = Integer.MAX_VALUE;
		}
		return priority1.compareTo(priority2);
	}

}
