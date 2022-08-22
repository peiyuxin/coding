package org.pyx.pyx.argorithm.graph;

/**
 * 最小生成树
 * <p>
 * 1、一些定义：
 * 树定义：
 * 1）无向、无环、连通图
 * 2）N个节点有N-1条边
 * <p>
 * 生成树定义：（Spanning Tree）
 * 1）节点：连通的全部节点
 * 2）边：原来边的子集
 * <p>
 * 最小生成树定义：（Minimum Spanning Tree）
 * 1）所有生成树中边权值的和最小的树
 * <p>
 * 2、算法描述
 * O(N^2)
 * 贪心算法，难点在于算法的证明。
 * 始终保持一棵树，维护一个集合V={}，一个边集合E={}，初始加入一个节点，然后选择和这个节点邻接的权最小的一个节点加入。
 * 描述如下，循环N-1次，选择一条边，满足
 * 1）使v1在集合V中，v2不在
 * 2）（v1，v2）权最小
 * 3）E = E + (v1, v2)
 * 4）V = V + v2
 * <p>
 * http://www.geeksforgeeks.org/greedy-algorithms-set-5-prims-minimum-spanning-tree-mst-2/
 * Time Complexity: O(V2), If the input graph is represented using an adjacency list, then the time complexity of
 * Prim’s algorithm can be reduced to O(E log V) with the help of a binary heap.  In this implementation, we are
 * always considering the spanning tree to start from the root of the graph
 * Auxiliary Space: O(V)
 * @author pyx
 * @date 2022/8/22
 */
public class Prim<psvm> {

    // Number of vertices in the graph
    private static final int V = 5;

    // A utility function to find the vertex with minimum
    // key value, from the set of vertices not yet included
    // in MST
    int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }
        return min_index;
    }

    // A utility function to print the constructed MST
    // stored in parent[]
    void printMST(int parent[], int graph[][])
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++){
            System.out.println(parent[i] + " - " + i + "\t"
                + graph[i][parent[i]]);
        }
    }

    // Function to construct and print MST for a graph
    // represented using adjacency matrix representation
    private void primMST(int graph[][]){
        // Array to store constructed MST
        int parent[] = new int[V];

        // Key valus used to pick minimum weight edge in
        // cut
        int key[] = new int[V];

        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[V];

        //Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first vertex in MST.
        key[0] = 0; //Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1;// first node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V -1 ; count++) {
            //Pick the minimum key vertex from the set of
            //Vertices not yes included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST set
            mstSet[u] = true;

            // Update key value and parent index of the
            // adjacent vertices of the picked vertex.
            // Consider only those vertices which are not
            // yet included in MST
            for (int i = 0; i < V; i++) {
                // graph[u][v] is non zero only for adjacent
                // vertices of m mstSet[v] is false for
                // vertices not yet included in MST Update
                // the key only if graph[u][v] is smaller
                // than key[v]
                if(graph[u][i] != 0 && mstSet[i] == false
                    && graph[u][i] < key[i]){
                    parent[i] = u;
                    key[i] = graph[u][i];
                }
            }
        }

        // print the constructed MST
        printMST(parent,graph);
    }

    public static void main(String[] args) {
        /* Let us create the following graph
        2 3
        (0)--(1)--(2)
        | / \ |
        6| 8/ \5 |7
        | /     \ |
        (3)-------(4)
            9         */
        Prim t = new Prim();
        int graph[][] = new int[][] { { 0, 2, 0, 6, 0 },
            { 2, 0, 3, 8, 5 },
            { 0, 3, 0, 0, 7 },
            { 6, 8, 0, 0, 9 },
            { 0, 5, 7, 9, 0 } };
        // Print the solution
        t.primMST(graph);
    }
}
