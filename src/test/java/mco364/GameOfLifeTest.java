package mco364;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertEquals;
import mco364.GameOfLife.Pattern;
import static mco364.GameOfLife.Pattern.BLINKER;
import mco364.GameOfLife.generationCalculator;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameOfLifeTest {
    
    
    @Test
    public void testNeighborCount(){
        Board board = new Board(5);
        Pattern pattern = BLINKER;
        GameOfLife game = new GameOfLife(board, board.size - 1, pattern);
        game.setUpPattern();
        generationCalculator generation = game.new generationCalculator(1);
        
       int count;
       count = generation.neighborCount(1, 2);
       
       assertEquals(1,count);
    }
 
    @Test
    public void testIsAliveNextGeneration(){
        Board board = new Board(5);
        Pattern pattern = BLINKER;
        GameOfLife game = new GameOfLife(board, board.size - 1, pattern);
        game.setUpPattern();
        generationCalculator generation = game.new generationCalculator(1);
        
       boolean isAlive;
       isAlive = generation.isAliveNextGeneration(2, 2);
       
       assertEquals(true,isAlive);
    }
}
