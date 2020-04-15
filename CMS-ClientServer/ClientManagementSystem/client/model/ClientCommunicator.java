package Ex6Task2Files.client.model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Ex6Task2Files.client.control.ClientController;
import Ex6Task2Files.dataTypes.*;

public class ClientCommunicator {
    private Socket clientSocket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private ClientController ct;
    
    /**
     * Connects the client application to the server at the specified IP address and port number.
     * @param hostName The IP address of the server.
     * @param port The port number of the server.
     */
    public void connect(String hostName, int port) {
    	try{
            clientSocket = new Socket(hostName, port);
            openStreams();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Assigns a controller object to the model.
     * @param ct The controller object to assign.
     */
    public void assignController(ClientController ct) {
    	this.ct = ct;
    }

    
    
    /**
     * Notifies the server of the client application closing, and closes all relevant
     * data streams.
     */
    public void closeAll() {
    	try{
            socketOut.writeObject(new Command("QUIT", null));
            socketOut.close();
            socketIn.close();
        } catch (IOException e) {
            System.err.println("Unable to close IO streams...");
            e.printStackTrace();
        } catch(NullPointerException ne){
    	    ne.printStackTrace();
        }
    }

    /**
     * Creates a search Command to the server and queries for an ArrayList of clients, 
     * then returns that list as an Object array to the controller.
     * @param command The type of search to conduct ("Client ID", "Last Name", or "Client Type").
     * @param text The argument of the search.
     * @return The Object array of matching clients.
     */
	public Object[] searchDB(String command, String text) {
		Command searchCommand = new Command("SEARCH: " + command, text);
		sendCommand(searchCommand);
		
		return receiveClients();
	}

	/**
	 * Sends an add Command to the server, and then sends the new client information.
	 * Waits for a confirmation Command from the server.
	 * @param client The client to add.
	 */
	public void addClient(Client client) {
		Command addCommand = new Command("ADD", null);
		sendCommand(addCommand);
		sendClient(client);
		
		Command incoming = receiveCommand();
		if(incoming.getCommandType().contains("SUCCESS")) {
			int newID = Integer.parseInt(incoming.getSearchArg());
			ct.displayAddConfirmationDialog(newID);
		}
		else
			ct.displayFailDialog();
	}

	/**
	 * Sends a delete Command to the server with the client ID of the client to delete.
	 * Waits for a confirmation Command from the server.
	 * @param clientId The client ID to delete.
	 */
	public void deleteClient(int clientId) {
		Command delCommand = new Command("DELETE", Integer.toString(clientId));
		sendCommand(delCommand);
		
		Command incoming = receiveCommand();
		if(incoming.getCommandType().contains("SUCCESS")) {
			int delID = Integer.parseInt(incoming.getSearchArg());
			ct.displayDeleteConfirmationDialog(delID);
		}
		else
			ct.displayFailDialog();
	}
	
	/**
	 * Sends a modify Command to the server, then sends the updated client information.
	 * Waits for a confirmation Command from the server.
	 * @param client The Client object with updated information.
	 */
	public void modifyClient(Client client) {
		Command modCommand = new Command("MODIFY", null);
		sendCommand(modCommand);
		sendClient(client);
		
		Command incoming = receiveCommand();
		if(incoming.getCommandType().contains("SUCCESS")) {
			int modID = Integer.parseInt(incoming.getSearchArg());
			ct.displayModifyConfirmationDialog(modID);
		}
		else
			ct.displayFailDialog();
	}
	
	/**
     * Closes the input and output streams.
     */
    private void shutDown(){
        try {
            socketOut.close();
            socketIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a serialized Command object to the server.
     * @param command The Command object to send.
     */
    private void sendCommand(Command command){
        try {
            socketOut.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sends a serialized Client object to the server.
     * @param client The Client object to send.
     */
    private void sendClient(Client client) {
    	try {
            socketOut.writeObject(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Receives a serialized Command object from the server.
     * @return A Command object.
     */
    private Command receiveCommand(){
        try {
            return (Command) socketIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Receives an serialized ArrayList of Client objects from the server and converts that into
     * an array of Objects.
     * @return An array of clients as a generalized Object array.
     */
	private Object[] receiveClients(){
    	ArrayList<Client> incomingClients;
        try {
        	incomingClients = (ArrayList<Client>) socketIn.readObject();
        	return incomingClients.toArray();
        } catch(ClassNotFoundException ce) {
        	System.err.println("Unable to match class: ArrayList<Client>.");
        } catch(IOException e) {
        	System.err.println("Unexpected IO Exception.");
        } catch(NullPointerException ne) {
        	System.err.println("No results found.");
        }
        return null;
    }

	/**
	 * Opens the input and output streams to and from the server.
	 */
    private void openStreams(){
        try{
            socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
            socketIn = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to open IO streams...");
            e.printStackTrace();
        }
    }
}
