package mco364;

import java.awt.Robot;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mco364.GameOfLife.Pattern;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        boolean finished = false;
        Board board = null;
        System.out.println("please enter a pattern");
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.valueOf(scanner.next().toUpperCase());
        System.out.println("please enter either automate or step");
        String command = scanner.next();
        if (pattern == Pattern.PULSAR || pattern == Pattern.PENTADECATHOLON) {
             board = new Board(25);
        }
        else {
            board = new Board(5);
        }
        GameOfLife game = new GameOfLife(board, board.size - 1, pattern);
        game.setUpPattern();
        
        if (command.equals("automate")) {
            while (!finished) {
                game.playGame();
                sleep(500);
                clearConsole();
            }
        }
        else {
            while (!finished) {
                game.playGame();
                String input = scanner.next();
                clearConsole();
            }
        }
     
        
        
    }

    public final static void clearConsole() {
        for (int i = 0; i < 100; i++) { // safety net since next code only works on console not Netbeans output
            System.out.println();
        }
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
// ignore
        }
    }
}
