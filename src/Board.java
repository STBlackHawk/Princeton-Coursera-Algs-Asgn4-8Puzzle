import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Iterator;

public class Board {

    private Integer dimention ;
    private Integer hamming = 0;
    private Integer manhattan = 0;
    private  Integer twin;
    private int[][] Board;
    private int blanki;
    private int blankj;

    public Board(int[][] blocks)  {

        if (blocks == null){throw new IllegalArgumentException();}

        dimention = blocks.length;
        Board = new int[blocks.length][blocks.length];

        for(int i = 0; i < blocks.length; i++){
            for (int j = 0; j<blocks.length; j++ ){

                Board[i][j] = blocks[i][j];

                if(Board[i][j] != i*blocks.length + j+1){
                    hamming++;
                }

                if(Board[i][j] != i*blocks.length + j+1){
                    int distance;
                    int quotint = Board[i][j] / blocks.length;
                    int remainder = Board[i][j] % blocks.length;

                    distance = quotint + remainder;

                    manhattan = manhattan + distance;

                }

                if (Board[i][j] == 0){
                    blanki = i;
                    blankj = j;
                }

            }
        }

    }         // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public int dimension(){return dimention;}        // board dimension n
    public int hamming(){return  hamming;}       // number of blocks out of place
    public int manhattan(){return manhattan;}                // sum of Manhattan distances between blocks and goal
    public boolean isGoal(){return (manhattan == 0 && hamming == 0);}     // is this board the goal board?
    public Board twin(){

        int[][] twinb = new int[dimention][dimention];
        Board twinBoard;
        int Rand = StdRandom.uniform(2);
        int randi = StdRandom.uniform(dimention);
        int randj = StdRandom.uniform(dimention);

        twin = twinb[randi][randj];

        if(Rand == 0) {
            if (randi != dimention - 1) {
                twinb[randi][randj] = twinb[randi + 1][randj];
                twinb[randi + 1][randj] = twin;

            } else {
                twinb[randi][randj] = twinb[randi - 1][randj];
                twinb[randi - 1][randj] = twin;
            }
        } else{
            if (randj != dimention - 1) {
                twinb[randi][randj] = twinb[randi][randj+1];
                twinb[randi][randj+1] = twin;

            } else {
                twinb[randi][randj] = twinb[randi][randj-1];
                twinb[randi][randj-1] = twin;
            }

        }

        twinBoard = new Board(twinb);
        return twinBoard;


    } // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y){return y.equals(Board);}   // does this board equal y?

    public Iterable<Board> neighbors(){
        Board nneighbor;
        Board sneighbor;
        Board wneighbor;
        Board eneighbor;


        Comparator<Board> BoardComparator = new BoardComparator();
        MinPQ neighbors = new MinPQ(BoardComparator);




        if(blanki!=0){
            nneighbor = new Board(this.swap(blanki-1, blankj));
            neighbors.insert(nneighbor);
        }
        if(blankj!=0){
            wneighbor = new Board(this.swap(blanki, blankj-1));
            neighbors.insert(wneighbor);
        }
        if(blanki != dimention-1){
            sneighbor = new Board(this.swap(blanki+1, blankj));
            neighbors.insert(sneighbor);
        }
        if(blankj != dimention-1){
            eneighbor= new Board(this.swap(blanki, blankj+1));
            neighbors.insert(eneighbor);
        }

       return neighbors;


    }  // all neighboring boards
    public String toString() {
        String B;
       B = Integer.toString(Board.length);

        for (int i = 0; i< Board.length;i++){
            B = B +"\n";
            for (int j = 0; j< Board.length; j++){
               B = B +  " "+Integer.toString(Board[i][j]);
            }

        }
        return B;

    } // string representation of this board (in the output format specified below)

    public static void main(String[] args) {}// unit tests (not graded)

    private int[][] swap(int i, int j){
        Board[blankj][blankj] = Board[i][j];
        Board[i][j] = 0;
        return Board;
    }



        private class BoardComparator implements Comparator<Board>{

        @Override
        public int compare(Board x, Board y){

            if (x.manhattan() + x.hamming() < y.manhattan() + y.manhattan()){
                return -1;
            }else if (x.manhattan() + x.hamming() > y.manhattan() + y.manhattan()){
                return -1;
            }else {return 0;}

        }

        }







}
