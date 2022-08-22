package org.pyx.pyx.argorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 经典的BFS算法
 * <p>
 * Given a snake and ladder board, find the minimum number of dice throws required to reach the destination or last
 * cell from source or 1st cell. Basically, the player has total control over outcome of dice throw and wants to find
 * out minimum number of throws required to reach last cell.
 * <p>
 * If the player reaches a cell which is base of a ladder, the player has to climb up that ladder and if reaches a
 * cell is mouth of the snake, has to go down to the tail of snake without a dice throw.
 * <p>
 * snakesladders
 * <p>
 * For example consider the board shown on right side (taken from here), the minimum number of dice throws required
 * to reach cell 30 from cell 1 is 3. Following are steps.
 * <p>
 * a) First throw two on dice to reach cell number 3 and then ladder to reach 22
 * b) Then throw 6 to reach 28.
 * c) Finally through 2 to reach 30.
 * <p>
 * There can be other solutions as well like (2, 2, 6), (2, 4, 4), (2, 3, 5).. etc.
 * <p>
 * We strongly recommend to minimize the browser and try this yourself first.
 * The idea is to consider the given snake and ladder board as a directed graph with number of vertices equal to the
 * number of cells in the board. The problem reduces to finding the shortest path in a graph. Every vertex of the
 * graph has an edge to next six vertices if next 6 vertices do not have a snake or ladder. If any of the next six
 * vertices has a snake or ladder, then the edge from current vertex goes to the top of the ladder or tail of the
 * snake. Since all edges are of equal weight, we can efficiently find shortest path using Breadth First Search of
 * the graph.
 * <p>
 * Following is C++ implementation of the above idea. The input is represented by two things, first is ‘N’ which is
 * number of cells in the given board, second is an array ‘move[0…N-1]’ of size N. An entry move[i] is -1 if there is
 * no snake and no ladder from i, otherwise move[i] contains index of destination cell for the snake or the ladder at i.
 * http://www.geeksforgeeks.org/snake-ladder-problem-2/
 * @author pyx
 * @date 2022/8/22
 */
public class SnakeAndLadderProblem {
    // An entry in queue used in BFS
    static class Vertex {
        int id; // Vertex number
        int dist; // Distance of this vertex from source

        public Vertex(){}

        public Vertex(int id,int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                "id=" + id +
                ", dist=" + dist +
                '}';
        }

        /**
         * This function returns minimum number of dice throws required to Reach last cell
         * from 0'th cell in a snake and ladder game. move[] is an array of size N where N
         * is no. of cells on board If there is no snake or ladder form cell i, then move[i]
         * is -1 Otherwise move[i] contains cell to which snake or ladder at i takes to.
         *
         * @param move
         * @param n
         * @return
         */
        static int getMinDiceThrows(int move[], int n){
            int visited[] = new int[n];
            Queue<Vertex> queue = new LinkedList<>();
            Vertex starter = new Vertex(0,0);
            Vertex res = null;

            //Mark the node 0 as visited and enqueue it.
            visited[0] = 1;
            queue.add(starter);


            //Do a BFS starting from vertex at index 0
            while (!queue.isEmpty()){
                Vertex v = queue.poll();
                int id = v.id;

                //If front vertex is the destination vertex, we are done
                if(id == n-1){
                    res = v;
                    break;
                }

                //Otherwise dequeue the front vertex and enqueue its adjacent vertices
                //(or cell numbers reachable through a dice throw)
                for (int i = id +1; i <= (id +6) && i< n; ++i) {
                    //If this cell is already visited, then ignore
                    if (visited[i] == 0) {
                        //Otherwise calculate its distance and mark it as visited
                        Vertex a = new Vertex();
                        a.dist = v.dist + 1;
                        visited[i] = 1;

                        // Check if there a snake or ladder at 'i' then tail of snake or top of ladder
                        // become the adjacent of 'i'
                        if (move[i] != -1){
                            a.id = move[i];
                        }else {
                            a.id = i;
                        }
                        ((LinkedList<Vertex>)queue).add(a);
                    }
                }
            }

            if (res == null) {
                throw new RuntimeException("No solution!");
            }

            //We reach here when 'v' has last vertex return the distance of vertex in 'v'
            return res.dist;
        }

        public static void main(String[] args) {
            // Let us construct the board given in above diagram
            int N = 30;
            int moves[] = new int[N];
            for (int i = 0; i < N; i++) {
                moves[i] = -1;
            }
            // Ladders
            //moves[2] = 21;
            moves[4] = 7;
            //moves[10] = 25;
            //moves[19] = 28;

            // Snakes
            moves[29] = 0;
            moves[28] = 0;
            moves[27] = 0;
            moves[25] = 0;
            moves[24] = 0;
            moves[23] = 0;
            moves[26] = 0;
            moves[20] = 8;
            moves[16] = 3;
            moves[18] = 6;

            System.out.println("掷骰子所需的最小次数为 "
                + getMinDiceThrows(moves, N));
        }

    }
}
