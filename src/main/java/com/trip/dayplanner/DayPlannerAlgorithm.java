package com.trip.dayplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DayPlannerAlgorithm {

	private static final String ALGO_FIRST_FIT = "first_fit";
	private static final String ALGO_FIRST_FIT_DECREASING = "first_fit_dec";
	private static final String ALGO_WEAKEST_FIT_DEC = "weakest_fit_dec";
	private static final String ALGO_STRONGEST_FIT = "strongest_fit";
	private static final Integer SLOT_DURATION = 4;
	
	public void runDayPlanner(){
//		SolverFactory<ItinerarySolution> solverFactory = getSolverFactory(ALGO_FIRST_FIT);
//		solveDayPlanner(solverFactory, ALGO_FIRST_FIT);
		
		SolverFactory<ItinerarySolution> solverFactory = getSolverFactory(ALGO_FIRST_FIT_DECREASING);
		solveDayPlanner(solverFactory, ALGO_FIRST_FIT_DECREASING);
		
		/*solverFactory = getSolverFactory(ALGO_WEAKEST_FIT_DEC);
		solveDayPlanner(solverFactory, ALGO_WEAKEST_FIT_DEC);*/
		
		/*solverFactory = getSolverFactory(ALGO_STRONGEST_FIT);
		solveDayPlanner(solverFactory, ALGO_STRONGEST_FIT);*/
		
	}
	
	
	public void solveDayPlanner(SolverFactory<ItinerarySolution> solverFactory, String algo){
		Solver<ItinerarySolution> solver = solverFactory.buildSolver();
		ActivityGenerater activityGenerater = new ActivityGenerater();
		List<Activity> unplannedActivityList = activityGenerater.generateActivities();
		List<TimeSlot> timeSlotList = generateTimeSlot(unplannedActivityList);
		
		Collections.sort(unplannedActivityList, new PriorityComparator());
		
		ItinerarySolution unsolvedItinerary = new ItinerarySolution();
		unsolvedItinerary.setActivityList(unplannedActivityList);
		unsolvedItinerary.setTimeSlotList(timeSlotList);
		long timeStart = System.currentTimeMillis();
		ItinerarySolution solvedItinerary = solver.solve(unsolvedItinerary);
		long timeEnd = System.currentTimeMillis();
		long timeTaken = (timeEnd - timeStart);
		System.out.println("Time Taken by Algo=" + timeTaken + " millisecs");
		List<Activity> activityList = solvedItinerary.getActivityList();
		Collections.sort(activityList, new TimeSlotComparator());
		List<TimeSlot> orderedTimeSlotList = getOrderedTimeSlots(activityList);
		Collections.sort(orderedTimeSlotList, new PriorityMeanComparator());
		int count = 1;
		for (TimeSlot timeSlot : orderedTimeSlotList) {
			if(timeSlot.getTotalDurationEval()>SLOT_DURATION){
				continue;
			}
			timeSlot.setId(count++);
		}
		
		ItineraryDataWriter.writeCsvFile(orderedTimeSlotList,algo,solvedItinerary.getScore());
	}
	
	public SolverFactory<ItinerarySolution> getSolverFactory(String algo){
		SolverFactory<ItinerarySolution> solverFactory = null;
		if(algo.equalsIgnoreCase(ALGO_FIRST_FIT)){
			solverFactory = SolverFactory.createFromXmlResource("itinerarySolverConfig_firstfit.xml");
		}else if(algo.equalsIgnoreCase(ALGO_FIRST_FIT_DECREASING)){
			solverFactory = SolverFactory.createFromXmlResource("itinerarySolverConfig_firstfit_dec.xml");
		}/*else if(algo.equalsIgnoreCase(ALGO_WEAKEST_FIT_DEC)){
			solverFactory = SolverFactory.createFromXmlResource("itinerarySolverConfig_weakestfit_dec.xml");
		}else if(algo.equalsIgnoreCase(ALGO_STRONGEST_FIT)){
			solverFactory = SolverFactory.createFromXmlResource("itinerarySolverConfig_strongestfit.xml");
		}*/
		
		return solverFactory;
	}
	
	private List<TimeSlot> getOrderedTimeSlots(List<Activity> activityList){
		List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		int timeSlotId = -1;
		TimeSlot timeSlot = null;
		for(Activity activity : activityList){
			if(activity.getTimeSlot()!=null && activity.getTimeSlot().getId().intValue() != timeSlotId){
				if(timeSlotId != -1){
					timeSlotList.add(timeSlot);
				}
				timeSlot = new TimeSlot();
				BeanUtils.copyProperties(activity.getTimeSlot(), timeSlot);
				timeSlot.getActivityList().add(activity);
				timeSlotId = activity.getTimeSlot().getId();
				continue;
			}
			
			timeSlot.getActivityList().add(activity);
		}
		
		return timeSlotList;
	}
	
	
	private List<TimeSlot> generateTimeSlot(List<Activity> unplannedActivityList){
		int totalDuration = 0;
		for (Activity activity : unplannedActivityList) {
			totalDuration += activity.getDurationInHrs();
		}
		
		int noOfSlotsReq = Math.floorDiv(totalDuration, SLOT_DURATION) + 1;
		List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		for(int i=1; i<=noOfSlotsReq;i++){
			TimeSlot timeSlot = new TimeSlot();
			timeSlot.setId(i);
			timeSlot.setTotalDuration(SLOT_DURATION);
			timeSlotList.add(timeSlot);
		}
		
		return timeSlotList;
	}
}
