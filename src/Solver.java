
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;


public class Solver {
   private Stack<Board> mainlog;


   private class Node implements Comparable<Node>{
        Board searchNode ;
        Node predecessor;
        int noOfMoves = 0;


        private  Node(Board board, Node predecessor){
          this.searchNode = board;
          this.predecessor = predecessor;
          this.noOfMoves = predecessor.noOfMoves +1;


        }

       @Override
       public int compareTo(Node o) {
            if(this.searchNode.manhattan() > o.searchNode.manhattan()){ return +1;}
            if(this.searchNode.manhattan() < o.searchNode.manhattan()){ return -1;}
            else return 0;

       }

    }
    private Node search;

    public Solver(Board initial)  {

       mainlog = new Stack<>();

        search = new Node(initial, null);
       Node searchTwin = new Node(initial.twin(), null);


        if(search.searchNode.isGoal()){mainlog.add(search.searchNode);
        }else {
            mainlog.push(initial);
            while (!search.searchNode.isGoal() && !searchTwin.searchNode.isGoal()) {

                Iterable<Board> queue = search.searchNode.neighbors();
                MinPQ<Node> neighbors = new MinPQ<>();
                for (Board Board : queue) {
                    Node temp = new Node(Board, search);
                    neighbors.insert(temp);
                }
//
//            while (!queue.isEmpty()){
//                neighbors.insert(queue.remove());
//            }

//            Iterator<Board> tempItertwin = searchTwin.searchNode.neighbors().iterator();
//            Iterator<Board> tempIter = search.searchNode.neighbors().iterator();
//            Board temp = tempIter.next();
//            Board temptwin =tempItertwin.next();

                if (search.predecessor == null ||
                        !search.predecessor.searchNode.equals(neighbors.min().searchNode)) {
                    search = neighbors.min();
                    mainlog.push(search.searchNode);
                } else {
                    neighbors.delMin();
                    search = neighbors.min();
                    mainlog.push(search.searchNode);
                }


                neighbors = new MinPQ<>();

                Iterable<Board> queuetwin = searchTwin.searchNode.neighbors();
                for (Board Board : queuetwin) {
                    Node temp = new Node(Board, searchTwin);
                    neighbors.insert(temp);
                }
                if (searchTwin.predecessor == null ||
                        !searchTwin.predecessor.searchNode.equals(neighbors.min().searchNode)) {
                    searchTwin = neighbors.min();

                } else {
                    neighbors.delMin();
                    searchTwin = neighbors.min();
                }



            }
        }



    }         // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable(){
        boolean isSolvable = false;
        if(mainlog.peek().isGoal()){
           isSolvable = true;
       }
        return isSolvable;

    }       // is the initial board solvable?
    public int moves() {

        return this.isSolvable() ? search.noOfMoves :-1;

    }// min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution(){
       return mainlog;

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

//        File file = new File(args[0]);
//
//        Scanner in = new Scanner(file);
//
//        int lenght = in.nextInt();
//        int[][] blocks = new int[lenght][lenght];
//
//        for(int i=0; i<lenght;i++){
//            for(int j=0; j<lenght; j++){
//                blocks[i][j] = in.nextInt();
//            }
//        }


        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if(!solver.isSolvable()) StdOut.println("No Solution is Available");
        else{
            StdOut.println("Minimum number of moves "+solver.moves());
            for(Board board: solver.solution()){
                StdOut.println(board);
            }
        }

    }// solve a slider puzzle (given below)



}
