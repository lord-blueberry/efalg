package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;

/**
 * Represents an infinite line
 * 
 * @author Jon
 * 
 */
public class Line {
	private Point p;
	private Vector v;

	public Line(Point p, Vector v) {
		this.p = p;
		this.v = v;
	}

	/**
	 * Copy constructor
	 * 
	 * @param l
	 */
	public Line(Line l) {
		p = (Point) l.p.clone();
		v = new Vector(l.v);
	}

	/**
	 * get the angle which this line has to be turned in order to go through p1
	 * 
	 * @param p1
	 * @return angle in radiant
	 */
	public double calculateAngle(Point p1) {
		Vector rp1 = new Vector(p, p1);
		return v.getAngle(rp1);
	}

	/**
	 * Turns line so it goes through the point p
	 * 
	 * @param p
	 */
	public void turnLine(Point p) {
		this.v = new Vector(this.p, p);
		this.p = (Point) p.clone();
	}

	/**
	 * 
	 * @param angle
	 */
	public void turnLine(double angle) {

	}

	/**
	 * Get the intersection point with this line
	 * 
	 * @param l
	 * @return
	 */
	public Point calculateIntersectionPoint(Line l) {
		Vector s = this.v;
		Vector t;

		/*
		 * int sx = p2.x - p1.x; int sy = p2.y - p1.y; int tx = -(l.p2.x -
		 * l.p1.x); int ty = -(l.p2.y - l.p1.y);
		 * 
		 * int sm = p1.y - p1.x; int tm = l.p1.y - l.p1.x;
		 * 
		 * int main = sx * (ty) - sy * tx; int minort = tx * tm - ty * sm; int
		 * minors = sx * tm - sy * sm;
		 * 
		 * double s1 = -(double) minort / main; double t1 = (double) minors /
		 * main;
		 */

		return null;
	}

	/**
	 * 
	 * @param rectangle
	 *            Array of 4 lines. Line 0 and 2, and 1 and 3 are parallel.
	 * @return
	 */
	public static double calculateRectangleArea(Line[] rectangle) {
		return calculateRectangleArea(rectangle[0], rectangle[1], rectangle[2], rectangle[3]);
	}

	/**
	 * get rectangle area enclosed by the 4 lines
	 * 
	 * @param a
	 *            is parallel to c
	 * @param b
	 *            is parallel to d
	 * @param c
	 *            is
	 * @param d
	 *            is
	 * @return
	 */
	public static double calculateRectangleArea(Line a, Line b, Line c, Line d) {
		double da = a.getDistanceParallel(c);
		double dc = b.getDistanceParallel(d);

		return da * dc;
	}

	/**
	 * Calculates the distance between this line and par. par has to be parallel
	 * to this.
	 * 
	 * @param par
	 * @return
	 */
	private double getDistanceParallel(Line par) {
		Vector g0 = new Vector(p, par.p);

		return v.cross(g0) / v.calculateMagnitude();
	}
}
