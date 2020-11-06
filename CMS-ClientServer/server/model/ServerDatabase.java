package server.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import dataTypes.Client;

public class ServerDatabase {

	private ArrayList<Client> clientList;
	private int latestID;

	/**
	 * Default constructor that creates a new ArrayList of clients The first client
	 * will have ID 1
	 */
	public ServerDatabase() {
		clientList = new ArrayList<Client>();
		latestID = 1;
	}

	/**
	 * A synchronized method that adds a client to the database
	 * 
	 * @param client new client
	 * @return returns the new client's ID number
	 */
	public synchronized int addClient(Client client) {
		client.setID(latestID);
		clientList.add(client);
		int returnInt = latestID;
		latestID++;
		return returnInt;
	}

	/**
	 * A synchronized method that removes a client based on ID number
	 * 
	 * @param clientID client ID
	 * @return True if delete was successful, false otherwise
	 */
	public synchronized boolean deleteClient(int clientID) {
		for (Client c : clientList) {
			if (c.getID() == clientID) {
				clientList.remove(c);
				return true;
			}
		}
		return false;
	}

	/**
	 * A synchronized method that modifies the information of a client
	 * 
	 * @param client the modified client
	 * @return True if modification successful, false otherwise
	 */
	public synchronized boolean modifyClient(Client client) {
		for (Client c : clientList) {
			if (c.compareTo(client) == 0) {
				clientList.remove(c);
				clientList.add(client);
				Collections.sort(clientList);
				return true;
			}
		}
		return false;
	}

	/**
	 * A synchronized method that searches the database based on the client search
	 * parameter
	 * 
	 * @param searchType client search type
	 * @param arg        client search parameter
	 * @return returns the search result
	 */
	public synchronized ArrayList<Client> searchDB(String searchType, String arg) {
		ArrayList<Client> searchResult = new ArrayList<Client>();
		if (searchType.equals("Client ID")) {
			try {
				searchResult = searchByID(Integer.parseInt(arg));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else if (searchType.equals("Last Name")) {
			searchResult = searchByLastName(arg);
		} else if (searchType.equals("Client Type")) {
			searchResult = searchByClientType(arg);
		}
		return searchResult;
	}

	/**
	 * Creates initial database from text file
	 * 
	 * @param filename name of file
	 */
	public void createTableFromFile(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String clientLine;
			String[] clientInfo;
			while ((clientLine = reader.readLine()) != null) {
				clientInfo = clientLine.split(";");
				Client importC = new Client(clientInfo[0], clientInfo[1], clientInfo[2], clientInfo[3], clientInfo[4],
						clientInfo[5]);
				addClient(importC);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find initial setup client list.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not read from initial setup client list.");
			e.printStackTrace();
		}
	}

	/**
	 * Search database by client ID
	 * 
	 * @param id client ID
	 * @return returns ArrayList containing matching client
	 */
	private ArrayList<Client> searchByID(int id) {
		ArrayList<Client> matching = new ArrayList<Client>();
		for (Client c : clientList) {
			if (c.getID() == id) {
				matching.add(c);
				break;
			}
		}
		return matching;
	}

	/**
	 * Searches database by last name
	 * 
	 * @param lastName last name of client
	 * @return returns ArrayList of clients with matching last name
	 */
	private ArrayList<Client> searchByLastName(String lastName) {
		ArrayList<Client> matching = new ArrayList<Client>();
		for (Client c : clientList) {
			if (c.getLastName().equals(lastName))
				matching.add(c);
		}
		return matching;
	}

	/**
	 * Searches database by client type
	 * 
	 * @param clientType type of client
	 * @return return ArrayList of clients with the matching client type
	 */
	private ArrayList<Client> searchByClientType(String clientType) {
		ArrayList<Client> matching = new ArrayList<Client>();
		for (Client c : clientList) {
			if (c.getClientType().equals(clientType))
				matching.add(c);
		}
		return matching;
	}
}