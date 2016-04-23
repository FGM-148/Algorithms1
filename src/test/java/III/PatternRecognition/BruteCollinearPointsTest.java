package III.PatternRecognition;

import edu.princeton.cs.algs4.StdIn;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BruteCollinearPointsTest {

    @Test
    public void input8() {

        System.out.println(System.getProperty("user.dir"));

        File file = new File("resources/3/input8.txt");
        Point[] points = null;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numberOfPoints;



        try {

            numberOfPoints = br.read();
            points = new Point[numberOfPoints];
            System.out.println(numberOfPoints);

            for (int i = 0; i < numberOfPoints; i++) {
                points[i] = new Point (br.read(), br.read());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Arrays.sort(points);

        double d1 = Double.POSITIVE_INFINITY;
        double d2 = Double.POSITIVE_INFINITY;

        System.out.println(Double.compare(d1,d2));


        //BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

    }

    @Test
    public void horizontal() {


        Point[] points = null;

        try {
            try(BufferedReader br = new BufferedReader(new FileReader("resources/3/input40.txt"))) {
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

        //System.out.println(Arrays.toString(points));

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        System.out.println(bruteCollinearPoints.numberOfSegments());
        System.out.println(Arrays.toString(bruteCollinearPoints.segments()));
    }

}