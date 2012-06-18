import java.util.List;


public class PrioritySearchTree {
	Point v;
	PrioritySearchTree p;
	PrioritySearchTree lc;
	PrioritySearchTree rc;

	public PrioritySearchTree( Point point) {
		v = point;
		p = null;
		lc = null;
		rc = null;
	}

	public PrioritySearchTree addmin(Point point) {
		if (this.v.x < point.x) {
			PrioritySearchTree node = new PrioritySearchTree(point);
			node.p = this;
			if (this.rc != null) {
				this.rc.p = node;
				node.lc = this.rc;
			}
			this.rc = node;
			return node;
		} else {
			return this.p.addmin(point);
		}
	}

	public PrioritySearchTree addmax(Point point) {
		if (this.v.x > point.x) {
			PrioritySearchTree node = new PrioritySearchTree( point);
			node.p = this;
			if (this.rc != null) {
				this.rc.p = node;
				node.lc = this.rc;
			}
			this.rc = node;
			return node;
		} else {
			return this.p.addmax(point);
		}
	}

	public void reportInSubtree(int qx, boolean dirIsLeft, List<HorizontalInterval> answer) {
		if (((v.x <= qx) && (dirIsLeft)) || ((v.x >= qx) && (!dirIsLeft))) {
			HorizontalInterval interval = v.interval;
			answer.add(interval);
			//out.println(interval.x1 + " " + interval.x2 + " " + interval.y);
			if (lc != null)
				lc.reportInSubtree(qx, dirIsLeft, answer);
			if (rc != null)
				rc.reportInSubtree(qx, dirIsLeft, answer);
		}
	}

	private void checkAndReportPoint(int qx, int qy1, int qy2,
			boolean dirIsLeft, List<HorizontalInterval> answer) {
		if ((((dirIsLeft) && (v.x <= qx)) || ((!dirIsLeft) && (v.x >= qx)))
				&& (v.y >= qy1) && (v.y <= qy2)) {
			HorizontalInterval interval = v.interval;
			answer.add(interval);
			//out.println(interval.x1 + " " + interval.x2 + " " + interval.y);
		}
	}

	public void queryPrioResearch(int qx, int qy1, int qy2,
			boolean dirIsLeft, List<HorizontalInterval> answer) {
		PrioritySearchTree lpath = this;
		PrioritySearchTree rpath = this;

		// костыли для запроса, не содержащиего ни одной точки по вертикали? а надо ли?
		boolean lchanged = true;
		boolean rchanged = true;
		while ((lpath.equals(rpath))&&(lchanged||rchanged)) { 
			lpath.checkAndReportPoint(qx, qy1, qy2, dirIsLeft, answer);
			lchanged = false;
			rchanged = false;
			if ((lpath.v.y >= qy1) && (lpath.lc != null)) {
				lpath = lpath.lc;
				lchanged = true;
			} else {
				if ((lpath.v.y < qy1) && (lpath.rc != null)) {
					lpath = lpath.rc;
					lchanged = true;
				}
			}

			if ((rpath.v.y > qy2) && (rpath.lc != null)) {
				rpath = rpath.lc;
				rchanged = true;
			} else {

				if ((rpath.v.y <= qy2) && (rpath.rc != null)) {
					rpath = rpath.rc;
					rchanged = true;
				}
			}

		}

		PrioritySearchTree vsplit;

		if (lpath.p.equals(rpath)) {
			vsplit = rpath;
		} else {
			vsplit = rpath.p;
		}

		if (lchanged)
			lpath.checkAndReportPoint(qx, qy1, qy2, dirIsLeft, answer);
		if (rchanged)
			rpath.checkAndReportPoint(qx, qy1, qy2, dirIsLeft, answer);

		while (((rpath.v.y > qy2) && (rpath.lc != null))
				|| ((rpath.v.y <= qy2) && (rpath.rc != null))) {
			rchanged = false;
			if ((rpath.v.y > qy2) && (rpath.lc != null)) {
				rpath = rpath.lc;
				rchanged = true;
			} else {

				if ((rpath.v.y <= qy2) && (rpath.rc != null)) {
					if (rpath.lc != null) rpath.lc.reportInSubtree(qx, dirIsLeft, answer);
					rpath = rpath.rc;
					rchanged = true;
				}
			}
			if (rchanged)
				rpath.checkAndReportPoint(qx, qy1, qy2, dirIsLeft, answer);
		}

		while (((lpath.v.y >= qy1) && (lpath.lc != null))
				|| ((lpath.v.y < qy1) && (lpath.rc != null))) {
			lchanged = false;
			if ((lpath.v.y >= qy1) && (lpath.lc != null)) {
				if (lpath.rc != null) lpath.rc.reportInSubtree(qx, dirIsLeft, answer);
				lpath = lpath.lc;
				lchanged = true;
			} else {
				if ((lpath.v.y < qy1) && (lpath.rc != null)) {
					lpath = lpath.rc;
					lchanged = true;
				}
			}
			if (lchanged)
				lpath.checkAndReportPoint(qx, qy1, qy2, dirIsLeft, answer);
		}

		if ((!lpath.equals(vsplit)) && (lpath.rc != null))
			lpath.rc.reportInSubtree(qx, dirIsLeft, answer);
		if ((!rpath.equals(vsplit)) && (rpath.lc != null))
			rpath.lc.reportInSubtree(qx, dirIsLeft, answer);
	}
}