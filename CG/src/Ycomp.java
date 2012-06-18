import java.util.Comparator;


public class Ycomp implements Comparator<Point> {
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		return Integer.valueOf(o1.y).compareTo(Integer.valueOf(o2.y));
	}
}