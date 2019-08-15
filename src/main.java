import java.util.*;
import java.io.*;

public class main {

    public static void main(String[] args) throws IOException {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("Please enter the name of a text file: ");
        String fileName = userScanner.next();

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        int graphSize = Integer.parseInt(br.readLine());

        String line;
        int start, end;
        double weight;

        // Makes a graph with nodes from [0 - graphSize - 1]
        WeightedGraph graph = new WeightedGraph(graphSize);

        for (int i = 0; i < graphSize - 1; i++) {
            if (!(line = br.readLine()).equals("")) {
                String[] lineArray = line.split("");
                line = line.replaceAll("\\s+","");
                for (int j = 0; j < line.length(); j += 2) {
                    start = i;
                    end = Integer.parseInt(lineArray[j]);
                    weight = Double.parseDouble(lineArray[j+1]);
                    graph.addEdge(start, end, weight);
                }
            }
        }

        graph.printGraph();



    }
}
