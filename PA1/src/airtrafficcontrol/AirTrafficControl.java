package airtrafficcontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Simulates an Air Traffic Control (ATC) Airplane List which uses a Max Heap Priority Queue to sort
 * Airplanes by Approach Codes (AC) assigned to them based on their current Distance from runway
 * and Elevation relative to the ground or whenever the ATC assigns a new AC to any Airplane.
 * @author Jerom Estrada
 *
 */
public class AirTrafficControl implements ActionListener{
	/**
	 * Returns the parent of the given index.
	 * @param index of the current element.
	 * @return the parent, (index-1)/2.
	 */
	public static int parent(int index)
	{
		return (index-1)/2;
	}
	/**
	 * Returns the left child of the given index.
	 * @param index of the current element.
	 * @return the left child, 2 * index + 1.
	 */
	public static int left(int index)
	{
		return 2 * index + 1;
	}
	/**
	 * Returns the right child of the given index.
	 * @param index of the current element.
	 * @return the right child, 2 * index + 2.
	 */
	public static int right(int index)
	{
		return 2 * index + 2;
	}
	/**
	 * Exchanges the information of the two Airplanes.
	 * @param airplane1 to be exchanged with airplane2.
	 * @param airplane2 to be exchanged with airplane1.
	 */
	public static void exchange(Airplane airplane1, Airplane airplane2)
	{
		/*
		 * The information of the airplane1 should be stored separately
		 * and assigned separately because the approach code might be
		 * an increased value, and the Airplane constructor calculates
		 * the approach code based on the distance and elevation.
		 */
		String tempFlightNum = airplane1.getFlightNumber();
		int tempDistance = airplane1.getDistance();
		int tempElevation = airplane1.getElevation();
		int tempAC = airplane1.getApproachCode();
		/*
		 * Copy airplane2's information into airplane1.
		 */
		airplane1.setFlightNumber(airplane2.getFlightNumber());
		airplane1.setDistance(airplane2.getDistance());
		airplane1.setElevation(airplane2.getElevation());
		airplane1.setApproachCode(airplane2.getApproachCode());
		/*
		 * Set airplane2's information with the saved temps.
		 */
		airplane2.setFlightNumber(tempFlightNum);
		airplane2.setDistance(tempDistance);
		airplane2.setElevation(tempElevation);
		airplane2.setApproachCode(tempAC);
	}
	/**
	 * Heapifies the Airplane in the given index following the Max Heap Property.
	 * @param airplanes is the Airplane Heap that is being passed in.
	 * @param index of the Airplane being Heapified.
	 */
	public static void maxAirplaneHeapify(AirplaneHeap airplanes, int index)
	{
		int left = left(index);
		int right = right(index);
		int largest = index;
		if(left < airplanes.getAirplaneHeapSize() && airplanes.getAC(left) > airplanes.getAC(index))
		{
			largest = left;
		}
		else
		{
			largest = index;
		}
		if(right < airplanes.getAirplaneHeapSize() && airplanes.getAC(right) > airplanes.getAC(largest))
		{
			largest = right;
		}
		if(largest != index)
		{
			exchange(airplanes.getAirplane(index), airplanes.getAirplane(largest));
			maxAirplaneHeapify(airplanes, largest);
		}
	}
	/**
	 * Builds a Max Heap out of the Airplane Heap.
	 * @param airplanes is the Airplane Heap being passed in.
	 */
	public static void buildMaxAirplaneHeap(AirplaneHeap airplanes)
	{
		int size = airplanes.getAirplaneList().size();
		airplanes.setAirplaneHeapSize(size);
		for(int i = size/2; i  >= 0; i--)// Size/2 is the index of the last parent to heapify.
		{
			maxAirplaneHeapify(airplanes, i);
		}
	}
	/**
	 * Sorts the Airplane Heap using a heapsort algorithm.
	 * @param airplanes is the Airplane Heap being passed in.
	 */
	public static void AirplaneHeapSort(AirplaneHeap airplanes)
	{
		buildMaxAirplaneHeap(airplanes);
		for(int i = airplanes.getArraySize()-1; i >= 1; i--)
		{
			exchange(airplanes.getAirplane(0), airplanes.getAirplane(i)); // Exchange the max with the last element
			airplanes.setAirplaneHeapSize(airplanes.getAirplaneHeapSize()-1); // Reduce the heap size by 1.
			maxAirplaneHeapify(airplanes, 0); // Heapify the element put in the beginning to its proper spot.
		}
	}
	/**
	 * Gets the Maximum Airplane based on its Approach Code in the Airplane Heap.
	 * @param airplanes is the Airplane Heap being passed in.
	 * @return the Airplane with the Max Approach Code.
	 */
	public static Airplane AirplaneHeapMaximum(AirplaneHeap airplanes)
	{
		return airplanes.getAirplane(0); // simply return the first element of the max heap.
	}
	/**
	 * Extracts the Maximum Airplane based on its Approach Code from the Airplane Heap.
	 * @param airplanes is the Airplane Heap being passed in.
	 * @return the Airplane with the Max Approach Code.
	 */
	public static Airplane AirplaneHeapExtractMax(AirplaneHeap airplanes)
	{
		// If the heap is less than 0, then there was an underflow.
		if(airplanes.getAirplaneHeapSize() < 0)
		{
			throw new RuntimeException("HeapUnderflowError");
		}
		Airplane max = airplanes.getAirplane(0); // save the max.
		Airplane last = airplanes.getAirplane(airplanes.getAirplaneHeapSize()-1); // take the last element of the heap.
		airplanes.getAirplaneList().set(0, last); // set the first element to the last.
		airplanes.setAirplaneHeapSize(airplanes.getAirplaneHeapSize()-1); // reduce the heap size.
		airplanes.updateArray();
		maxAirplaneHeapify(airplanes, 0); // Heapify the first element to its proper spot.
		return max;
	}
	/**
	 * Increases the AC of the Airplane with the given index in the Airplane Heap.
	 * @param airplanes is the Airplane Heap being passed in.
	 * @param index of the Airplane.
	 * @param newAC for the Airplane with the given index.
	 */
	public static void AirplaneHeapIncreaseKey(AirplaneHeap airplanes, int index, int newAC)
	{
		// If the new Approach Code is smaller than the current one, throw an error.
		if(newAC < airplanes.getAC(index))
		{
			throw new RuntimeException("SmallerIncreaseError");
		}
		airplanes.getAirplane(index).setApproachCode(newAC);
		while(index > 0 && airplanes.getAC(parent(index)) < airplanes.getAC(index))
		{
			exchange(airplanes.getAirplane(index), airplanes.getAirplane(parent(index)));
			index = parent(index);
		}
	}
	/**
	 * Inserts a new Airplane into the max heap while maintaining the max heap property.
	 * @param airplanes is the Airplane heap.
	 * @param newAirplane to be added into the Airplane heap.
	 */
	public static void maxAirplaneHeapInsert(AirplaneHeap airplanes, Airplane newAirplane)
	{
		int approachCode = newAirplane.getApproachCode();
		airplanes.addToAirplaneHeap(newAirplane);
		airplanes.getAirplane(airplanes.getAirplaneHeapSize()-1).setApproachCode(Integer.MIN_VALUE);
		AirplaneHeapIncreaseKey(airplanes, airplanes.getAirplaneHeapSize()-1, approachCode);
	}
	/**
	 * Creates a random flight number using the array of flight numbers.
	 * @return a random flight number.
	 */
	public static String createRandomFlight()
	{
		String flight = "";
		Random rand = new Random();
		int index = rand.nextInt(44);
		int firstNum = rand.nextInt(10);
		int secondNum = rand.nextInt(10);
		return flight + flightNumbers[index] + firstNum + secondNum;
	}
	/**
	 * Creates an Airplane with a given flight number and generates random distance and elevation.
	 * @param flightNum for this new Airplane.
	 * @return an Airplane with a randomly generated distance and elevation along with an Approach Code.
	 */
	public static Airplane createAirplane(String flightNum)
	{
		Random rand = new Random();
		int distanceToRunway = rand.nextInt(17001) + 3000; // [3000...20000]
		int elevation = rand.nextInt(2001) + 1000; // [1000...3000]
		return new Airplane(flightNum.toUpperCase(), distanceToRunway, elevation);
	}
	/**
	 * Specifically generates 30 random Airplanes.
	 * @param airplanes is the Airplane Heap that the 30 Airplanes will be added into.
	 */
	public static void thirtyFlights(AirplaneHeap airplanes)
	{
		Random rand = new Random();
		for(int i = 0; i < 30; i++)
		{
			String flight = createRandomFlight(); // create a random flight number.
			int distanceToRunway = rand.nextInt(17001) + 3000; // [3000...20000]
			int elevation = rand.nextInt(2001) + 1000; // [1000...3000]
			airplanes.addToAirplaneHeap(new Airplane(flight, distanceToRunway, elevation));	
		}
	}
	/**
	 * Prints the entire ArrayList inside the Airplane Heap in descending order despite any heap size.
	 * This is necessary for printing the sorted elements because the heap size is zero at that time.
	 * @param airplanes is the Airplane Heap to be printed in a descending order.
	 * @return
	 */
	public static String printArray(AirplaneHeap airplanes)
	{
		String printed = "";
		for(int i = airplanes.getArraySize()-1, j = 1; i >= 0; i--, j++)
		{
			Airplane airline = airplanes.getAirplaneList().get(i);
			printed += "  " + j + ". ( " + airline.getFlightNumber() +", D: "+ airline.getDistance() + 
					" meters, H: " + airline.getElevation()+ " meters ) - AC: " + airline.getApproachCode();
			if(i != 0)
			{
				printed += " \n";
			}
		}
		return printed;
	}
	/**
	 * Prints the Airplane Heap while it's in max heap form.
	 * @param airplanes is the Airplane Heap to be printed in max heap order.
	 * @return
	 */
	public static String printAirplaneHeap(AirplaneHeap airplanes)
	{
		String printed = "";
		for(int i = 0; i < airplanes.getArraySize(); i++)
		{
			Airplane airline = airplanes.getAirplaneList().get(i);
			printed += "  " + (i+1) + ". ( " + airline.getFlightNumber() +", D: "+ airline.getDistance() + 
					" meters, H: " + airline.getElevation()+ " meters ) - AC: " + airline.getApproachCode();
			if(i != airplanes.getArraySize()-1)
			{
				printed += " \n";
			}
		}
		return printed;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * The Following are the logic behind the buttons in the GUI.
		 * Each button performs a specific task to display or modify the list.
		 */
		// Add Button action
		if(e.getSource() == addButton && !flightNumText.getText().equals(""))
		{
			buildMaxAirplaneHeap(airplaneHeap); // Make sure it's a max heap.
			maxAirplaneHeapInsert(airplaneHeap, createAirplane(flightNumText.getText())); // Insert the new Airplane.
			AirplaneHeapSort(airplaneHeap); // Sort right away for printing the updated list.
			airplanesTextArea.setText(printArray(airplaneHeap)); // Displays the updated list in the GUI.
			airplanesTextArea.setCaretPosition(0); // This makes sure that the scroll bar, if ever, stays on the top.
			buildMaxAirplaneHeap(airplaneHeap); // Revert into the max heap form
												// since the Airplanes are stored in a Max Heap.
			flightNumText.setText(""); // Resets the text field that took the new flight number.
		}
		// Generate New List Button action.
		else if(e.getSource() == generateButton)
		{
			airplaneHeap.clearAirplaneHeap(); // Clear the current Airplane Heap.
			thirtyFlights(airplaneHeap); // Generate another 30 random flights.
			AirplaneHeapSort(airplaneHeap); // Sort the new list.
			airplanesTextArea.setText(printArray(airplaneHeap)); // Display the new list into the GUI.
			airplanesTextArea.setCaretPosition(0); // makes sure the the scroll bar is at the top.
			buildMaxAirplaneHeap(airplaneHeap); // Build a Max Heap out of the list.
		}
		// Peek Button action.
		else if(e.getSource() == peekButton && !airplanesTextArea.getText().equals(""))
		{
			buildMaxAirplaneHeap(airplaneHeap); // Put the list in a Max Heap form.
			Airplane max = AirplaneHeapMaximum(airplaneHeap); // Store the max Airplane.
			// Display the appropriate messages based on the max Airplane.
			message.setText("The next plane to land is "+ max.getFlightNumber() + " with AC - " + max.getApproachCode());
			details.setText("Distance: " + max.getDistance() + " m  |  Elevation: " + max.getElevation() + " m");
		}
		// Remove Button action.
		else if(e.getSource() == removeMaxButton)
		{
			// If the display in the GUI is not empty.
			if(!airplanesTextArea.getText().equals(""))
			{
				buildMaxAirplaneHeap(airplaneHeap); // Put the list in a Max Heap form.
				Airplane max = AirplaneHeapExtractMax(airplaneHeap); // Extract the max and save.
				AirplaneHeapSort(airplaneHeap); // Sort the new list.
				airplanesTextArea.setText(printArray(airplaneHeap)); // Displays the sorted list into the GUI.
				airplanesTextArea.setCaretPosition(0); // Makes sure the scroll bar is at the top.
				// Displays the message.
				message.setText("                  " + max.getFlightNumber() + " is removed from the list");
				details.setText(""); // Resets the lower part of the messages from previous displays.
				buildMaxAirplaneHeap(airplaneHeap); // Store the list into a max heap.
			}
			else
			{
				// Resets the messages if button is pressed and display is empty.
				message.setText("");
				details.setText("");
			}
		}
		// Increase Button action.
		else if(e.getSource() == increaseButton && !indexText.getText().equals("") && !newACText.getText().equals(""))
		{	// If the index and new approach code text fields are not empty, execute the following.
			int index = Integer.parseInt(indexText.getText()); // Parses the index text into an int.
			int newAC = Integer.parseInt(newACText.getText()); // Parses the approach code text into an int.
			if(index >= 1 && index <= airplaneHeap.getArraySize()) // if the index is a valid index.
			{
				try // Try this block first since the new Approach Code can be smaller than the current one.
				{	// The increase key method throws an exception in that case so this will handle it.
					buildMaxAirplaneHeap(airplaneHeap);
					AirplaneHeapIncreaseKey(airplaneHeap, index-1, newAC);
					AirplaneHeapSort(airplaneHeap);
					airplanesTextArea.setText(printArray(airplaneHeap));
					airplanesTextArea.setCaretPosition(0);
					message.setText("               New Approach Code Assigned");
					details.setText("");
					indexText.setText("");
					newACText.setText("");
					buildMaxAirplaneHeap(airplaneHeap);
				}
				catch(RuntimeException error)
				{	// If the new Approach Code is in fact smaller than the current one.
					message.setText("New Approach Code is smaller than current"); // Display this message in the GUI.
					details.setText(""); // Reset any unnecessary messages.
				}
			}
		}
		// View Heap Button action.
		else if(e.getSource() == viewAirplaneHeapButton)
		{
			buildMaxAirplaneHeap(airplaneHeap); // Put the list in a Max Heap form.
			airplanesTextArea.setText(printAirplaneHeap(airplaneHeap)); // Display the Max Heap into the GUI.
			airplanesTextArea.setCaretPosition(0); // Make sure that the scroll bar is at the top.
			// Display the appropriate messages.
			message.setText("List is currently in AirplaneHeap View");
			details.setText("");
		}
	}
	// GUI objects that will be used in the main program.
	private static JPanel panel;
	private static JFrame frame;
	private static JLabel flightNumLabel;
	private static JTextField flightNumText;
	private static JLabel airplanesLabel;
	private static JTextArea airplanesTextArea;
	private static JScrollPane airplanesScrollPane;
	private static JButton addButton;
	private static JLabel indexLabel;
	private static JTextField indexText;
	private static JLabel newACLabel;
	private static JTextField newACText;
	private static JButton increaseButton;
	private static JButton viewAirplaneHeapButton;
	private static JLabel viewNoteLabel;
	private static JButton generateButton;
	private static JButton removeMaxButton;
	private static JButton peekButton;
	private static JLabel message;
	private static JLabel details;
	private static AirplaneHeap airplaneHeap;
	/*
	 * List of flight numbers that Airplanes have. Based on the list
	 * provided in a wiki search about flight numbers.
	 * It is static and outside the main so that the methods can use it.
	 */
	static String[] flightNumbers = new String[] { 
			"AM", "AC", "QK", "NZ", "TN",
			"DJ", "AS", "NH", "AA", "CP",
			"BG", "BA", "CI", "DL", "DA",
			"LY", "EK", "EY", "FX", "AY",
			"HA", "JL", "NU", "JB", "LJ",
			"KE", "LA", "LO", "LH", "MH",
			"MM", "QF", "QR", "SK", "SQ",
			"BC", "WN", "SG", "NK", "TK",
			"UA", "UP", "VA", "WS"
	};
	// Main program that will display the GUI
	public static void main(String [] args)
	{
		airplaneHeap = new AirplaneHeap(); // Create an empty AirplaneHeap using the default constructor.
		thirtyFlights(airplaneHeap); // Generate 30 airplanes and store them into the AirplaneHeap.
		
		// Generate the frame of the GUI.
		panel = new JPanel();
		panel.setLayout(null);
		frame = new JFrame("  Air Traffic Control Simulation");
		frame.setLocation(300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.setResizable(false);
		frame.add(panel);
		
		// Airplanes Landing Sequence label
		airplanesLabel = new JLabel("Airplanes Landing Sequence: ");
		airplanesLabel.setBounds(25, 25, 200, 25);
		panel.add(airplanesLabel);
		
		// Generate New List Button
		generateButton = new JButton("Generate New List");
		generateButton.setBounds(200, 25, 170, 25);
		generateButton.addActionListener(new AirTrafficControl());
		panel.add(generateButton);
		
		// Text Area where the list will be displayed onto.
		airplanesTextArea = new JTextArea("");
		airplanesTextArea.setBounds(20, 70, 355, 260);
		airplanesTextArea.setEditable(false);
		panel.add(airplanesTextArea);
		
		// Scroll pane
		airplanesScrollPane = new JScrollPane(airplanesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		airplanesScrollPane.setBounds(20, 70, 355, 260);
		panel.add(airplanesScrollPane);
		
		// Flight Number label
		flightNumLabel = new JLabel("Flight Number: ");
		flightNumLabel.setBounds(40, 350, 100, 25);
		panel.add(flightNumLabel);
		
		// Text field for the Flight Number.
		flightNumText = new JTextField("");
		flightNumText.setBounds(140, 350, 80, 25);
		panel.add(flightNumText);
		
		// Add Flight Button.
		addButton = new JButton("Add Flight");
		addButton.setBounds(240, 350, 110, 25);
		addButton.addActionListener(new AirTrafficControl());
		panel.add(addButton);
		
		// Index label.
		indexLabel = new JLabel("Index: ");
		indexLabel.setBounds(40, 390, 50, 25);
		panel.add(indexLabel);
		
		// Text field for the Index.
		indexText = new JTextField("");
		indexText.setBounds(85, 390, 25, 25);
		panel.add(indexText);
		
		// Approach Code label.
		newACLabel = new JLabel("New AC: ");
		newACLabel.setBounds(120, 390, 50, 25);
		panel.add(newACLabel);
		
		// Text field for the new Approach Code.
		newACText = new JTextField("");
		newACText.setBounds(175, 390, 85, 25);
		panel.add(newACText);
		
		// Increase Button.
		increaseButton = new JButton("Increase");
		increaseButton.setBounds(275, 390, 85, 25);
		increaseButton.addActionListener(new AirTrafficControl());
		panel.add((increaseButton));
		
		// View Heap Button.
		viewAirplaneHeapButton = new JButton("View Heap");
		viewAirplaneHeapButton.setBounds(40, 420, 100, 25);
		viewAirplaneHeapButton.addActionListener(new AirTrafficControl());
		panel.add(viewAirplaneHeapButton);
		
		// Note label.
		viewNoteLabel = new JLabel("Note: Must View Heap Prior to Increase");
		viewNoteLabel.setBounds(150, 420, 230, 25);
		panel.add(viewNoteLabel);
		
		// Peek Button.
		peekButton = new JButton("Peek");
		peekButton.setBounds(240, 460, 85, 25);
		peekButton.addActionListener(new AirTrafficControl());
		panel.add(peekButton);
		
		// Remove Top Airplane Button.
		removeMaxButton = new JButton("Remove Top Airplane");
		removeMaxButton.setBounds(40, 460, 170, 25);
		removeMaxButton.addActionListener(new AirTrafficControl());
		panel.add(removeMaxButton);
		
		// Message label, used for general messages.
		message = new JLabel("");
		message.setBounds(60, 500, 300, 25);
		panel.add(message);
		
		// Details label, used for additional details.
		details = new JLabel("");
		details.setBounds(70, 520, 300, 25);
		panel.add(details);
		
		// Make the frame visible to the user.
		frame.setVisible(true);
	}
}
