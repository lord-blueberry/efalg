package efalg_task5_2;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WinterVertex {
	static ArrayList<ArrayList<Connection>> timeTable;
	static ArrayList<Vertex> cities;
	static HashMap<Long, Node> graph;
	static int max = Integer.MAX_VALUE - 100000000;

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("winter.in"));

		int cityCount = in.nextInt();
		int connCount = in.nextInt();
		long[] currentCities = new long[cityCount];
		ArrayList<Long> allVertices = new ArrayList<>();

		graph = new HashMap<>();

		for (int i = 0; i < connCount; i++) {
			long depart = in.nextLong();
			int city = in.nextInt();
			long arrival = in.nextLong();
			int dest = in.nextInt();

			long start = encode(depart, city);
			long end = encode(arrival, dest);
			if (!graph.containsKey(start)) {
				graph.put(start, new Node(start));
			}
			Node n = graph.get(start);

			n.connections.add(end);
			allVertices.add(start);

			//Vertex v = cities.get(city - 1);

			//v.connections.add(new Connection(depart, arrival, dest - 1));
		}

		Collections.sort(allVertices);

		//add waiting times
		long[] tmp = new long[cityCount];
		for (int i = 0; i < allVertices.size(); i++) {
			int city = decodeCity(allVertices.get(i));
			if (tmp[city - 1] != 0) {
				Node n = graph.get(allVertices.get(i));
				n.connections.add(tmp[city - 1]);
			}
			tmp[city - 1] = allVertices.get(i);
		}

		//dijkstra
		PriorityQueue<Node> pq = new PriorityQueue<>();
		Node start = new Node(new Long(1));
		start.cost = 0;
		pq.add(start);

		int minTime = 0;
		while (!pq.isEmpty()) {
			Node current = pq.poll();
			current.visited = true;
			int city = decodeCity(current.v);
			int time = decodeTime(current.v);
			ArrayList<Long> conn = current.connections;

			if (city == cityCount) {
				minTime = current.cost;
				break;
			}

			for (int i = 0; i < conn.size(); i++) {
				Node destination = graph.get(conn.get(i));
				int destTime = decodeTime(destination.v);
				int destCity = decodeCity(destination.v);

				int waitTime = destCity == city ? destTime - time : 0;
				int accCost = current.cost + waitTime;

				if (accCost < destination.cost && !destination.visited) {
					destination.cost = accCost;
					pq.add(destination);
				}
			}

		}

		System.out.println(minTime);
		PrintWriter out = new PrintWriter("winter.out");
		out.write(minTime);
		out.close();
	}

	public static long encode(long time, int city) {
		long start = time;
		start = start << 16;
		start += city;
		return start;
	}

	public static int decodeCity(long in) {
		return (int) (in & 0xffff);
	}

	public static int decodeTime(long in) {
		return (int) (in >>> 16);
	}

	public static class Node implements Comparable<Node> {
		Long v;
		int cost;
		ArrayList<Long> connections;
		boolean visited = false;

		public Node(Long v) {
			this.v = v;
			cost = max;
			connections = new ArrayList<>();
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(cost, o.cost);

		}

	}

	public static class Vertex implements Comparable<Vertex> {
		int city;
		int waitingTime;
		long currentTime;
		ArrayList<Connection> connections;

		public Vertex(int city) {
			this.city = city;
			currentTime = -1;
			waitingTime = max;
			connections = new ArrayList<>();
		}

		@Override
		public int compareTo(Vertex o) {
			int x = Integer.compare(waitingTime, o.waitingTime);
			return x == 0 ? Integer.compare(o.city, city) : x;
		}
	}

	public static class Connection implements Comparable<Connection> {
		long departTime;
		long arrivalTime;
		int destCity;

		public Connection(long depart, long arrival, int destCity) {
			this.departTime = depart;
			this.arrivalTime = arrival;
			this.destCity = destCity;

		}

		@Override
		public int compareTo(Connection o) {
			return Long.compare(departTime, o.departTime);
		}

	}
}
