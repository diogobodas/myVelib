package system;

public class GPS {

	private double x;
	private double y;
	
	GPS(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public static double distance(GPS p1,GPS p2) {
		return Math.sqrt(( p1.getX() - p2.getX()) + (p1.getY() - p2.getY()));
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
	
	
	
}
