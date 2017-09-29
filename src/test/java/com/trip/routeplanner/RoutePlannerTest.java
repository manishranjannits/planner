package com.trip.routeplanner;

import static com.trip.routeplanner.GraphUtil.registerShutdownHook;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;

public class RoutePlannerTest {
	private static String DB_PATH = "/tmp/neo4j";
	private static File dbFile = new File(DB_PATH);
	 
	public static void main(final String[] args) {
		
		/*RoutePlannerTest thisClass = new RoutePlannerTest();
		thisClass.clearAllNodes();
		thisClass.clearAllIndexes();
		try {
			FileUtils.deleteRecursively( dbFile );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thisClass.createSampleCities();
		thisClass.createSampleRelationShipsByRoad();*/
		CreateRoute createRoute = new CreateRoute();
//		createRoute.findNearbyCities("London", 500);
		createRoute.printAllPaths();
	}
	
	private void createSampleCities(){
		System.out.println("Creating Sample Cities");
		CreateRoute createRoute = new CreateRoute();
		createRoute.createNewCity("London");
		createRoute.createNewCity("Brighton");
		createRoute.createNewCity("Portsmouth");
		createRoute.createNewCity("Bristol");
		createRoute.createNewCity("Oxford");
		createRoute.createNewCity("Gloucester");
		createRoute.createNewCity("Northampton");
		createRoute.createNewCity("Southampton");
	}
	
	private void createSampleRelationShipsByRoad(){
		System.out.println("Creating Sample Relationships");
		CreateRoute createRoute = new CreateRoute();
		createRoute.createRoute("London", "Brighton", 52, TransportType.CAR);
		createRoute.createRoute("London", "Oxford", 95, TransportType.CAR);
		createRoute.createRoute("Brighton", "Portsmouth", 49, TransportType.CAR);
		createRoute.createRoute("Portsmouth", "Southampton", 20, TransportType.CAR);
		createRoute.createRoute("Southampton", "Bristol", 77, TransportType.CAR);
		createRoute.createRoute("Oxford", "Southampton", 66, TransportType.CAR);
		createRoute.createRoute("Oxford", "Northampton", 45, TransportType.CAR);
		createRoute.createRoute("Northampton", "Bristol", 114, TransportType.CAR);
		createRoute.createRoute("Northampton", "Gloucester", 106, TransportType.CAR);
		createRoute.createRoute("Gloucester", "Bristol", 35, TransportType.CAR);
	}
	
	
	private void clearAllNodes(){
		System.out.println("Deleting all nodes");
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Transaction tx = graphDb.beginTx();
		Iterable<Node> nodes = graphDb.getAllNodes();
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		try{
			for (Node node : nodes) {
//				System.out.println("Deleting Node: " + node.getProperty("name"));
				nodeIndex.remove(node);
				node.delete();
			}
			tx.success();
		}finally{
			tx.finish();
		}
		graphDb.shutdown();
	}
	
	private void clearAllIndexes(){
		System.out.println("Deleting all indexes");
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Transaction tx = graphDb.beginTx();
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		try{
			nodeIndex.delete();
			tx.success();
		}finally{
			tx.finish();;
		}
		graphDb.shutdown();
	}
	
	/*private void createIndex(){
		System.out.println("Creating Index in schema");
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbFile);
		registerShutdownHook(graphDb);
		IndexDefinition indexDefinition;
		Transaction tx = graphDb.beginTx();
		try{
			Schema schema = graphDb.schema();
			indexDefinition = schema.indexFor(Label.label("cityname")).on("cityname").create();
			tx.success();
		}finally{
			tx.terminate();
		}
		graphDb.shutdown();
	}*/
	
	private void createDummyCities(){/*
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbFile);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
 
		Transaction tx = graphDb.beginTx();
		try {
			Node londonNode = graphDb.createNode();
			londonNode.setProperty("name", "London");
			nodeIndex.add(londonNode, "name", "London");
 
			Node brightonNode = graphDb.createNode();
			brightonNode.setProperty("name", "Brighton");
			nodeIndex.add(brightonNode, "name", "Brighton");
 
			Node portsmouthNode = graphDb.createNode();
			portsmouthNode.setProperty("name", "Portsmouth");
			nodeIndex.add(portsmouthNode, "name", "Portsmouth");
 
			Node bristolNode = graphDb.createNode();
			bristolNode.setProperty("name", "Bristol");
			nodeIndex.add(bristolNode, "name", "Bristol");
 
			Node oxfordNode = graphDb.createNode();
			oxfordNode.setProperty("name", "Oxford");
			nodeIndex.add(oxfordNode, "name", "Oxford");
 
			Node gloucesterNode = graphDb.createNode();
			gloucesterNode.setProperty("name", "Gloucester");
			nodeIndex.add(gloucesterNode, "name", "Gloucester");
 
			Node northamptonNode = graphDb.createNode();
			northamptonNode.setProperty("name", "Northampton");
			nodeIndex.add(northamptonNode, "name", "Northampton");
 
			Node southamptonNode = graphDb.createNode();
			southamptonNode.setProperty("name", "Southampton");
			nodeIndex.add(southamptonNode, "name", "Southampton");
 
			// london -> brighton ~ 52mi
			Relationship r1 = londonNode.createRelationshipTo(brightonNode,
					RelTypes.LEADS_TO);
			r1.setProperty("distance", 52);
 
			// brighton -> portsmouth ~ 49mi
			Relationship r2 = brightonNode.createRelationshipTo(portsmouthNode,
					RelTypes.LEADS_TO);
			r2.setProperty("distance", 49);
 
			// portsmouth -> southampton ~ 20mi
			Relationship r3 = portsmouthNode.createRelationshipTo(
					southamptonNode, RelTypes.LEADS_TO);
			r3.setProperty("distance", 20);
 
			// london -> oxford ~95mi
			Relationship r4 = londonNode.createRelationshipTo(oxfordNode,
					RelTypes.LEADS_TO);
			r4.setProperty("distance", 95);
 
			// oxford -> southampton ~66mi
			Relationship r5 = oxfordNode.createRelationshipTo(southamptonNode,
					RelTypes.LEADS_TO);
			r5.setProperty("distance", 66);
 
			// oxford -> northampton ~45mi
			Relationship r6 = oxfordNode.createRelationshipTo(northamptonNode,
					RelTypes.LEADS_TO);
			r6.setProperty("distance", 45);
 
			// northampton -> bristol ~114mi
			Relationship r7 = northamptonNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r7.setProperty("distance", 114);
 
			// southampton -> bristol ~77mi
			Relationship r8 = southamptonNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r8.setProperty("distance", 77);
 
			// northampton -> gloucester ~106mi
			Relationship r9 = northamptonNode.createRelationshipTo(
					gloucesterNode, RelTypes.LEADS_TO);
			r9.setProperty("distance", 106);
 
			// gloucester -> bristol ~35mi
			Relationship r10 = gloucesterNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r10.setProperty("distance", 35);
 
			tx.success();
 
			System.out
					.println("searching for the shortest route from London to Bristol..");
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					Traversal.expanderForTypes(RelTypes.LEADS_TO,
							Direction.BOTH), "distance");
 
			WeightedPath path = finder.findSinglePath(londonNode, bristolNode);
			System.out.println("London - Bristol with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("name"));
			}
 
			System.out
					.println("\nsearching for the shortest route from Northampton to Brighton..");
			path = finder.findSinglePath(northamptonNode, brightonNode);
			System.out.println("Northampton - Brighton with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("name"));
			}
 
		} finally {
			tx.terminate();
		}
 
		graphDb.shutdown();
	*/}
	
}
