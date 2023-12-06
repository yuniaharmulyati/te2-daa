
// Java program for the above approach
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

class dynamicProgramming{

    // Function to check whether there
    // exists a Hamiltonian Path or not
    static boolean Hamiltonian_path(int adj[][], int N)
    {
        boolean dp[][] = new boolean[N][(1 << N)];

        // Set all dp[i][(1 << i)] to
        // true
        for(int i = 0; i < N; i++)
            dp[i][(1 << i)] = true;

        // Iterate over each subset
        // of nodes
        for(int i = 0; i < (1 << N); i++)
        {
            for(int j = 0; j < N; j++)
            {

                // If the jth nodes is included
                // in the current subset
                if ((i & (1 << j)) != 0)
                {

                    // Find K, neighbour of j
                    // also present in the
                    // current subset
                    for(int k = 0; k < N; k++)
                    {

                        if ((i & (1 << k)) != 0 &&
                                adj[k][j] == 1 && j != k &&
                                dp[k][i ^ (1 << j)])
                        {

                            // Update dp[j][i]
                            // to true
                            dp[j][i] = true;
                            break;
                        }
                    }
                }
            }
        }

        // Traverse the vertices
        for(int i = 0; i < N; i++)
        {

            // Hamiltonian Path exists
            if (dp[i][(1 << N) - 1])
                return true;
        }

        // Otherwise, return false
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

    // Driver Code
    public static void main(String[] args)
    {
        int loadedMatrix[][] = readMatrixFromTxt("20.txt");

        if (loadedMatrix != null) {
            long startTime = System.nanoTime();
            long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            if (Hamiltonian_path(loadedMatrix, loadedMatrix.length))
                System.out.println("YES");
            else
                System.out.println("NO");

            long time = System.nanoTime() - startTime;
            long memory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())- startMemory;

            System.out.println("Time: " + time / 1e6 + " ms");
            System.out.println("Memory: " + memory / 1024 + " KB");
            System.out.println("---------------------------------------------------");
        }
    }
}

