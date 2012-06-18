import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Visual extends Canvas {
	private int x1, x2, y, qx, qy1, qy2;
	private boolean clear = true;
	private boolean first = true;
	private boolean query = false;
	private List<HorizontalInterval> L = new ArrayList<HorizontalInterval>();
	private List<Point> points = new ArrayList<Point>();
	private List<HorizontalInterval> answer = new ArrayList<HorizontalInterval>();

	public Visual() {
		super();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!query) {
					if (first) {
						x1 = e.getX();
						y = e.getY();
						first = false;
					} else {
						x2 = e.getX();
						HorizontalInterval interval = new HorizontalInterval(
								Math.min(x1, x2), Math.max(x1, x2), y);
						L.add(interval);
						points.add(new Point(x1, y, interval));
						points.add(new Point(x2, y, interval));
						first = true;
						repaint();
					}
				} else {
					if (first) {
						qx = e.getX();
						qy1 = e.getY();
						first = false;
					} else {
						qy2 = e.getY();
						first = true;
						Collections.sort(points, new Xcomp());
						IntervalTree InterTree = new IntervalTree(L, points);
						answer.clear();
						InterTree.queryIntervalTree(qx, Math.min(qy1, qy2), Math.max(qy1, qy2), answer);
						repaint();

					}
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					query = true;
				}
			}
		});
	}

	public void update(Graphics g) {
		if (!query) {
			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);
		} else {
			HorizontalInterval interval;
			if (!clear) {
				g.clearRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.BLACK);
				for (int i = 0; i < L.size(); i++) {
					interval = L.get(i);
					g.drawLine(interval.x1, interval.y, interval.x2, interval.y);
				}
			}
			g.setColor(Color.RED);
			g.drawLine(qx, qy1, qx, qy2);
			g.setColor(Color.GREEN);
			for (int i = 0; i < answer.size(); i++) {
				interval = answer.get(i);
				g.drawLine(interval.x1, interval.y, interval.x2, interval.y);
			}
			clear = false;
		}

	}

	public static void main(String s[]) {
		final Frame f = new Frame("Draw");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.dispose();
			}
		});
		f.setSize(400, 300);

		final Canvas c = new Visual();
		f.add(c);

		f.setVisible(true);
	}
}