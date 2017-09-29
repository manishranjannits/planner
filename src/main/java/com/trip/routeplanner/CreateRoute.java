package com.trip.routeplanner;

import static com.trip.routeplanner.GraphUtil.registerShutdownHook;

import java.util.List;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;


public class CreateRoute {
	private static String DB_PATH = "/tmp/neo4j";

	public void createNewCity(String cityName) {
		System.out.println("Creating new city: " + cityName);
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Transaction tx = graphDb.beginTx();
		try {
			Node cityNode = graphDb.createNode();
			cityNode.setProperty("cityname", cityName);
			nodeIndex.add(cityNode, "cityname", cityName);
			tx.success();
		} catch(Exception ex){
			System.out.println("Exception Creating new city: " + ex);
		}finally {
			tx.finish();
			graphDb.shutdown();
		}
	}

	public void createRoute(String fromCity, String toCity, float distance, TransportType transportType) {
		System.out.println("Creating new route: " + fromCity + "-" + toCity);
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Transaction tx = graphDb.beginTx();
		try {
			Node fromNode = nodeIndex.get("cityname", fromCity).getSingle();
			Node toNode = nodeIndex.get("cityname", toCity).getSingle();
			Relationship r1 = fromNode.createRelationshipTo(toNode,
					RelTypes.LEADS_TO);
			
			r1.setProperty("distance", distance);
			r1.setProperty("transportType", transportType.name());
			tx.success();
		}catch(Exception ex){
			System.out.println("Exception Creating new city: " + ex);
		} finally {
			tx.finish();
			graphDb.shutdown();
		}

	}

	public void findShortestRoute(String fromCity, String toCity) {
		System.out.println("Finding Shortest route: " + fromCity + "-" + toCity);
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Transaction tx = graphDb.beginTx();
		try {
			Node fromNode = nodeIndex.get("cityname", fromCity).getSingle();
			Node toNode = nodeIndex.get("cityname", toCity).getSingle();
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					Traversal.expanderForTypes(RelTypes.LEADS_TO,
							Direction.BOTH), "distance");

			WeightedPath path = finder.findSinglePath(fromNode, toNode);
			System.out.println("London - Bristol with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("cityname"));
			}
			 tx.success();
		}catch(Exception ex){
			System.out.println("Exception Creating new city: " + ex);
		} finally {
			 tx.finish();
			graphDb.shutdown();
		}
	}
	
	public void findNearbyCities(String fromCity, float distance){
		System.out.println("Finding Nearby City: " + fromCity);
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Transaction tx = graphDb.beginTx();
		try {
			Node fromNode = nodeIndex.get("cityname", fromCity).getSingle();
			float distanceVal = 0F;
			Traverser cityTraverser = getNearbyCity(fromNode,distance);
			for (Node nearByCityNode : cityTraverser) {
				distanceVal = distanceVal + (Float)cityTraverser.currentPosition().lastRelationshipTraversed().getProperty("distance");
				System.out.print("Cities Near " + fromCity + " "
						+ nearByCityNode.getProperty("cityname") + " at distance: "+ distanceVal
						+ " at depth: " + cityTraverser.currentPosition().depth());
				if(cityTraverser.currentPosition().depth() > 1){
					System.out.print(" via: " );
					Node currentNode = cityTraverser.currentPosition().currentNode();
					int depth = cityTraverser.currentPosition().depth();
					PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
							Traversal.expanderForTypes(RelTypes.LEADS_TO,
									Direction.BOTH), depth);
					
					Path path = finder.findSinglePath(fromNode, currentNode);
					int count = 0;
					for (Node n : path.nodes()) {
						if(count != 0 && count != depth){
							System.out.print(" " + n.getProperty("cityname"));
						}
						count++;
					}
				}
				System.out.println("");
			}
			 tx.success();
		}catch(Exception ex){
			System.out.println("Exception finding nearby city: " + ex);
		} finally {
			 tx.finish();
			graphDb.shutdown();
		}
	}
	
	public void printAllPaths(){
		System.out.println("Printing all paths...");
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Transaction tx = graphDb.beginTx();
		try {
			
			Iterable<Node> nodes = graphDb.getAllNodes();
			for (Node node : nodes) {
				Iterable<Relationship> relationships = node.getRelationships();
				for (Relationship relationship : relationships) {
					System.out.println("Path: ");
					Node[] nodeArr = relationship.getNodes();
					for (int i = 0; i < nodeArr.length; i++) {
						System.out.print(nodeArr[i].getProperty("cityname") + "-");
					}
					System.out.println("");
				}
			}
			
			/*PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					Traversal.expanderForTypes(RelTypes.LEADS_TO,
							Direction.BOTH), "distance");
			WeightedPath path = finder.;
			System.out.println("London - Bristol with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("cityname"));
			}*/
			tx.success();
		} catch(Exception ex){
			System.out.println("Exception Creating new city: " + ex);
		}finally {
			tx.finish();
			graphDb.shutdown();
		}
	}
	
	private static Traverser getNearbyCity(final Node cityNode, final float distance) {
		return cityNode.traverse(Order.BREADTH_FIRST,
				StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator() {
					float distanceVar;
					float maxDistance;
					@Override
					public boolean isReturnableNode(final TraversalPosition currentPos) {
						if((!currentPos.isStartNode())){
							distanceVar += (Float)currentPos.lastRelationshipTraversed().getProperty("distance");
						}
						
						return !currentPos.isStartNode()
								&& currentPos.lastRelationshipTraversed().hasProperty("distance")
								&& (maxDistance >= distanceVar);
					}
					
					private ReturnableEvaluator init(float var){
						maxDistance = var;
				        return this;
				    }
				}.init(distance), RelTypes.LEADS_TO, Direction.BOTH);
	}
	
	
}
