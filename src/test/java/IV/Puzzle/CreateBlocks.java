package IV.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CreateBlocks {
    private Board board;

    CreateBlocks(String puzzle) {

        int[][] blocks = null;
        int sizeOfGrid = 0;


        try {
            try(BufferedReader br = new BufferedReader(new FileReader("resources/4/" + puzzle + ".txt"))) {
                String line = null;
                Scanner in;
                sizeOfGrid = Integer.parseInt(br.readLine());

                blocks = new int[sizeOfGrid][sizeOfGrid];

                for (int i = 0; i < sizeOfGrid; i++) {
                    line = br.readLine();
                    in = new Scanner(line);
                    for (int j = 0; j < sizeOfGrid; j++) {

                        blocks[i][j] = in.nextInt();
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        board = new Board(blocks);


    }

    public Board getBoard() {
        return board;
    }

}
