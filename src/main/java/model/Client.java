package model;
/**Represents and implements a client.*/
public class Client {
	private int arrivalTime;
	private int serviceTime;
	private int finishTime;
	private int clientNo;
	
	/**Constructor for client.
	 * @param time - represents arrival time
	 * @param service - represents service time
	 * @param client - represents client ID*/
	public Client (int time, int service, int client) {
		this.arrivalTime = time;
		this.serviceTime = service;
		this.finishTime = 0; 
		this.clientNo = client;
	}
	
	/**Gets arrival time.
	 * @return arrivalTime*/
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	/**Gets service time.
	 * @return serviceTime*/
	public int getServiceTime() {
		return this.serviceTime;
	}
	
	/**Gets finish time.
	 * @return finishTime*/
	public int getFinishTime() {
		return this.finishTime;
	}
	
	/**Gets client ID.
	 * @return clientNo*/
	public int getClientNo() {
		return this.clientNo;
	}
	
	/**Sets finish time.*/
	public void setFinishTime(int waitingTime) {
		this.finishTime = this.arrivalTime + this.serviceTime + waitingTime;
	}

}
