package Ex6Task2Files.server.control;

import Ex6Task2Files.dataTypes.Client;
import Ex6Task2Files.dataTypes.Command;
import Ex6Task2Files.server.model.*;

import java.io.*;
import java.util.ArrayList;

public class ServerInstance implements Runnable {

	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private ServerDatabase serverDB;

	/**
	 * The default constructor that connects to the server database
	 * @param in input stream
	 * @param out output stream
	 * @param serverDB client-server database
	 */
	public ServerInstance(ObjectInputStream in, ObjectOutputStream out, ServerDatabase serverDB){
		socketIn = in;
		socketOut = out;
		this.serverDB = serverDB;
	}


	@Override
	/**
	 * This searches the database based on the Command that is sent by the user.
	 * Command can either be SEARCH, ADD, DELETE, MODIFY or QUIT
	 */
	public void run() {
		boolean running = true;
		while(running) {
			try {
				Command incoming = (Command) socketIn.readObject();
				boolean operationSuccess;
				Client incomingC;
				ArrayList<Client> searchResults;
				switch(incoming.getCommandType()) {
				case "SEARCH: Client ID":
					searchResults = serverDB.searchDB("Client ID", incoming.getSearchArg());
					sendResults(searchResults);
					break;
				case "SEARCH: Last Name":
					searchResults = serverDB.searchDB("Last Name", incoming.getSearchArg());
					sendResults(searchResults);
					break;
				case "SEARCH: Client Type":
					searchResults = serverDB.searchDB("Client Type", incoming.getSearchArg());
					sendResults(searchResults);
					break;
				case "ADD":
					incomingC = (Client) socketIn.readObject();
					int feedbackID = serverDB.addClient(incomingC);
					sendResponse("SUCCESS", Integer.toString(feedbackID));
					break;
				case "DELETE":
					operationSuccess = serverDB.deleteClient(Integer.parseInt(incoming.getSearchArg()));
					if(operationSuccess)
						sendResponse("SUCCESS", incoming.getSearchArg());
					else
						sendResponse("FAIL", null);
					break;
				case "MODIFY":
					incomingC = (Client) socketIn.readObject();
					operationSuccess = serverDB.modifyClient(incomingC);
					if(operationSuccess)
						sendResponse("SUCCESS", Integer.toString(incomingC.getID()));
					else
						sendResponse("FAIL", null);
					break;
				case "QUIT":
					System.out.println("Ending session...");
					running = false;
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}

		closeStreams();
	}

	/**
	 * This closes streams to the server
	 */
	private void closeStreams(){
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This sends a response command back to the client
	 * @param response response message
	 * @param idArg ID associated with command
	 */
	private void sendResponse(String response, String idArg) {
		try {
			socketOut.writeObject(new Command(response, idArg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the results of the command as an arraylist back to the client
	 * @param clients ArrayList of clients
	 */
	private void sendResults(ArrayList<Client> clients) {
		try {
			socketOut.writeObject(clients);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
