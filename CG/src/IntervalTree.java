
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class IntervalTree {
	IntervalTree Il;
	IntervalTree Ir;
	PrioritySearchTree Iml;
	PrioritySearchTree Imr;
	int xm;

	IntervalTree(List<HorizontalInterval> L, List<Point> points) {
		int n = points.size() / 2;
		xm = points.get(n).x;
		List<HorizontalInterval> Ll = new ArrayList<HorizontalInterval>();
		List<HorizontalInterval> Lr = new ArrayList<HorizontalInterval>();
		for (int i = 0; i < n; i++) {
			HorizontalInterval interval = L.get(i);
			if (interval.x2 < xm) {
				interval.type = -1;
				Ll.add(interval);
			}
			if (interval.x1 > xm) {
				interval.type = 1;
				Lr.add(interval);
			}
		}
		List<Point> pointsl = new ArrayList<Point>();
		List<Point> pointsr = new ArrayList<Point>();
		List<Point> pointsml = new ArrayList<Point>();
		List<Point> pointsmr = new ArrayList<Point>();
		for (int i = 0; i < 2 * n; i++) {
			HorizontalInterval interval = points.get(i).getInterval();
			if (interval.type == -1) {
				pointsl.add(points.get(i));
			} else {
				if (interval.type == 1) {
					pointsr.add(points.get(i));
				} else {
					if (points.get(i).x == interval.x1) {
						pointsml.add(points.get(i));
					} else {
						pointsmr.add(points.get(i));
					}
				}
			}
		}

		Collections.sort(pointsml, new Ycomp());
		Iml = new PrioritySearchTree(new Point(Integer.MIN_VALUE,
				Integer.MIN_VALUE, null));
		PrioritySearchTree nodel = Iml;
		for (int i = 0; i < pointsml.size(); i++) {
			nodel = nodel.addmin(pointsml.get(i));
		}

		Collections.sort(pointsmr, new Ycomp());
		Imr = new PrioritySearchTree( new Point(Integer.MAX_VALUE,
				Integer.MIN_VALUE, null));
		PrioritySearchTree noder = Imr;
		for (int i = 0; i < pointsmr.size(); i++) {
			noder = noder.addmax(pointsmr.get(i));
		}

		for (int i = 0; i < Ll.size(); i++) {
			Ll.get(i).type = 0;
		}
		for (int i = 0; i < Lr.size(); i++) {
			Lr.get(i).type = 0;
		}

		if (Ll.isEmpty()) {
			Il = null;
		} else {
			Il = new IntervalTree(Ll, pointsl);
		}
		if (Lr.isEmpty()) {
			Ir = null;
		} else {
			Ir = new IntervalTree(Lr, pointsr);
		}
	}

	public void queryIntervalTree(int qx, int qy1, int qy2, List<HorizontalInterval> answer) {
		if (qx < xm) {
			Iml.queryPrioResearch(qx, qy1, qy2, true, answer);
			if (Il != null) {
				Il.queryIntervalTree(qx, qy1, qy2, answer);
			}
		} else {
			Imr.queryPrioResearch(qx, qy1, qy2, false, answer);
			if (Ir != null) {
				Ir.queryIntervalTree(qx, qy1, qy2, answer);
			}
		}
	}
}