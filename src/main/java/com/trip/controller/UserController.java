/**
 * 
 */
package com.trip.controller;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudbalance.CloudBalancingAlgo;
import com.trip.dayplanner.DayPlannerAlgorithm;

/**
 * @author prashants
 */
@RestController
@RequestMapping("/user")
public class UserController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    DayPlannerAlgorithm dayPlannerService;
    
    @Autowired
    CloudBalancingAlgo cloudBalancingService;
    
    @RequestMapping(value = "/createItinerary",
            method = RequestMethod.GET)
    @ResponseBody
    public String createItinerary() throws DatatypeConfigurationException {
    	 
    	dayPlannerService.runDayPlanner();
    	 
    	 return "success";
    }
    
    
    @RequestMapping(value = "/createCloudBalance",
            method = RequestMethod.GET)
    @ResponseBody
    public String createCloudBalance() throws DatatypeConfigurationException {
    	 
    	cloudBalancingService.runCloudBalancing();
    	 
    	 return "success";
    }
    
}
