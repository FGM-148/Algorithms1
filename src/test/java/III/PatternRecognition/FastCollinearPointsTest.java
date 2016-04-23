package III.PatternRecognition;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FastCollinearPointsTest {

    @Test
    public void testInput40() {

        testInput(40);
        testInput(8);
        testInput(50);
        testInput(9);
        testInput(80);
    }

    private void testInput(int n) {
        Point[] points = null;

        try {
            try(BufferedReader br = new BufferedReader(new FileReader("resources/3/input" + n + ".txt"))) {
                String line = null;
                Scanner in;
                int numberOfPoints = Integer.parseInt(br.readLine());
                points = new Point[numberOfPoints];

                while (--numberOfPoints >= 0) {
                    line = br.readLine();
                    in = new Scanner(line).useDelimiter("[^0-9]+");
                    int x = in.nextInt();
                    int y = in.nextInt();
                    Point point = new Point(x, y);
                    points[numberOfPoints] = point;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);

        System.out.println(fastCollinearPoints.numberOfSegments());
        System.out.println(Arrays.toString(fastCollinearPoints.segments()));
    }

}