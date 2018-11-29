import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solver {
   private Stack<Board> mainlog = new Stack<>();
   private Stack<Board> twinlog = new Stack<>();
   private boolean isSolvable;
   private int noOfMoves;
   private Comparator<Board> BoardComparator = new BoardComparator();
   private MinPQ<Board> neighbors = new MinPQ(BoardComparator);

   public class Node{
        Board searchNode ;
        Node predecessor;


        private  Node(Board board){
          this.searchNode = board;
          this.predecessor = null;


        }

        private  Node nextSearch(Board board){
        Node nextSearch = new Node(board);
        nextSearch.predecessor = this;
        return  nextSearch;
        }


    }

    public Solver(Board initial)  {

       Node search = new Node(initial);
       Node searchTwin = new Node(initial.twin());

       mainlog.push(initial);
        if(search.searchNode.isGoal()){mainlog.add(search.searchNode);}
        while (!search.searchNode.isGoal() && !searchTwin.searchNode.isGoal()) {

            Queue<Board> queue = (Queue<Board>) search.searchNode.neighbors();
            for (Board Board : queue){

                neighbors.insert(Board);
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
                    !search.predecessor.searchNode.equals(neighbors.min())) {
                search = search.nextSearch(neighbors.min());
                mainlog.push(search.searchNode);
            } else {
                neighbors.delMin();
                search = search.nextSearch(neighbors.min());
                mainlog.push(search.searchNode);
            }
            noOfMoves++;

            while(!neighbors.isEmpty()){
                neighbors.delMin();
            }

            Queue<Board> queuetwin = (Queue<Board>) searchTwin.searchNode.neighbors();
            while (!queuetwin.isEmpty()){
                neighbors.insert(queuetwin.remove());
            }
            if (searchTwin.predecessor == null ||
                    !searchTwin.predecessor.searchNode.equals(neighbors.min())) {
                searchTwin = searchTwin.nextSearch(neighbors.min());
                twinlog.push(searchTwin.searchNode);
            } else {
                neighbors.delMin();
                searchTwin = searchTwin.nextSearch(neighbors.min());
                twinlog.push(searchTwin.searchNode);
            }

            while(!neighbors.isEmpty()){
                neighbors.delMin();
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
    public int moves(){return noOfMoves;}      // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()  {
        return mainlog;
    }    // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args)throws FileNotFoundException {
//        In in = new In(args[0]);
//        int lenght = in.readInt();
//        int[][] blocks = new int[lenght][lenght];
//        for(int i=0; i<lenght;i++){
//            for(int j=0; j<lenght; j++){
//                blocks[i][j] = in.readInt();
//            }
//        }

        File file = new File(args[0]);

        Scanner in = new Scanner(file);

        int lenght = in.nextInt();
        int[][] blocks = new int[lenght][lenght];

        for(int i=0; i<lenght;i++){
            for(int j=0; j<lenght; j++){
                blocks[i][j] = in.nextInt();
            }
        }




        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if(!solver.isSolvable()) StdOut.println("No Solution is Available");
        else{
            StdOut.println("Minimum number of moves"+solver.moves());
            for(Board board: solver.solution()){
                StdOut.println(board);
            }
        }

    }// solve a slider puzzle (given below)


    private class BoardComparator implements Comparator<Board>{

        @Override
        public int compare(Board x, Board y){
            return Integer.compare(x.manhattan(), y.manhattan());

        }

    }


}
