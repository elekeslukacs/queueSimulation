package model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Implements scheduler, which creates queues and dispatches clients.*/

public class Scheduler {
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	private ArrayList<Queue> queues;
	private int noOfQueues;
	
	/**Create noOfQueues queue instances.
	 * @param noOfQueues - Number of queues*/
	public Scheduler (int noQueues) {
		this.noOfQueues = noQueues;
		this.queues = new ArrayList<Queue>();
		for(int i = 0; i < this.noOfQueues; i++) {
			Queue q = new Queue("Q" + i);
			//q.setDaemon(true);
			this.queues.add(q);
		}
	}
	
	/**Dispatches a client to the queue with smallest waiting time and sets the client's finish time./
	 * @param Client c - Client to be dispatched.
	 * @return result - String representing a message about the dispatching of the client.*/
	public String dispatchClient(Client c) {
		int index = 0;
		try {
			int min = this.queues.get(0).getWaitingTime();
			for(int i = 1; i<this.noOfQueues; i++) {
				int aux = queues.get(i).getWaitingTime();
				if(aux < min) {
					min = aux;
					index = i;
				}
			}
			
			c.setFinishTime(min); // set finish time for the client;
			queues.get(index).addClient(c);
			System.out.println("Client " + c.getClientNo() + " goes to queue " + index);
			LOGGER.log(Level.INFO, "Client " + c.getClientNo() + " goes to queue " + index + "\n" );
		}
		catch (InterruptedException e) {
		}
		String result = "";
		result = "Client " + c.getClientNo() + " goes to queue " + index + "\n\n";
		return result;
	}
	
	/**Returns the list of queues.
	 * @return queues */
	public ArrayList<Queue> getQueues(){
		return this.queues;
	}

}
