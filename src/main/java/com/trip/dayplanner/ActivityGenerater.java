package com.trip.dayplanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ActivityGenerater {

	public List<Activity> generateActivities(){
		List<Activity> activityList = new ArrayList<Activity>();
		String csvFile = "ActivityList.csv";
		/*InputStream is = getClass().getResourceAsStream(csvFile);*/
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] activityDataArr = null;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
            	if(lineNumber != 0){
            		activityDataArr = line.split(cvsSplitBy);
                	Activity activity = new Activity();
                	activity.setName(activityDataArr[0]);
                	activity.setDescription(activityDataArr[1]);
                	activity.setDurationInHrs(Float.parseFloat(activityDataArr[2])/60);
                	String priorityStr = activityDataArr[3];
                	if("".equalsIgnoreCase(priorityStr)){
                		activity.setPriority(Integer.MAX_VALUE/10000);
                	}else{
                		activity.setPriority(Integer.parseInt(priorityStr));
                	}
                	
                	activity.setCost(Float.parseFloat(activityDataArr[4]));
                	activityList.add(activity);
            	}
            	
            	lineNumber++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
		return activityList;
	}
	
	/*public List<ActivityProblemFact> generateActivityProblemFact(List<Activity> activityList){
		List<ActivityProblemFact> activityProblemFactList = new ArrayList<ActivityProblemFact>();
		for (Activity activity : activityList) {
			ActivityProblemFact activityProblemFact = new ActivityProblemFact();
			activityProblemFact.setDuration(activity.getDurationInHrs());
			activityProblemFactList.add(activityProblemFact);
		}
		
		return activityProblemFactList;
	}*/
}
