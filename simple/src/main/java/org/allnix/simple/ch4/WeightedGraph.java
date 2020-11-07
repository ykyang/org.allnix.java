package org.allnix.simple.ch4;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {
    private static PrintStream out = System.out;
    
    public WeightedGraph(Collection<V> vertices) {
        super(vertices);
    }

    public Collection<WeightedEdge> mst(int start) {
        LinkedList<WeightedEdge> result = new LinkedList<>();
        
        //> bound checking
        if (start < 0) { 
            return result;
        }
        if (getVertexCount() <= start) {
            return result;
        }
        
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[getVertexCount()];
        
        //> Use this at 2 different places so it is convenient
        //> to make it a function.
        IntConsumer visit = index -> {
            visited[index] = true;
            //> Add all edges to priority queue
            for (WeightedEdge edge : edgeOf(index)) {
                if (!visited[edge.v]) {
                    pq.offer(edge);
                }
            }
        };
        
        //> start with "start"
        visit.accept(start);
        while (!pq.isEmpty()) { 
            WeightedEdge edge = pq.poll();
            if (visited[edge.v]) {
                continue;
            }
            
            result.add(edge);
            visit.accept(edge.v);
        }
        
        return result;
    }
    
    public void printWeightedPath(Collection<WeightedEdge> weightedPath) {
       for (WeightedEdge edge : weightedPath) {
           out.println(vertexAt(edge.u) + " "  + edge.weight + "> " + vertexAt(edge.v));
       }
       out.println("Total weight: " + totalWeight(weightedPath));
    }
    
    /**
     * Add a new edge to the graph.
     * 
     * Vertex must exist.
     * 
     * @param edge
     */
    public void addEdge(WeightedEdge edge) {
        edgeOf(edge.u).add(edge);
        edgeOf(edge.v).add(edge.reversed());
    }
    
    public WeightedGraph<V> addEdge(int u, int v, double weight) {
        WeightedEdge edge = new WeightedEdge(u, v, weight);
        addEdge(edge);
        
        return this;
    }
    public WeightedGraph<V> addEdge(V u, V v, double weight) {
        WeightedEdge edge = new WeightedEdge(indexOf(u), indexOf(v), weight);
        addEdge(edge);
        
        return this;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++) {
            sb.append(vertexAt(i));
            sb.append(" -> ");
            Object[] objs = edgeOf(i).stream()
                    .map(e -> "(" + vertexAt(e.v) + ", " + e.weight + ")")
                    .toArray();
            sb.append(Arrays.toString(objs));
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();
    }
    
    public static double totalWeight(Collection<WeightedEdge> path) {
        double sum = path.stream().mapToDouble(e -> e.weight).sum();
        return sum;
    }
    
    /**
     * ./gradlew -PmainClass=org.allnix.simple.ch4.WeightedGraph runApp
     * gradlew.bat -PmainClass=org.allnix.simple.ch4.WeightedGraph runApp
     * 
     * @param args
     */
    public static void main(String[] args) {
        List<String> cities = List.of("Seattle", "San Francisco",  
                "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston",
                "New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit",
                "Philadelphia", "Washington"
                );
        
        WeightedGraph<String> cityGraph = new WeightedGraph<>(cities);
        
        cityGraph.addEdge("Seattle", "Chicago", 1737)
        .addEdge("Seattle","San Francisco", 678)
        .addEdge("San Francisco", "Riverside", 386)
        .addEdge("San Francisco", "Los Angeles", 348)
        .addEdge("Los Angeles", "Riverside", 50)
        .addEdge("Los Angeles", "Phoenix", 357)
        .addEdge("Riverside", "Phoenix", 307)
        .addEdge("Riverside", "Chicago", 1704)
        .addEdge("Phoenix", "Dallas", 887)
        .addEdge("Phoenix", "Houston", 1015)
        .addEdge("Dallas", "Chicago", 805)
        .addEdge("Dallas", "Atlanta", 721)
        .addEdge("Dallas", "Houston", 225)
        .addEdge("Houston", "Atlanta", 702)
        .addEdge("Houston", "Miami", 968)
        .addEdge("Atlanta", "Chicago", 588)
        .addEdge("Atlanta", "Washington", 543)
        .addEdge("Atlanta", "Miami", 604)
        .addEdge("Miami", "Washington", 923)
        .addEdge("Chicago", "Detroit", 238)
        .addEdge("Detroit", "Boston", 613)
        .addEdge("Detroit", "Washington", 396)
        //.addEdge("Detroit", "Washington")
        .addEdge("Detroit", "New York", 482)
        .addEdge("Boston", "New York", 190)
        .addEdge("New York", "Philadelphia", 81)
        .addEdge("Philadelphia", "Washington", 123);
        
        out.println(cityGraph.toString());
        
        Collection<WeightedEdge> mst = cityGraph.mst(0);
        cityGraph.printWeightedPath(mst);
    }
}
