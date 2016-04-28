package IV.Puzzle;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testInput() {

        int[][] blocks = null;
        int sizeOfGrid = 0;

        Board board = new CreateBlocks("puzzle04").getBoard();

        System.out.println("Hamming " + board.hamming());
        System.out.println("Manhattan " + board.manhattan());
        System.out.println(board.isGoal());
//        System.out.println(board);

    }

}