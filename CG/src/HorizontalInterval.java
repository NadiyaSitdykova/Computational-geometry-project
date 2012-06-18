
public class HorizontalInterval {
	int x1;
	int x2;
	int y;
	int type;

	public HorizontalInterval(int firstHorPoint, int secondHorPoint,
			int verPoint) {
		x1 = firstHorPoint;
		x2 = secondHorPoint;
		y = verPoint;
		type = 0;
	}

	public boolean equals(HorizontalInterval other) {
		if ((x1 == other.x1) && (x2 == other.x2) && (y == other.y)) {
			return true;
		} else
			return false;
	}
}