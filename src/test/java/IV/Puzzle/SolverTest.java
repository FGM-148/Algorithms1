package IV.Puzzle;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void test(){
        Board board = new CreateBlocks("puzzle29").getBoard();


        Solver solver = new Solver(board);

        System.out.println(solver.isSolvable());
        System.out.println(solver.moves());

        for (Board b: solver.solution()) {
            System.out.println(b);
        }
    }

}