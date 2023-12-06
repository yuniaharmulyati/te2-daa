import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class backtrack
{
    int V;
    int path[];
    boolean isSafe(int v, int graph[][], int path[], int pos)
    {
        if (graph[path[pos - 1]][v] == 0)
            return false;

        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }
    boolean hamCycleUtil(int graph[][], int path[], int pos)
    {
        if (pos == V)
            return true;

        for (int v = 1; v < V; v++)
        {
            if (isSafe(v, graph, path, pos))
            {
                path[pos] = v;
                if (hamCycleUtil(graph, path, pos + 1))
                    return true;
                path[pos] = -1;
            }
        }
        return false;
    }

    int hamCycle(int graph[][])
    {
        V = graph.length;
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;

        path[0] = 0;
        if (!hamCycleUtil(graph, path, 1))
        {
            System.out.println("\nSolution does not exist");
            return 0;
        }

        printSolution(path);
        return 1;
    }

    void printSolution(int path[])
    {
        System.out.println("Solution Exists: Following" +
                " is one Hamiltonian Path");
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");

        System.out.println(" " + path[0] + " ");
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

    // driver program to test above function
    public static void main(String args[])
    {
        backtrack hamiltonian =
                new backtrack();

        int loadedMatrix[][] = readMatrixFromTxt("16.txt");
        
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        if (loadedMatrix != null) {
            hamiltonian.hamCycle(loadedMatrix);
        }

        long time = System.nanoTime() - startTime;
        long memory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())- startMemory;

        System.out.println("Time: " + time / 1e6 + " ms");
        System.out.println("Memory: " + memory / 1024 + " KB");
        System.out.println("---------------------------------------------------");

    }
}
