package III.PatternRecognition;


import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();
    private final LineSegment[] segmentsArray;
    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;

        Point[] array = new Point[points.length];
        System.arraycopy(points,0,array,0,points.length);
        Arrays.sort(array);
        findSegments(array);
        segmentsArray = new LineSegment[segments.size()];


    }


    public int numberOfSegments() {
        return segments.size();
    }


    public LineSegment[] segments() {

        return segments.toArray(segmentsArray);
    }

    private void findSegments(Point[] points){

        Point[] singleSegment = new Point[4];

        for (int n = 0; n < points.length; n++) {
            if (points[n] == null)
                throw new NullPointerException();
            for (int i = n + 1; i < points.length; i++) {
                if (points[i].compareTo(points[n]) == 0)
                    throw new IllegalArgumentException();
                for (int j = i + 1; j < points.length; j++) {
                    if (points[j].compareTo(points[n]) == 0)
                        throw new IllegalArgumentException();
                    for (int k = j + 1; k < points.length; k++) {
                        if (points[k].compareTo(points[n]) == 0)
                            throw new IllegalArgumentException();
                        singleSegment[0] = points[n];
                        singleSegment[1] = points[i];
                        singleSegment[2] = points[j];
                        singleSegment[3] = points[k];
                        processSegment(singleSegment);
                    }
                }
            }
        }

    }

    private void processSegment(Point[] singleSegment) {

        if (singleSegment!=null) {
            Point minPoint = singleSegment[0];
            Point innerMinPoint = singleSegment[1];
            Point innerMaxPoint = singleSegment[2];
            Point maxPoint = singleSegment[3];

            LineSegment segment = new LineSegment(minPoint, maxPoint);

            int compareInner = Double.compare(minPoint.slopeTo(innerMinPoint), innerMaxPoint.slopeTo(maxPoint));
            int compareOuter = Double.compare(minPoint.slopeTo(maxPoint), innerMinPoint.slopeTo(innerMaxPoint));

            if (compareInner == 0 && compareOuter == 0)
                segments.add(segment);
        }
    }

}
