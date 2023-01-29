import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class project5 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        String line = reader.readLine();
        graph G = new graph(Integer.parseInt(line) + 7);
        line = reader.readLine();
        String[] values = line.split(" ");
        for (int i = 0; i < values.length; i++) {
            int intValue = Integer.parseInt(values[i]);
            G.source.put("r"+i, intValue);
        }

        line = reader.readLine();
        while (line != null) {
            String[] edges = line.split(" ");
            String first = edges[0];
            if (!G.Nodes.containsKey(first)) {
                G.Nodes.put(first, G.Nodes.size());
                G.Nodes2.put(G.Nodes2.size(), first);
            }

            for (int i = 1; i < edges.length; i += 2) {
                String end = edges[i];
                if (!G.Nodes.containsKey(end)) {
                    G.Nodes.put(end, G.Nodes.size());
                    G.Nodes2.put(G.Nodes2.size(), end);
                }
                int weight = Integer.parseInt(edges[i + 1]);
                G.addEdge(G.Nodes.get(first), G.Nodes.get(end), weight);
            }
            line = reader.readLine();
        }
        G.connectSources();
        int result= (G.fordFulkerson(G.Nodes.get("superSource"), G.Nodes.get("KL")));

        writer.write(result + "\n");
        ArrayList<String> minC = G.minCut(G.Nodes.get("superSource"), G.Nodes.get("KL"));
        for (String s : minC) {
            writer.write(s + "\n");
        }


        //System.out.println(G.maxFlow(G.connectSources(), G.Nodes.get("KL")));
        reader.close();
        writer.close();
    }


}
