import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList; 
import java.util.Queue; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
    * Performs a breadth first search (bfs) on the input graph, starting at
    * {@code start} which represents the starting vertex.
    *
    * When exploring a vertex, make sure to explore in the order that the
    * adjacency list returns the neighbors to you. Failure to do so may cause
    * you to lose points.
    *
    * You may import/use {@code java.util.Set}, {@code java.util.List},
    * {@code java.util.Queue}, and any classes that implement the
    * aforementioned interfaces, as long as it is efficient.
    *
    * The only instance of {@code java.util.Map} that you may use is the
    * adjacency list from {@code graph}. DO NOT create new instances of Map
    * for BFS (storing the adjacency list in a variable is fine).
    *
    * DO NOT modify the structure of the graph. The graph should be unmodified
    * after this method terminates.
    *
    * @throws IllegalArgumentException if any input
    *  is null, or if {@code start} doesn't exist in the graph
    * @param <T> the generic typing of the data
    * @param start the vertex to begin the bfs on
    * @param graph the graph to search through
    * @return list of vertices in visited order
    */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                "Inputs must be non-null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj =
            graph.getAdjList();
        if (!adj.containsKey(start)) {
            throw new IllegalArgumentException(
                "Vertex not found in graph!");
        }
        List<Vertex<T>> ret = new ArrayList<Vertex<T>>();
        Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
        ret.add(start);
        q.add(start);
        while (q.size() != 0) {
            Vertex<T> curr = q.remove();
            List<VertexDistance<T>> nextList =
                adj.get(curr);
            for (int i = 0; i < nextList.size(); ++i) {
                Vertex<T> vert = nextList.get(i).getVertex();
                if (!ret.contains(vert)) {
                    q.add(vert);
                    ret.add(vert);
                }
            }
        }
        return ret;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                "Inputs must be non-null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj =
            graph.getAdjList();
        if (!adj.containsKey(start)) {
            throw new IllegalArgumentException(
                "Vertex not found in graph!");
        }
        List<Vertex<T>> ret = new ArrayList<Vertex<T>>();
        Set<Vertex<T>> vs = new HashSet<Vertex<T>>();

        rdfs(start, adj, vs, ret);
        return ret;
    }

    /**
     * recursive dfs helper
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param adjList adjacency list
     * @param visitedSet vs
     * @param returnList dfs list of vertices in visited order
     */
    public static <T> void rdfs(Vertex<T> start,
        Map<Vertex<T>, List<VertexDistance<T>>> adjList,
        Set<Vertex<T>> visitedSet,
        List<Vertex<T>> returnList) {

        visitedSet.add(start);
        returnList.add(start);

        for (VertexDistance<T> x : adjList.get(start)) {
            if (!visitedSet.contains(x.getVertex())) {
                rdfs(x.getVertex(), adjList, visitedSet, returnList);
            }
        }
    }





    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                "Inputs must be non-null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj =
            graph.getAdjList();
        if (!adj.containsKey(start)) {
            throw new IllegalArgumentException(
                "Vertex not found in graph!");
        }
        //map to return with vertices and their distance to start
        Map<Vertex<T>, Integer> ret = new HashMap<Vertex<T>, Integer>();
        //pq to get next vertex
        Queue<VertexDistance<T>> q = new PriorityQueue<VertexDistance<T>>();
        //visited set to check terminating condition
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();

        for (Vertex<T> x : adj.keySet()) {
            ret.put(x, Integer.MAX_VALUE);
        }
        ret.put(start, 0);
        visited.add(start);

        q.add(new VertexDistance<T>(start, 0));
        while (!q.isEmpty() && visited.size() < adj.keySet().size()) {
            VertexDistance<T> curr = q.poll();
            //get adj list for curr
            List<VertexDistance<T>> currAdj = adj.get(curr.getVertex());
            int totDist;
            for (VertexDistance<T> pair : currAdj) {
                totDist = pair.getDistance() + curr.getDistance();
                if (totDist < ret.get(pair.getVertex())) {
                    ret.put(pair.getVertex(), totDist);
                    q.add(new VertexDistance<T>(pair.getVertex(),
                        totDist));
                }
            }
        }
        return ret;
    }


    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                "Inputs must be non-null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj =
            graph.getAdjList();
        if (!adj.containsKey(start)) {
            throw new IllegalArgumentException(
                "Vertex not found in graph!");
        }
        //set to return with edges
        Set<Edge<T>> ret = new HashSet<Edge<T>>();
        //pq to get next vertex
        Queue<Edge<T>> q = new PriorityQueue<Edge<T>>();
        //visited set to check terminating condition
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();

        for (VertexDistance<T> pair : adj.get(start)) {
            q.add(new Edge<T>(start, pair.getVertex(),
                pair.getDistance()));
        }
        visited.add(start);
        while (!q.isEmpty() && visited.size() < adj.keySet().size()) {
            Edge<T> temp = q.poll();
            if (!visited.contains(temp.getV())) {
                visited.add(temp.getV());
                ret.add(temp);
                ret.add(new Edge<T>(temp.getV(), temp.getU(),
                    temp.getWeight()));
                for (VertexDistance<T> pair : adj.get(temp.getV())) {
                    q.add(new Edge<T>(temp.getV(), pair.getVertex(),
                        pair.getDistance()));
                }
            }
        }
        if (visited.size() < adj.size() - 1) {
            return null;
        }
        return ret;
    }
}