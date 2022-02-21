package airtrafficcontrol;
/**
 * 
 * @author Jerom Estrada
 * Models an Airplane that has a flight number, distance to the runway 
 * and current elevation that can be assigned an approach code.
 *
 */
public class Airplane {
	/**
	 * Creates a new Airplane using the given flightNumber, distance and elevation.
	 * @param flightNumber of this Airplane.
	 * @param distance of this Airplane to the runway.
	 * @param elevation of this Airplane.
	 */
	public Airplane(String flightNumber, int distance, int elevation)
	{
		this.flightNumber = flightNumber;
		this.distance = distance;
		this.elevation = elevation;
		calculateApproachCode();
	}
	/**
	 * Gets the flight number of this Airplane.
	 * @return flightNumber.
	 */
	public String getFlightNumber()
	{
		return flightNumber;
	}
	/**
	 * Sets the flight number of this Airplane to the new flight number.
	 * @param newFlightNumber to be set for this Airplane.
	 */
	public void setFlightNumber(String newFlightNumber)
	{
		flightNumber = newFlightNumber;
	}
	/**
	 * Gets the distance of this Airplane.
	 * @return distance
	 */
	public int getDistance()
	{
		return distance;
	}
	/**
	 * Sets the distance of this Airplance to the new distance.
	 * @param newDistance to be set for this Airplane.
	 */
	public void setDistance(int newDistance)
	{
		distance = newDistance;
	}
	/**
	 * Gets the elevation of this Airplane.
	 * @return elevation.
	 */
	public int getElevation()
	{
		return elevation;
	}
	/**
	 * Sets the elevation of this Airplane to the new elevation.
	 * @param newElevation to be set for this Airplane.
	 */
	public void setElevation(int newElevation)
	{
		elevation = newElevation;
	}
	/**
	 * Calculates the Approach Code of this Airplane based on the distance and elevation.
	 */
	public void calculateApproachCode()
	{
		approachCode = 15000 - ((distance + elevation) / 2);
	}
	/**
	 * Gets the Approach Code of this Airplane
	 * @return
	 */
	public int getApproachCode()
	{
		return approachCode;
	}
	/**
	 * Sets the Approach Code of this Aiplane to the new Approach Code.
	 * @param newApproachCode
	 */
	public void setApproachCode(int newApproachCode)
	{
		approachCode = newApproachCode;
	}
	
	private String flightNumber;
	private int distance;
	private int elevation;
	private int approachCode;

}
