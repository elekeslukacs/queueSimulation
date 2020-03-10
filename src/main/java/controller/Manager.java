package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Client;
import model.Queue;
import model.Scheduler;
import view.GUI;

/**Implements Manager, starts the threads and controls the simulation.*/

public class Manager extends Thread {
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	
	private int time = 100;
	private int maxServiceTime = 10;
	private int minServiceTime = 5;
	private int numberOfQueues = 3;
	private int numberOfClients = 100;
	private int minArrivalTime = 2;
	private int maxArrivalTime = 4;
	private int currentTime = 0;
	private static Manager m;
	private Scheduler scheduler;
	private ArrayList<Client> clients;
	private ArrayList<Client> servedClients;
	//private GUI gui;
	
	/**Constructor. ActionListeners for the buttons are implemented, takes information from the view, starts the simulation.*/
	public Manager() {
		
		GUI.getGui().getSaveDataButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try{
				time = GUI.getGui().getSimulationTime();
				maxServiceTime = GUI.getGui().getServiceMax();
				minServiceTime = GUI.getGui().getServiceMin();
				numberOfQueues = GUI.getGui().getQueue();
				numberOfClients = GUI.getGui().getClient();
				minArrivalTime = GUI.getGui().getTimeMin();
				maxArrivalTime = GUI.getGui().getTimeMax();
				}
				catch(NumberFormatException e) {
//					e.printStackTrace();
				}
				
			}
			
		});
		
		
		GUI.getGui().getStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//m = new Manager();
				clients = new ArrayList<Client>();
				servedClients = new ArrayList<Client>();
				m.interrupt();
				scheduler = new Scheduler(numberOfQueues);
				for(Queue q : scheduler.getQueues()) {
				q.start();
				}
				generateClients(numberOfClients);
				m.start();
			}
		});
		
		GUI.getGui().getStopButton().addActionListener(new ActionListener() {
			//Set current time to bigger than final time to stop the thread
			public void actionPerformed(ActionEvent e) {
				currentTime = time + 2;
				for(Queue q : scheduler.getQueues()) {
					q.closeQueue();
				}
				
			}
		});

	}
	
	/**Generates a random number in the interval [min, max].
	 * @return num - Integer in the interval [min, max]*/
	private static int randBetween(int min, int max) {
		Random rand = new Random();
		int num = rand.nextInt((max - min) + 1) + min;
		return num;
	}
	
	/**Generate n random clients setting their arrival and service time.
	 * @param n - The number of clients*/
	private void generateClients(int n) {
		int arrival;
		int service;
		arrival = randBetween(this.minArrivalTime, this.maxArrivalTime);
		service = randBetween(this.minServiceTime, this.maxServiceTime);
		Client c = new Client(arrival ,service, 0);
		this.clients.add(c);
		for(int i = 1; i < numberOfClients; i++) {
			arrival = clients.get(i-1).getArrivalTime() + randBetween(this.minArrivalTime, this.maxArrivalTime);
			service = randBetween(this.minServiceTime, this.maxServiceTime);
			clients.add(new Client(arrival, service, i));
		}
	}
	
	/**Starts simulation, while the current time is less than the final time, the thread runs. After that displays statistics.*/
	public void run() {
		while(currentTime < time) {
			try {
				System.out.println(currentTime);
				LOGGER.log(Level.INFO, "Time " + currentTime);
				Client c = clients.get(0);
				
			if(c.getArrivalTime() == currentTime) {
				GUI.getGui().results.setText("");
				System.out.println("Client "  + c.getClientNo() + " arrives at time " + c.getArrivalTime() + " with service time " + c.getServiceTime());
				GUI.getGui().addText("Client "  + c.getClientNo() + " arrives at time " + c.getArrivalTime() + " with service time " + c.getServiceTime() + "\n");
				LOGGER.log(Level.INFO, "Client "  + c.getClientNo() + " arrives at time " + c.getArrivalTime() + " with service time " + c.getServiceTime() + "\n");
				
				String result = scheduler.dispatchClient(c);
				GUI.getGui().addText(result);
				
				servedClients.add(c);
				clients.remove(0);
				
				for(Queue q : this.scheduler.getQueues()) {
					GUI.getGui().addText(q.getName() + ": " + q.toString());
				}
				
			}
				sleep(1000);
				currentTime++;
			} catch (InterruptedException e) {
			}
		}

		System.out.println("\n");
		getServedClientNumber();
		getAvgWaitingTime();
		getPeakHour();
		
	}
	
	/**Displays the number of clients each queue served.*/
	public void getServedClientNumber() {
		for(Queue q : scheduler.getQueues()) {
			try {
				GUI.getGui().addText("At " + q.getName() + " " + q.getClientCounter() + " clients were served.\n");
				LOGGER.log(Level.INFO, "At " + q.getName() + " " + q.getClientCounter() + " clients were served.\n");
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	/**Calculates the average waiting time. Displays it on the GUI.
	 * @return avg - The average value of the clients' waiting times.*/
	public double getAvgWaitingTime() {
		double avg = 0;
		if(!this.servedClients.isEmpty()) {
			for(Client c : this.servedClients) {
				avg += (c.getFinishTime() - c.getArrivalTime());
			}
			avg /= this.servedClients.size();
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		GUI.getGui().addText("\nAverage waiting time: " + df.format(avg) + " minutes.\n");
		LOGGER.log(Level.INFO,"Average waiting time: " + df.format(avg) + " minutes.\n");
		return avg;
	}
	
	/**Calculates and displays the peak hour.*/
	public void getPeakHour() {
		int peakHour = 0;
		int last = 0;
		int current = 1;
		boolean change = false;
		
		for(int i = 1; i<this.servedClients.size(); i++) {
			if(servedClients.get(i).getArrivalTime()/60 > servedClients.get(i-1).getArrivalTime()/60) {
				if(current > last) {
					last = current;
					peakHour = servedClients.get(i-1).getArrivalTime()/60;
					change = true;
				}
				current = 0;
			}
			else {
				current++;
			}
			if(i == servedClients.size()) {
				if(current > last) {
					last = current;
					peakHour = servedClients.get(i-1).getArrivalTime()/60;
				}
			}
		}
		if(!change) {
			last = current;
		}
		GUI.getGui().addText("\nPeak hour was the " + (peakHour+1) + ". hour since queues were opened. " + last + " clients were in total. \n");
	}
	
	/**Main function, starts the application.*/
	public static void main (String[] args) {
		
		m = new Manager();
	}
}
