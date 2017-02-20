/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mco364;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author yrobi
 */
public class GameOfLife {
   
    private Board board;
    private int size;
    private List<Cell> aliveCells;
    private final Pattern pattern;
    
    public GameOfLife(Board board, int size, Pattern shape) {
        this.board = board;
        this.size = size;
        aliveCells = Collections.synchronizedList(new ArrayList<Cell>());
        pattern = shape;
    }
    
    public void setUpPattern() {
        if (pattern == Pattern.BLINKER) {
            board.b[1][2] = true;
            board.b[2][2] = true;
            board.b[3][2] = true;
        }
        if(pattern == Pattern.TOAD) {
            board.b[2][1] = true;
            board.b[1][2] = true;
            board.b[2][2] = true;
            board.b[1][3] = true;
            board.b[1][4] = true;
            board.b[2][3] = true;
        }
        if(pattern == Pattern.BEACON) {
            board.b[1][1] = true;
            board.b[1][2] = true;
            board.b[2][1] = true;
            board.b[2][2] = true;
            board.b[3][3] = true;
            board.b[3][4] = true;
            board.b[4][3] = true;
            board.b[4][4] = true;
        }
        if(pattern == Pattern.PULSAR) {
            board.b[6][8] = true;
            board.b[6][9] = true;
            board.b[6][10] = true;
            board.b[6][14] = true;
            board.b[6][15] = true;
            board.b[6][16] = true;
            board.b[8][6] = true;
            board.b[8][11] = true;
            board.b[8][13] = true;
            board.b[8][18] = true;
            board.b[9][6] = true;
            board.b[9][11] = true;
            board.b[9][13] = true;
            board.b[9][18] = true;
            board.b[10][6] = true;
            board.b[10][11] = true;
            board.b[10][13] = true;
            board.b[10][18] = true;
            board.b[11][8] = true;
            board.b[11][9] = true;
            board.b[11][10] = true;
            board.b[11][14] = true;
            board.b[11][15] = true;
            board.b[11][16] = true;
            board.b[13][8] = true;
            board.b[13][9] = true;
            board.b[13][10] = true;
            board.b[13][14] = true;
            board.b[13][15] = true;
            board.b[13][16] = true;
            board.b[14][6] = true;
            board.b[14][11] = true;
            board.b[14][13] = true;
            board.b[14][18] = true;
            board.b[15][6] = true;
            board.b[15][11] = true;
            board.b[15][13] = true;
            board.b[15][18] = true;
            board.b[16][6] = true;
            board.b[16][11] = true;
            board.b[16][13] = true;
            board.b[16][18] = true;
            board.b[18][8] = true;
            board.b[18][9] = true;
            board.b[18][10] = true;
            board.b[18][14] = true;
            board.b[18][15] = true;
            board.b[18][16] = true;
        }
        if (pattern == Pattern.PENTADECATHOLON) {
            board.b[4][5] = true;
            board.b[5][5] = true;
            board.b[6][4] = true;
            board.b[6][6] = true;
            board.b[7][5] = true;
            board.b[8][5] = true;
            board.b[9][5] = true;
            board.b[10][5] = true;
            board.b[11][4] = true;
            board.b[11][6] = true;
            board.b[12][5] = true;
            board.b[13][5] = true;
        }
        board.print();
    }
    
    public void playGame() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        for (int i = 0; i <= 24; i++) {
            Thread t = new Thread(new generationCalculator(i));
            pool.execute(t);
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
            //stall execution until all threads finish.
        }
        System.out.println("all threads are finished");
        board.clear();
        System.out.println("the board has been cleared");
        for(Cell cell : aliveCells) {
            board.b[cell.row][cell.col] = true;
        }
        board.print();
        aliveCells.clear();
    }

     class generationCalculator implements Runnable {
        private int begin;
        private int end;
        
        public generationCalculator(int begin) {
            this.begin = begin;
            end = size;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running");
            if (begin <= size) {
                for (int i = 0; i <= end; i++) {
                    if (isAliveNextGeneration(begin,i)) {
                        aliveCells.add(new Cell(begin,i));
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " has finished");
        }
        
        public int neighborCount(int row, int col){
            int count = 0;
            if (isLegal(row - 1, col)) {
                if (board.b[row - 1][col]) {
                    count++;
                }
            }
            if (isLegal(row - 1, col + 1)) {
                if (board.b[row - 1][col + 1]) {
                    count++;
                }
            }
            if (isLegal(row, col + 1)) {
                if (board.b[row][col + 1]) {
                    count++;
                }
            }
            if (isLegal(row + 1, col + 1)) {
                if (board.b[row + 1][col + 1]) {
                    count++;
                }
            }
            if (isLegal(row + 1, col)) {
                if (board.b[row + 1][col]) {
                    count++;
                }
            }
            if (isLegal(row + 1, col - 1)) {
                if (board.b[row + 1][col - 1]) {
                    count++;
                }
            }
            if (isLegal(row, col - 1)) {
                if (board.b[row][col - 1]) {
                    count++;
                }
            }
            if (isLegal(row - 1, col - 1)) {
                if (board.b[row - 1][col - 1]) {
                    count++;
                }
            }
            return count;
        }  
        
        public boolean isLegal(int row, int col) {
            return (row >= 0 && row <= size) && (col >= 0 && col <= size);
        }
        
        public boolean isAliveNextGeneration(int row, int col){
            
            if (board.b[row][col] && (neighborCount(row,col) == 2 || neighborCount(row,col) == 3)) {
               return true;
            }
            if (!board.b[row][col] &&  neighborCount(row,col) == 3) {
                return true;
            }
            return false;
        }   
    }
    
    public enum Pattern {
        BLINKER, TOAD, BEACON, PULSAR, PENTADECATHOLON
    }

    private static class Cell {
        private final int row;
        private final int col;
        
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }  
}
 class Board {
       int size;
       boolean b[][];

    public int getSize() {
        return size;
    }
    
    Board(int size) {
        this.size = size;
        b = new boolean[size][size];
    }

    public  void print() {
        String horizontalLine = new String(new char[size * 4]).replace("\0", "-");
        System.out.println(horizontalLine);

        for (int i = 0; i < size; i++) {
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                System.out.print(b[i][j] ? " O |" : "   |");
            }
            System.out.println();
            System.out.println(horizontalLine);
        }
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                b[i][j] = false;
            }
        }
    }
}
