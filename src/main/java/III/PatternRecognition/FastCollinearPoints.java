package III.PatternRecognition;


import java.util.*;

public class FastCollinearPoints {

    private final ArrayList<Segment> segments = new ArrayList<>();
    private final LineSegment[] segmentsArray;
    private final Point[] pointsArray;
    private int resultSize;

    public FastCollinearPoints(Point[] points){

        pointsArray = new Point[points.length];
        System.arraycopy(points,0, pointsArray,0, pointsArray.length);
        Arrays.sort(pointsArray);
        if (pointsArray[pointsArray.length-1] == null)
            throw new NullPointerException();

        int numberOfEqualSlopes = 0;
        double currentSlope = 0.0;

        for (int n = 0; n < pointsArray.length-3; n++) {

            if (pointsArray[n].compareTo(pointsArray[n+1]) == 0)
                throw new IllegalArgumentException();

            Comparator<Point> comp = pointsArray[n].slopeOrder();
            Arrays.sort(pointsArray,n, pointsArray.length,comp);

            numberOfEqualSlopes = 1;
            currentSlope = pointsArray[n].slopeTo(pointsArray[n+1]);

            for (int i = n+1; i < pointsArray.length-1; i++) {
                if (Double.compare(pointsArray[n].slopeTo(pointsArray[i+1]), currentSlope) == 0)
                    numberOfEqualSlopes++;
                else {
                    if (numberOfEqualSlopes > 2) {
                        addSegment(n, i, numberOfEqualSlopes, currentSlope);
                    }
                    currentSlope = pointsArray[n].slopeTo(pointsArray[i+1]);
                    numberOfEqualSlopes = 1;
                }
            }
            if (numberOfEqualSlopes > 2) {
                addSegment(n, pointsArray.length-1, numberOfEqualSlopes, currentSlope);

            }
            Arrays.sort(pointsArray, n+1, pointsArray.length);
        }

        checkForIllegalArguments(pointsArray,pointsArray.length-1);
        segmentsArray = new LineSegment[resultSize];
        createResult();
    }

    private void checkForIllegalArguments(Point[] pointsArray, int i) {

        while (i > 0 && i > pointsArray.length-4) {
            if (pointsArray[i].compareTo(pointsArray[i-1]) == 0)
                throw new IllegalArgumentException();
            i--;
        }
    }

    private void createResult () {
        Collections.sort(segments);
        int index = 0;

        for (int i = 0; i < segments.size(); i++) {
            segmentsArray[index] = (new LineSegment(segments.get(i).start, segments.get(i).end));
            i += segments.get(i).skipSubSegments();
            index++;
        }
    }


    public int numberOfSegments(){
        return resultSize;
    }


    public LineSegment[] segments() {

        LineSegment []  result = new LineSegment[segmentsArray.length];
        System.arraycopy(segmentsArray,0, result,0, segmentsArray.length);

        return result;
    }

    private void addSegment (int startIndex, int endIndex, int numberOfPoints, double slope) {
        Point start = pointsArray[startIndex];
        Point end = pointsArray[endIndex];

        if (numberOfPoints == 3)
            resultSize++;

        segments.add(new Segment(start, end, numberOfPoints, slope));

    }

    private class Segment implements Comparable<Segment> {
        Point start;
        Point end;
        int numberOfPoints;
        double slope;

        Segment (Point s, Point e, int n, double sl) {
            start = s;
            end = e;
            numberOfPoints = n;
            slope = sl;
        }

        @Override
        public int compareTo(Segment that) {
            int result = this.end.compareTo(that.end);
            if (result == 0)
                return Double.compare(this.slope, that.slope);
            else
                return result;
        }

        int skipSubSegments() {
            return numberOfPoints - 3;
        }
    }
}
