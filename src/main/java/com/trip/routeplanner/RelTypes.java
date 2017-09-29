package com.trip.routeplanner;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType {
	KNOWS, LEADS_TO
}
