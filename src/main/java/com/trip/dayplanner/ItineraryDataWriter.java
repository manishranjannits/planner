package com.trip.dayplanner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.optaplanner.core.api.score.Score;


public class ItineraryDataWriter {

	private static final String FILE_HEADER = "timeSlotId, durationOfActivities,activityNames,totalCost,priorityMean,noOfActivities";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String COMMA_DELIMITER = ",";
	private static final String STRING_SEPARATOR = "|";
	
	public static void writeCsvFile(List<TimeSlot> orderedTimeSlotList, String algo, Score score) {
		FileWriter fileWriter = null;
		String fileName = "Itinerary_" + algo + ".csv";
	    try {
	        fileWriter = new FileWriter(fileName);

	        //Write the CSV file header
	        fileWriter.append(FILE_HEADER.toString());

	        //Add a new line separator after the header
	        fileWriter.append(NEW_LINE_SEPARATOR);

	        //Write a new student object list to the CSV file
	        for (TimeSlot timeslot : orderedTimeSlotList) {
	            fileWriter.append("" + timeslot.getId());
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append("" + timeslot.getTotalDurationEval());
	            fileWriter.append(COMMA_DELIMITER);
	        	for(Activity activity : timeslot.getActivityList()){
	        		fileWriter.append(activity.getName());
	        		fileWriter.append(STRING_SEPARATOR);
	        	}
	        	fileWriter.append(COMMA_DELIMITER);
	        	fileWriter.append("" + timeslot.getTotalCost());
	        	fileWriter.append(COMMA_DELIMITER);
	        	fileWriter.append("" + timeslot.getMeanPriority());
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append("" + timeslot.getActivityList().size());
	            fileWriter.append(NEW_LINE_SEPARATOR);
	        }
	        System.out.println("CSV file was created successfully !!!");
	        System.out.println("Score of: "+ algo +" Solution= "+ score);
	    } catch (Exception e) {
	        System.out.println("Error in CsvFileWriter !!!");
	        e.printStackTrace();
	    } finally {
	        try {
	            fileWriter.flush();
	            fileWriter.close();
	        } catch (IOException e) {
	            System.out.println("Error while flushing/closing fileWriter !!!");
	            e.printStackTrace();
	        }
	    }
	}
}
