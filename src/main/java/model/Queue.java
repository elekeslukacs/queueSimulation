package model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.GUI;

/**Represents and implements a queue, extends Thread*/

public class Queue extends Thread{
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	private boolean open = true;
	private int waitingTime;
	private int clientCounter;
	private ArrayList<Client> clients;
	
	/**Constructor
	 * @param name - Name of the queue/thread*/
	public Queue(String name) {
		this.waitingTime = 0;
		this.clientCounter = 0;
		this.clients = new ArrayList<Client>();
		setName(name);
	}
	
	/**Adds client to the queue and increments waiting time and client counter.
	 * @param c - Client to be added
	 * @throws InterruptedException*/
	public synchronized void addClient(Client c) throws InterruptedException{
		this.clients.add(c);
		this.waitingTime += c.getServiceTime();
		this.clientCounter++;
		notifyAll();
	}
	
	/**Removes client from the queue.
	 * @throws InterruptedException
	 * @return c - Client removeds*/
	public synchronized Client removeClient() throws InterruptedException{
		while(this.clients.size() == 0) {
			wait();
		}
		Client c = this.clients.get(0);
		this.clients.remove(0);
		notifyAll();
		return c;
	}
	
	/**Thread, takes first clients, puts thread to sleep and decrements waiting time.*/
	public void run() {
		Client current;
		while(open) {
			
			try {
				current = removeClient();
				sleep(current.getServiceTime()*1000);
				this.waitingTime -= current.getServiceTime();
				
				LOGGER.log(Level.INFO,"Client " + current.getClientNo() + " was served at queue " + getName() + " leaves at " + current.getFinishTime() + "\n" );
				System.out.println("Clien t" + current.getClientNo() + " was served at queue " + getName() + " leaves at " + current.getFinishTime());
				GUI.getGui().addText("\nClient " + current.getClientNo() + " was served at queue " + getName() + " leaves at " + current.getFinishTime() + "\n");
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**Sets open to false in order to stop a thread.*/
	public synchronized void closeQueue() {
		this.open = false;
		//notifyAll();
	}
	
	/**Gets waiting time.
	 * @return waitingTime*/
	public synchronized int getWaitingTime() throws InterruptedException{
		notifyAll();
		return this.waitingTime;
		
	}
	
	/**Gets the client counter
	 * @return clientCounter*/
	public synchronized int getClientCounter() throws InterruptedException{
		notifyAll();
		return this.clientCounter;
	}
	
	/**String representation of queue
	 * @return result - String representing the queue*/
	public String toString() {
		String result = "";
		result = "" + this.clients.size() + " clients in the queue. Waiting time: " + this.waitingTime + "\n";
		return result;
	}
	
	

}
