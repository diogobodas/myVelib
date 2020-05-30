package station;

/**
 * 
 * Enum type containing possible status for parking slot. A ParkingSlot can be FREE (without bikes), OCCUPIED (with bike), or OUT_OF_ORDER (unavailable for user, regardless of having bike or not)
 *
 */
public enum SlotStatus {
	OCCUPIED, FREE, OUT_OF_ORDER
}
