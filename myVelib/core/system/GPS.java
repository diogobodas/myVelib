package system;

/**
 * 
 * Class for representing 2D coordinate on a map.
 *
 */
public class GPS {

	private double x;
	private double y;
	
	/**
	 * Basic constructor with x and y coordinates
	 * @param x Double for x-axis coordinate
	 * @param y Double for y-axis coordinate
	 */
	public GPS(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method for calculating the distance between two points p1 and p2
	 * @param p1 GPS coordinate 1
	 * @param p2 GPS coordinate 2
	 * @return Double with the euclidean distance between the two points.
	 */
	public static double distance(GPS p1,GPS p2) {
		return Math.sqrt(Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2));
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return ("(" + this.x + ", " + this.y + ")"); 
	}
	
	
	
}
