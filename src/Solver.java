
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;



public class Solver {
    private Stack<Board> solution = null;


    private class Node implements Comparable<Node>{
        Board searchNode ;
        Node predecessor;
        int noOfMoves = 0;


        public Node(Board board){
            this.searchNode = board;
        }



        public  Node(Board board, Node predecessor){
            this.searchNode = board;
            this.predecessor = predecessor;
            this.noOfMoves = predecessor.noOfMoves +1;


        }

        @Override
        public int compareTo(Node o) {
            return (this.searchNode.manhattan() - o.searchNode.manhattan()) + (this.noOfMoves - o.noOfMoves);

        }

    }
    private Node search;

    public Solver(Board initial)  {

        solution = new Stack<>();

        search = new Node(initial);
        Node searchTwin = new Node(initial.twin());

        MinPQ<Node> neighbors = new MinPQ<>();
        MinPQ<Node> neighborstwin = new MinPQ<>();

        neighbors.insert(search);
        neighborstwin.insert(searchTwin);



        if(!search.searchNode.isGoal()){
            while (!search.searchNode.isGoal() && !searchTwin.searchNode.isGoal()) {

                search = neighbors.delMin();
                Iterable<Board> queue = search.searchNode.neighbors();
                for (Board Board : queue) {
                    Node temp = new Node(Board, search);
                    if (search.predecessor == null ||
                        !search.predecessor.searchNode.equals(temp.searchNode)) {
                     neighbors.insert(temp);
                    }
                }

                //                 StdOut.println(search.searchNode);


                searchTwin = neighborstwin.delMin();
                Iterable<Board> queuetwin = searchTwin.searchNode.neighbors();
                for (Board Board : queuetwin) {
                    Node temp = new Node(Board, searchTwin);
                    if (searchTwin.predecessor == null ||
                        !searchTwin.predecessor.searchNode.equals(neighborstwin.min().searchNode)) {
                        neighborstwin.insert(temp);
                    }
                }
            }
        }
    }         // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable(){
        boolean isSolvable = false;
        if(search.searchNode.isGoal()){
            isSolvable = true;
        }
        return isSolvable;

    }       // is the initial board solvable?
    public int moves() {
        return this.isSolvable() ? search.noOfMoves :-1;

    }// min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution(){

       if(search.searchNode.isGoal()) {
           Node Temp = search;

           while (Temp.predecessor != null) {
               solution.push(Temp.searchNode);
               Temp = Temp.predecessor;
           }
           solution.push(Temp.searchNode);
       } else{
           solution = null;
       }

        return solution;
    }    // sequence of boards in a shortest solution; null if unsolvable



    public static void main(String[] args){
        In in = new In(args[0]);
        int lenght = in.readInt();
        int[][] blocks = new int[lenght][lenght];
        for(int i=0; i<lenght;i++){
            for(int j=0; j<lenght; j++){
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        // solver.solution();


        if (!solver.isSolvable()) StdOut.println("No Solution is Available");
        else {
            StdOut.println("Minimum number of moves " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        //        }

    }// solve a slider puzzle (given below)



}
