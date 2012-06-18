
public class Point {
	int x;
	int y;
	HorizontalInterval interval;

	Point(int hor, int ver, HorizontalInterval horInterval) {
		x = hor;
		y = ver;
		interval = horInterval;
	}

	public HorizontalInterval getInterval() {
		return interval;
	}

	boolean equals(Point other) {
		if ((x == other.x) && (y == other.y)
				&& (interval.equals(other.interval))) {
			return true;
		} else
			return false;
	}
}