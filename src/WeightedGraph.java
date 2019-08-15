import java.util.*;

public class WeightedGraph {

    class WeightedNode {
        int mIndex;
        private List<WeightedEdge> mNeighbors = new ArrayList<WeightedEdge>();

        WeightedNode(int index) {
            mIndex = index;
        }
    }

    private class WeightedEdge implements Comparable {

        private WeightedNode mFirst, mSecond;
        private double mWeight;

        WeightedEdge(WeightedNode first, WeightedNode second, double weight) {
            mFirst = first;
            mSecond = second;
            mWeight = weight;
        }

        @Override
        public int compareTo(Object o) {
            WeightedEdge e = (WeightedEdge) o;
            return Double.compare(mWeight, e.mWeight);
        }
    }

    private List<WeightedNode> mVertices = new ArrayList<WeightedNode>();

    public WeightedGraph(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            mVertices.add(new WeightedNode(i));
        }
    }

    public void addEdge(int firstVertex, int secondVertex, double weight) {
        WeightedNode first = mVertices.get(firstVertex);
        WeightedNode second = mVertices.get(secondVertex);

        WeightedEdge edge = new WeightedEdge(first, second, weight);
        first.mNeighbors.add(edge);
        second.mNeighbors.add(edge);
    }

    /**
     * Example: in a graph with three vertices (0, 1, and 2), with edges from 0
     * to 1 of weight 10, and from 0 to 2 of weight 20, printGraph() should print
     *
     * Vertex 0: (1, 10), (2, 20) Vertex 1: (0, 10) Vertex 2: (0, 20)
     */
    public void printGraph() { // TODO: ask if WeightedEdge.mFirst < WeightedEdge.mSecond
        String string;
        for (WeightedNode vertex : mVertices) {
            string = ("Vertex " + vertex.mIndex + ":  ");
            if (vertex.mNeighbors.size() > 0) {
                for (WeightedEdge e : vertex.mNeighbors) {
                    if (vertex.mIndex == e.mFirst.mIndex) {
                        string += ("(" + e.mSecond.mIndex + ", " + e.mWeight + "), ");
                    }
                    else {
                        string += ("(" + e.mFirst.mIndex + ", " + e.mWeight + "), ");
                    }
                }
            }
            else {
               string = ("Vertex " + vertex.mIndex + " has no neighbors.");
            }
            System.out.println(string.substring(0, string.length() - 2));
        }
    }

    /**
     * Applies Prim's algorithm to build and return a minimum spanning tree for
     * the graph. Start by constructing a new WeightedGraph with the same number
     * of vertices as this graph. Then apply Prim's algorithm. Each time an edge
     * is selected by Prim's, add an equivalent edge to the other graph. When
     * complete, return the new graph, which is the minimum spanning tree.
     *
     * @return an UnweightedGraph representing this graph's minimum spanning
     * tree.
     */
    public WeightedGraph getMinimumSpanningTree() {
        int randVertexIndex;
        int numVertices = mVertices.size();
        Random random = new Random();
        WeightedGraph primMST = new WeightedGraph(numVertices);
        HashSet<WeightedNode> setOfVertices = new HashSet<>();     // Tracks "marked" vertices
        PriorityQueue<WeightedEdge> edgePQ = new PriorityQueue<>();  // Used to select the next edge to add

        // If a VERTEX is added to the MST, add each EDGE that connects to it into the PQ
        // The queue returns the smallest-weight edge
        // When searching for the next edge to add, remove(poll) edges from the queue until an unmarked vertex is found
        // Add the unmarked vertex to the set and and add its edges to the PQ

        while (setOfVertices.size() != numVertices) {
            randVertexIndex = random.nextInt(numVertices);           // Gets a random number from [0 - numVertices]
            WeightedNode vertex = mVertices.get(randVertexIndex);
            if (setOfVertices.contains(vertex)) {                    // If the vertex is already marked, loop again
                continue;
            }
            primMST.mVertices.add(vertex);                      // Otherwise, add the vertex to the MST
            setOfVertices.add(vertex);                          // Add the vertex to the set
            edgePQ.addAll(vertex.mNeighbors);                   // Add the edges that connect to the vertex into the PQ

            WeightedEdge edge = edgePQ.peek();                  // Edge = the edge with the smallest weight

            // Repeatedly add a new edge and vertex into MST by choosing an edge that connects a vertex in MST
            // to a vertex NOT in MST... if there are several, choose the one with least weight

            for (int i = 0; i < vertex.mNeighbors.size();) {
                WeightedNode neighbor = edge.mSecond;
                int neighborIndex = neighbor.mIndex;
                int vertexIndex = vertex.mIndex;
                double edgeWeight = edge.mWeight;
                if (!setOfVertices.contains(neighbor)) {    // Checks if the vertex that we are connected to is marked
                    primMST.addEdge(vertexIndex, neighborIndex, edgeWeight);
                }
                else {
                    edgePQ.poll();
                }
            }

           }


        return null;
    }

    /**
     * Applies Dijkstra's algorithm to compute the shortest paths from a source
     * vertex to all other vertices in the graph. Returns an array of path
     * lengths; each value in the array gives the length of the shortest path
     * from the source vertex to the corresponding vertex in the array.
     */
    public double[] getShortestPathsFrom(int source) {
        // TODO: apply Dijkstra's algorithm and return the distances array.

        // This queue is used to select the vertex with the smallest "d" value
        // so far.
        // Each time a "d" value is changed by the algorithm, the corresponding
        // DijkstraDistance object needs to be removed and then re-added to
        // the queue so its position updates.
        PriorityQueue<DijkstraDistance> vertexQueue =
                new PriorityQueue<DijkstraDistance>();

        // Initialization: set the distance of the source node to 0, and all
        // others to infinity. Add all distances to the vertex queue.
        DijkstraDistance[] distances = new DijkstraDistance[mVertices.size()];
        distances[source] = new DijkstraDistance(source, 0);
        for (int i = 0; i < distances.length; i++) {
            if (i != source)
                distances[i] = new DijkstraDistance(i, Integer.MAX_VALUE);

            vertexQueue.add(distances[i]);
        }

        while (vertexQueue.size() > 0) {
            // Finish the algorithm.






        }


        return null;
    }

    class DijkstraDistance implements Comparable {

        int mVertex;
        double mCurrentDistance;

        DijkstraDistance(int vertex, double distance) {
            mVertex = vertex;
            mCurrentDistance = distance;
        }

        @Override
        public int compareTo(Object other) {
            DijkstraDistance d = (DijkstraDistance) other;
            return Double.compare(mCurrentDistance, d.mCurrentDistance);
        }
    }

    public static void main(String[] args) {
        // Use this main to test your code by hand before implementing the program.
        WeightedGraph g = new WeightedGraph(6);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(1, 4, 4);
        g.addEdge(2, 3, 1);
        g.addEdge(2, 5, 2);
        g.addEdge(3, 4, 1);
        g.addEdge(3, 5, 3);
        g.addEdge(4, 5, 2);
        g.printGraph();


        double[] distances = g.getShortestPathsFrom(0);
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Distance from 0 to " + i + ": " +
                    distances[i]);

        }

        WeightedGraph mst = g.getMinimumSpanningTree();
        System.out.println("Minimum spanning tree:");
        mst.printGraph();
    }
}
