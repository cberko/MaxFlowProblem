import java.util.*;

public class graph {
    int totalSource = 6;
    int totalNode;
    HashMap<String, Integer> Nodes;
    HashMap<Integer, String> Nodes2;
    HashMap<String, Integer> source;
    int[][] AdjMatrix;
    boolean[] visited;
    int[] excess;
    int[] height;
    int[] currentEdge;
    int[][] flow;
    public graph(int totalNode) {
        this.totalNode = totalNode;
        AdjMatrix = new int[totalNode + 1][totalNode + 1];
        visited = new boolean[totalNode + 1];
        source = new HashMap<>();
        Nodes = new HashMap<>();
        Nodes2 = new HashMap<>();
        excess = new int[totalNode + 1];
        height = new int[totalNode + 1];
        currentEdge = new int[totalNode + 1];
        flow = new int[totalNode + 1][totalNode + 1];
        for (int i = 0; i < totalNode; i++) {
            for (int j = 0; j < totalNode; j++) {
                AdjMatrix[i][j] = 0;
                flow[i][j] = 0;
            }
        }
    }


    public void addEdge(int source, int destination, int weight) {
        AdjMatrix[source][destination] = weight;
        flow[source][destination] = weight;
    }

    public int connectSources() {
        int super_source = totalNode;
        totalNode++;
        for (String sourcename : source.keySet()) {
            int destination = Nodes.get(sourcename);
            addEdge(super_source, destination, source.get(sourcename));
            Nodes.put("superSource", super_source);
            Nodes2.put(super_source, "superSource");
        }
        return super_source;
    }

    public int fordFulkerson(int source, int sink) {
        int maxFlow = 0;
        int[] parent = new int[totalNode + 1];
        while (bfs(source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, flow[u][v]);
            }
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] -= pathFlow;
                flow[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }

        return maxFlow;
    }
    public boolean bfs(int source, int sink, int[] parent) {
        boolean[] visited = new boolean[totalNode + 1];
        for (int i = 0; i < totalNode; i++) {
            visited[i] = false;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
        while (queue.size() != 0) {
            int u = queue.poll();
            for (int v = 0; v < totalNode; v++) {
                if (!visited[v] && flow[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return visited[sink];
    }
    private void dfs(int source, boolean[] visited){
        visited[source] = true;
        for(int i = 0;i<totalNode;i++){
            if(flow[source][i] > 0 && !visited[i]){
                dfs(i,visited);
            }
        }
    }

    public ArrayList<String> minCut(int source, int sink) {
        boolean[] visited = new boolean[totalNode + 1];
        dfs(source, visited);
        ArrayList<String> minCut = new ArrayList<>();
        for(int i = 0;i<totalNode;i++){
            for(int j = 0;j<totalNode;j++){
                if(visited[i] && !visited[j] && AdjMatrix[i][j] > 0){
                    if(i==totalNode-1){
                        minCut.add(Nodes2.get(j));
                    }
                    else{
                        minCut.add(Nodes2.get(i) + " " + Nodes2.get(j));
                    }
                }
            }
        }
        return minCut;
    }
}





