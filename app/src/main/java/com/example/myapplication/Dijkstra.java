package com.example.myapplication;

import java.util.ArrayList;

public class Dijkstra {
    private static final int NO_PARENT = -1;

    private static int End;

    private static double distance;
    private static ArrayList<Integer> path = new ArrayList<Integer>();
    private ArrayList<String> newData = new ArrayList<>();
    private ArrayList<String> newData2 = new ArrayList<>();
     static void dijkstra(double[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        double[] shortestDistances = new double[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++) {
            shortestDistances[vertexIndex] = Double.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = (shortestDistance +
                            edgeDistance);
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents, getEnd());
    }

    private static void printSolution(int startVertex, double[] distances, int[] parents, int End) {
        int nVertices = distances.length;
        System.out.print("Vertex\t Distance\tPath");

//		for (int vertexIndex = 0;
//				vertexIndex < nVertices;
//				vertexIndex++)

        if (End != startVertex) {
            System.out.print("\n" + startVertex + " -> ");
            System.out.print(End + " \t\t ");
            System.out.print(distances[End] + "\t\t");

            setDistance(distances[End]);
            printPath(End, parents);

//            for(int i = 0; i < distances.length;i++ ){
//                distanceAr.add(distances[i]);
//                System.out.print("distancetest "+distances[i] );
//            }

        }

    }

    private static void printPath(int currentVertex, int[] parents) {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        getPath().add(currentVertex);
        System.out.print(currentVertex + " ");

    }
    void mapnewArray() {
        //map คู้
        for (int i2 = 0; i2 < (path.size() - 1); i2++) {
            String datanew = "";
            try {
                datanew = "{" + path.get(i2) + "," + path.get(i2 + 1) + "}";
                getNewData().add(datanew);
            } catch (Exception e) {
//               System.out.println(e.toString());

            }
        }

        //for send to compare xratio
        for (int i2 = 0; i2 < (path.size() - 1); i2++) {
            try {

                getNewData2().add(path.get(i2).toString());
                getNewData2().add(path.get(i2 + 1).toString());
            } catch (Exception e) {
//                System.out.println(e.toString());
            }
        }
    }

    public static int getEnd() {
        return End;
    }

    public static void setEnd(int end) {
        End = end;
    }

    public static double getDistance() {
        return distance;
    }

    public static void setDistance(double distance) {
        Dijkstra.distance = distance;
    }

    public static ArrayList<Integer> getPath() {
        return path;
    }

    public static void setPath(ArrayList<Integer> path) {
        Dijkstra.path = path;
    }

    public ArrayList<String> getNewData() {
        return newData;
    }

    public void setNewData(ArrayList<String> newData) {
        this.newData = newData;
    }

    public ArrayList<String> getNewData2() {
        return newData2;
    }

    public void setNewData2(ArrayList<String> newData2) {
        this.newData2 = newData2;
    }
}
