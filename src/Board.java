import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.MinPQ;
import java.lang.Math;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class Board {

    private Integer dimention ;
    private Integer hamming = 0;
    private Integer manhattan = 0;
    private int[][] Board;
    private int blanki;
    private int blankj;
    private Queue<Board> neighbors  = new LinkedList<Board>();

    public Board(int[][] blocks)  {

        if (blocks == null){throw new IllegalArgumentException();}

        dimention = blocks.length;
        Board = new int[blocks.length][blocks.length];

        for(int i = 0; i < blocks.length; i++){
            for (int j = 0; j<blocks.length; j++ ){

                Board[i][j] = blocks[i][j];
                if(Board[i][j] != 0){

                    if (Board[i][j] != i * blocks.length + j + 1) {
                        hamming++;
                    }

                    if (Board[i][j] != i * blocks.length + j + 1) {
                        int distance;
                        int quotint = Math.abs((i * blocks.length + j + 1) - Board[i][j]) / blocks.length;
                        int remainder = Math.abs((i * blocks.length + j + 1) - Board[i][j]) % blocks.length;

                        distance = quotint + remainder;

                        manhattan = manhattan + distance;

                    }
                } else {
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
    public boolean isGoal(){return (manhattan == 0 && hamming == 0);
    }     // is this board the goal board?

    public Board twin(){
        int[][] twinb = new int[dimention][dimention];
        int twin1 = 0;
        int twin2 = 0;
        Board twinBoard;
        int twin1bi = 0;
        int twin1bj = 0;
        int twin2bi = 0;
        int twin2bj = 0;

        int temptwin = 0;
        for(int i=0;i<dimention;i++){
            for(int j=0; j<dimention; j++){
                twinb[i][j] = Board[i][j];
                if (twinb[i][j] != i * dimention + j + 1){
                    twin1bi = i;
                    twin1bj = j;
                }
            }
        }
        ;



        while (twin1 == 0){
            twin1bi = StdRandom.uniform(dimention);
            twin1bj = StdRandom.uniform(dimention);
            twin1 = twinb[twin1bi][twin1bj];
        }

        while (twin2 == 0 || twin2 == twin1){
            twin2bi = StdRandom.uniform(dimention);
            twin2bj = StdRandom.uniform(dimention);
            twin2 = twinb[twin2bi][twin2bj];
        }


        temptwin = twinb[twin1bi][twin1bj];
        twinb[twin1bi][twin1bj] = twinb[twin2bi][twin2bj];
        twinb[twin2bi][twin2bj] = temptwin;




        twinBoard = new Board(twinb);
        return twinBoard;


    } // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board b = (Board) y;
            for (int i=0; i<dimention;i++){
                for (int j =0; j<dimention; j++){
                    if(b.Board[i][j]!= this.Board[i][j]){
                        return false;
                    }
                }

            }
            return true;
        } else {

            return false;
        }
    }// does this board equal y?

    public Iterable<Board> neighbors(){

        Board nneighbor;
        Board sneighbor;
        Board wneighbor;
        Board eneighbor;


        if(blanki!=0){
            nneighbor = this.swap(blanki-1, blankj);
            neighbors.add(nneighbor);
        }
        if(blankj!=0){
            wneighbor = this.swap(blanki, blankj-1);
            neighbors.add(wneighbor);
        }
        if(blanki != dimention-1){
            sneighbor = this.swap(blanki+1, blankj);
            neighbors.add(sneighbor);
        }
        if(blankj != dimention-1){
            eneighbor = this.swap(blanki, blankj+1);
            neighbors.add(eneighbor);
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

    private Board swap(int i, int j){
        int[][] swapBoard = new int[dimention][dimention];
        int bi = 0;
        int bj = 0;
        for (int k=0;k<dimention;k++ ){
            for (int m =0; m<dimention; m++){
                swapBoard[k][m] = Board[k][m];
                if (swapBoard[k][m]== 0){
                    bi=k;
                    bj=m;
                }
            }
        }
        int temp = swapBoard[bi][bj];
        swapBoard[bi][bj] = swapBoard[i][j];
        swapBoard[i][j] = temp;
        return new Board(swapBoard);
    }






}
