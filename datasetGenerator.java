import java.io.*;
import java.util.*;

public class datasetGenerator {

    static int[][] generateGraph(int vertices) {
        Random random = new Random();
        int[][] adjMatrix = new int[vertices][vertices];

        // Generate random connections
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (i != j) {
                    int isConnected = random.nextInt(2);
                    adjMatrix[i][j] = isConnected;
                    adjMatrix[j][i] = isConnected;
                }
            }
        }

        return adjMatrix;
    }

    static boolean hasHamiltonianPath(int[][] graph) {
        int vertices = graph.length;
        boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        // Start from the first vertex
        return hamiltonianPathUtil(graph, visited, 0, 1);
    }

    static boolean hamiltonianPathUtil(int[][] graph, boolean[] visited, int current, int count) {
        int vertices = graph.length;

        if (count == vertices) {
            return true;
        }

        for (int i = 0; i < vertices; i++) {
            if (graph[current][i] == 1 && !visited[i]) {
                visited[i] = true;
                if (hamiltonianPathUtil(graph, visited, i, count + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }

        return false;
    }

    static int[][] readMatrixFromTxt(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            int size = lines.size();
            int[][] matrix = new int[size][size];
            for (int i = 0; i < size; i++) {
                String[] values = lines.get(i).split("\\s+");
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }

            bufferedReader.close();
            return matrix;
        } catch (IOException e) {
            System.out.println("An error occurred while reading the matrix from " + fileName);
            e.printStackTrace();
        }

        return null;
    }

    static void exportMatrixToTxt(int[][] matrix, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    printWriter.print(matrix[i][j] + " ");
                }
                printWriter.println();
            }

            printWriter.close();
            System.out.println("Matrix exported to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the matrix to " + fileName);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int vertices = 20; // Number of vertices in the graph

        // Generate the adjacency matrix and export it to a text file
        int[][] adjMatrix = generateGraph(vertices);
        exportMatrixToTxt(adjMatrix, "20.txt");

        // Read the adjacency matrix from the text file
        int[][] loadedMatrix = readMatrixFromTxt("20.txt");

        if (loadedMatrix != null) {
            System.out.println("Adjacency Matrix:");
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    System.out.print(loadedMatrix[i][j] + " ");
                }
                System.out.println();
            }

            if (hasHamiltonianPath(loadedMatrix)) {
                System.out.println("\nThe loaded graph contains a Hamiltonian path.");
            } else {
                System.out.println("\nThe loaded graph does not contain a Hamiltonian path.");
            }
        }
    }
}
