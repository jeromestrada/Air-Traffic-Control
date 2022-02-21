package airtrafficcontrol;

import java.util.ArrayList;
/**
 * Models a heap of Airplanes that can be sorted.
 * @author Jerom Estrada
 *
 */
public class AirplaneHeap {
	/**
	 * Creates an empty heap of Airplanes.
	 */
	public AirplaneHeap()
	{
		airplanes = new ArrayList<Airplane>();
		arraySize = 0;
		heapSize = 0;
	}
	/**
	 * Creates a heap of Airplanes using the passed in ArrayList of Airplanes.
	 * @param airplanes is array list that will be used for the heap.
	 */
	public AirplaneHeap(ArrayList<Airplane> airplanes)
	{
		this.airplanes = airplanes;
		heapSize = airplanes.size();
		arraySize = airplanes.size();
	}
	/**
	 * Gets the size of this Airplane heap.
	 * @return heapSize
	 */
	public int getAirplaneHeapSize()
	{
		return heapSize;
	}
	/**
	 * Sets the size of this Airplane heap.
	 * @param newHeapSize to be set for this Airplane heap.
	 */
	public void setAirplaneHeapSize(int newHeapSize)
	{
		heapSize = newHeapSize;
	}
	/**
	 * Gets the size of the Array List holding the heap in this Airplane Heap.
	 * @return arraySize.
	 */
	public int getArraySize()
	{
		return arraySize;
	}
	/**
	 * Adds the new Airplane to this Airplane heap.
	 * @param newAirplane to be added into the heap.
	 */
	public void addToAirplaneHeap(Airplane newAirplane)
	{
		airplanes.add(heapSize, newAirplane);
		arraySize++;
		heapSize++;
	}
	/**
	 * Updates the size of the ArrayList based on the existing heap in this Airplane Heap.
	 */
	public void updateArray()
	{
		while(airplanes.size() > heapSize)
		{
			airplanes.remove(heapSize);
		}
		arraySize = heapSize; // This makes sure that there are no elements outside the heapSize
		// This is necessary to maintain the ArrayList. However, sorting the arraylist doesn't call this.
	}
	public void clearAirplaneHeap()
	{
		airplanes.removeAll(airplanes);
		heapSize = 0;
		arraySize = 0;
	}
	/**
	 * Gets the Approach Code of the Airplane in the given index.
	 * @param index of the Airplane to be fetched in this Airplane Heap.
	 * @return the Approach Code of the Airplane in the index.
	 */
	public int getAC(int index)
	{
		return airplanes.get(index).getApproachCode();
	}
	/**
	 * Gets the Airplane with the given index.
	 * @param index of the Airplane that is being fetched in this Airplane Heap.
	 * @return
	 */
	public Airplane getAirplane(int index)
	{
		return airplanes.get(index);
	}
	/**
	 * Gets the array list of Airplanes stored in this Airplane Heap.
	 * @return airplanes
	 */
	public ArrayList<Airplane> getAirplaneList()
	{
		return airplanes;
	}
		
	private ArrayList<Airplane> airplanes;
	private int heapSize;
	private int arraySize;
}
