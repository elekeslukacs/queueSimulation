package view;

import java.awt.GridLayout;

import javax.swing.*;

/**Implements graphical user interface. Singleton Class.*/

public class GUI {
	private JFrame mainFrame;
	private JLabel timeInterval;
	private JTextField timeMin;
	private JTextField timeMax;
	private JLabel serviceTime;
	private JTextField serviceMin;
	private JTextField serviceMax;
	private JLabel noOfQueues;
	private JTextField queueTextField;
	private JLabel noOfClients;
	private JTextField clientsTextField;
	private JLabel simTime;
	private JTextField simTextField;
	private JButton saveData;
	private JButton start;
	private JButton stop;
	public JTextArea results;
	private JScrollPane scrollResults;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private static GUI gui;
	
	/**Constructor, calls the preapareGUI method.*/
	private GUI() {
		prepareGUI();
	}
	
	/**Returns the only object created.*/
	public synchronized static GUI getGui() {
		if(gui == null) {
			gui = new GUI();
		}
		
		return gui;
	}
	
	/**Prepares the gui, initializes all the components.*/
	public void prepareGUI() {
		mainFrame = new JFrame("Queue Simulation");
		mainFrame.setSize(700, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new GridLayout(4,1));
		
		timeInterval = new JLabel("Arriving time between two customers:");
		timeMin = new JTextField(4);
		timeMax = new JTextField(4);
		noOfQueues = new JLabel("Queues No.:");
		queueTextField = new JTextField(4);
		panel1 = new JPanel();
		panel1.add(timeInterval);
		panel1.add(timeMin);
		panel1.add(timeMax);
		panel1.add(noOfQueues);
		panel1.add(queueTextField);
		
		serviceTime = new JLabel("Interval for service time:");
		serviceMin = new JTextField(4);
		serviceMax = new JTextField(4);
		noOfClients = new JLabel("Clients No.:");
		clientsTextField = new JTextField(4);
		simTime = new JLabel("Simulation time:");
		simTextField = new JTextField(4);
		panel2 = new JPanel();
		panel2.add(serviceTime);
		panel2.add(serviceMin);
		panel2.add(serviceMax);
		panel2.add(noOfClients);
		panel2.add(clientsTextField);
		panel2.add(simTime);
		panel2.add(simTextField);
		
		saveData = new JButton("Save Data");
		start = new JButton("Start");
		stop = new JButton("Stop");
		panel3 = new JPanel();
		panel3.add(saveData);
		panel3.add(start);
		panel3.add(stop);
		
		results = new JTextArea();
		results.setSize(700, 200);
		scrollResults = new JScrollPane(results);
		//scrollResults.setSize(600, 200);
		
		mainFrame.getContentPane().add(panel1);
		mainFrame.add(panel2);
		mainFrame.add(panel3);
		mainFrame.add(scrollResults);
		mainFrame.setVisible(true);
	}
	
	/**Checks if a string is a number or not.
	 * @return boolean value - True if number, false if other.*/
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	/**Adds text to the text area of the GUI.*/
	public void addText (String s) {
		this.results.append(s);
	}
	
	public JButton getSaveDataButton() {
		return saveData;
	}
	
	public JButton getStartButton() {
		return start;
	}
	
	public JButton getStopButton() {
		return stop;
	}
	
	//Getters that return integer values taken from different textfields.
	public int getTime() {
		try {
			int num = Integer.parseInt(this.timeInterval.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
//		return Integer.parseInt(this.timeInterval.getText());
	}
	
	public int getTimeMin() {
		try {
			int num = Integer.parseInt(this.timeMin.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.timeMin.getText());
	}
	
	public int getTimeMax() {
		try {
			int num = Integer.parseInt(this.timeMax.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.timeMax.getText());
	}
	
	public int getServiceMin() {
		try {
			int num = Integer.parseInt(this.serviceMin.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.serviceMin.getText());
	}
	
	public int getServiceMax() {
		try {
			int num = Integer.parseInt(this.serviceMax.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.serviceMax.getText());
	}
	
	public int getQueue() {
		try {
			int num = Integer.parseInt(this.queueTextField.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.queueTextField.getText());
	}
	
	public int getClient() {
		try {
			int num = Integer.parseInt(this.clientsTextField.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.clientsTextField.getText());
	}
	
	public int getSimulationTime() {
		try {
			int num = Integer.parseInt(this.simTextField.getText());
			return num;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Wrong format");
			throw new NumberFormatException("not number");
		}
		//return Integer.parseInt(this.simTextField.getText());
	}
//	public static void main (String[] args) {
//		GUI g = new GUI();
//	}
//	
	

}
