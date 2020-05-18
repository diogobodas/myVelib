package bike;

public abstract class Bike {
	private int id;
	private static int last_id;
	
	// Bikes all have a unique id that counts upwards for both electric an regular bike
	public Bike() {
		this.id = Bike.last_id + 1;
		Bike.last_id += 1;
	}
	
	public int getID() {
		return this.id;
	}
	
}
