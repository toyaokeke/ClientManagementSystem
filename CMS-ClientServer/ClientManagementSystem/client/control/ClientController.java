package Ex6Task2Files.client.control;

import Ex6Task2Files.client.view.*;
import Ex6Task2Files.client.model.*;
import Ex6Task2Files.dataTypes.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientController {
	private ClientGUI clientGUI;
	private ClientCommunicator clientModel;

	/**
	 * Parameter constructor for the client, assigns a view and a model to the controller.
	 * @param cv The client view object, in control of the gui.
	 * @param cm The client model object, in control of client-server communications.
	 */
	public ClientController(ClientGUI cv, ClientCommunicator cm) {
		clientGUI = cv;
		clientModel = cm;

		clientGUI.registerRadioButtonListener1(new RadioListener1());
		clientGUI.registerRadioButtonListener2(new RadioListener2());
		clientGUI.registerRadioButtonListener3(new RadioListener3());

		clientGUI.registerTextAreaListener(new TextListener());
		clientGUI.registerModifyListeners(new ModifyListener());
		clientGUI.closeWIndow(new CloseWindow());
	}

	/**
	 * Clears the search results in the client view.
	 */
	private void clearSearchResults(){
		ArrayList<String> emptyField = new ArrayList<>();
		Object[] b = emptyField.toArray(); // emptyField.toArray();
		clientGUI.textArea.setListData(b);
	}
	
	/**
	 * The radio button listener for "Client ID" button.
	 */
	class RadioListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == clientGUI.clientId)
			{
				clientGUI.registerSearchListener(new SearchButtonListener2());
			}
		}
	}

	/**
	 * The search button listener for when the "Client ID" radio button is active.
	 */
	class SearchButtonListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientGUI.searchButton && clientGUI.clientId.isSelected())
			{
				clientGUI.textArea.setListData(clientModel.searchDB("Client ID", clientGUI.searchBox.getText()));
			}
			if(e.getSource() == clientGUI.clearSearch) {
				clearSearchResults();
			}
		}
	}
	
	/**
	 * The radio button listener for "Last Name" button.
	 */
	class RadioListener2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == clientGUI.lastName)
			{
				clientGUI.registerSearchListener(new SearchButtonListener3());
			}
		}
	}

	/**
	 * The search button listener for when the "Last Name" radio button is active.
	 */
	class SearchButtonListener3 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientGUI.searchButton && clientGUI.lastName.isSelected())
			{
				clientGUI.textArea.setListData(clientModel.searchDB("Last Name", clientGUI.searchBox.getText()));
			}
			if(e.getSource() == clientGUI.clearSearch) {
				clearSearchResults();
			}
		}
	}

	/**
	 * The radio button listener for "Client Type" button.
	 */
	class RadioListener3 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientGUI.clientType)
			{
				clientGUI.registerSearchListener(new SearchButtonListener1());
			}
		}
	}

	/**
	 * The search button listener for when the "Client Type" radio button is active.
	 */
	class SearchButtonListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientGUI.searchButton && clientGUI.clientType.isSelected())
			{
				//System.out.println("Search Button Clicked");
				//System.out.println(clientGUI.searchBox.getText());
				clientGUI.textArea.setListData(clientModel.searchDB("Client Type", clientGUI.searchBox.getText()));
				
			}
			if(e.getSource() == clientGUI.clearSearch) {
				clearSearchResults();
			}
		}
	}

	/**
	 * Monitors the four buttons on the bottom right of the GUI: "Add", "Delete", "Modify", and "Clear".
	 */
	class ModifyListener implements ActionListener{

		/**
		 * Checks if the inputed postal code is of the format "A1A 1A1".
		 * @param postalCode The postal code to check.
		 * @return True if the postal code follows the correct format.
		 */
		public boolean checkPostalCode(String postalCode) {
			String[] check = postalCode.split("");
			boolean test = false;
			try {
				String numbers = check[1]+check[4]+check[6];
				String letters = check[0]+check[2]+check[5];
				if(check.length == 7 && numbers.matches("^[0-9]*$") && letters.matches("[a-zA-Z]+") && check[3].equals(" ")) {
					test = true;
				}
				else
					test = false;
			}
			catch(ArrayIndexOutOfBoundsException e) {
				
			}

			return test;
		}

		/**
		 * Checks if the inputed phone number is of the format "123-456-7890".
		 * @param phoneNumber The phone number to check.
		 * @return True if the phone number follows the correct format.
		 */
		public boolean checkPhoneNumber(String phoneNumber) {
			String[] check = phoneNumber.split("");
			boolean test = false;

			try{
				String numbers = check[0]+check[1]+check[2]+check[4]+check[5]+check[6]+check[8]+check[9]+check[10]+check[11];
				if(check.length == 12 && numbers.matches("^[0-9]*$") && check[3].equals("-") && check[7].equals("-")) {
					test = true;
				}
				else {
					test = false;
				}
			}
			catch (ArrayIndexOutOfBoundsException e) {
				
			}
			return test;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clientGUI.eb1) {
				String firstName = clientGUI.et2.getText();
				String lastName = clientGUI.et3.getText();
				String address = clientGUI.et4.getText();
				String postalCode = clientGUI.et5.getText();
				String phoneNumber = clientGUI.et6.getText();
				String clientType = (String)clientGUI.dropDown.getSelectedItem();

					if(!(checkPostalCode(postalCode))) {
						JOptionPane.showMessageDialog(null, "An invalid postal code was entered. Ensure your postal code follows"
								+ "the convention: A1A 1A1");
					}
					else if (!(checkPhoneNumber(phoneNumber))) {
							JOptionPane.showMessageDialog(null, "An invalid phone number was entered. Ensure your phone number follows"
								+ "the convention: 111-111-1111");
					}
					else if(firstName.length() > 20 && lastName.length() >20) {
						JOptionPane.showMessageDialog(null, "The names entered were too long. Client names cannot exceed 20 letters");
					}
					else if(address.length() > 50) {
						JOptionPane.showMessageDialog(null, "The address entered was too long. Client address cannot exceed 50 characters");
					}
					else if(clientType.equals(" ")) {
						JOptionPane.showMessageDialog(null, "Please enter a valid client type");
					}
					else {
							Client client  = new Client(firstName, lastName, address, postalCode, phoneNumber, clientType);
							clientModel.addClient(client);
					}
			}

			if (e.getSource() == clientGUI.eb2) {
				try{
					int clientId = Integer.parseInt(clientGUI.et1.getText());
					clientModel.deleteClient(clientId);
				}
				catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "The requested operation cannot be performed. Please ensure "
							+ " that all relevant fields have been entered accurately ");
				}
			}

			if (e.getSource() == clientGUI.eb3) {
				try {
					int clientId = Integer.parseInt(clientGUI.et1.getText());
					String firstName = clientGUI.et2.getText();
					String lastName = clientGUI.et3.getText();
					String address = clientGUI.et4.getText();
					String postalCode = clientGUI.et5.getText();
					String phoneNumber = clientGUI.et6.getText();
					String clientType = (String)clientGUI.dropDown.getSelectedItem();
					if(!(checkPostalCode(postalCode))) {
						JOptionPane.showMessageDialog(null, "An invalid postal code was entered. Ensure your postal code follows"
								+ "the convention: A1A 1A1");
					}
					else if (!(checkPhoneNumber(phoneNumber))) {
						JOptionPane.showMessageDialog(null, "An invalid phone number was entered. Ensure your phone number follows"
								+ " the convention: 111-111-1111");
					}
					else if(firstName.length() > 20 && lastName.length() >20) {
						JOptionPane.showMessageDialog(null, "The names entered were too long. Client names cannot exceed 20 letters");
					}
					else if(address.length() > 50) {
						JOptionPane.showMessageDialog(null, "The address entered was too long. Client address cannot exceed 50 characters");
					}
					else {
						Client client  = new Client(clientId, firstName, lastName, address, postalCode, phoneNumber, clientType);
						clientModel.modifyClient(client);
					}
				}
			catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "The requested operation can not be performed. Please ensure that all relevant"
						+ " fields have been entered accurately");
			}
		}

			if (e.getSource() == clientGUI.eb4) {
				clientGUI.et1.setText("");
				clientGUI.et2.setText("");
				clientGUI.et3.setText("");
				clientGUI.et4.setText("");
				clientGUI.et5.setText("");
				clientGUI.et6.setText("");
				clientGUI.dropDown.setSelectedItem(" ");
			}
		}
	}

	/**
	 * Listens to if the user selects any clients from the list on the bottom left of the GUI.
	 */ 
	class TextListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			try{
				Client selectedClient = (Client) clientGUI.textArea.getSelectedValue();
				
				clientGUI.et1.setText(Integer.toString(selectedClient.getID()));
				clientGUI.et2.setText(selectedClient.getFirstName());
				clientGUI.et3.setText(selectedClient.getLastName());
				clientGUI.et4.setText(selectedClient.getAddress());
				clientGUI.et5.setText(selectedClient.getPostalCode());
				clientGUI.et6.setText(selectedClient.getPhoneNumber());
				clientGUI.dropDown.setSelectedItem(selectedClient.getClientType());
			}
			catch(NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "There is no value in the text box");
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}

	/**
	 * Listens to the GUI closing event from the view object.
	 */
	class CloseWindow implements WindowListener{
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			clientModel.closeAll();
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
	}
	
	/**
	 * Tells the model object to connect to a specified IP address at the default port (9999).
	 * @param serverAddress The server IP address to connect to.
	 */
	public void connectToServer(String serverAddress) {
		clientModel.connect(serverAddress, 9999);
	}
	
	/**
	 * Tells the view object to display a dialog box polling for the user to enter the server
	 * IP address.
	 * @return The server IP address.
	 */
	public String getServerAddress() {
		return clientGUI.pollForServerAddress();
	}
	
	/**
	 * Tells the view object to display a confirmation dialog box for the addition of data.
	 * @param newID The ID assigned to the new data.
	 */
	public void displayAddConfirmationDialog(int newID) {
		clientGUI.displayConfirmationDialog("Addition", newID);
	}

	/**
	 * Tells the view object to display a confirmation dialog box for the deletion of data.
	 * @param delID The ID of the data deleted.
	 */
	public void displayDeleteConfirmationDialog(int delID) {
		clientGUI.displayConfirmationDialog("Deletion", delID);
	}
	
	/**
	 * Tells the view object to display a confirmation dialog box for the modification of data.
	 * @param modID The ID of the data modified.
	 */
	public void displayModifyConfirmationDialog(int modID) {
		clientGUI.displayConfirmationDialog("Modify", modID);
	}

	/**
	 * Tells the view object to display a error dialog box for the failure of operation.
	 */
	public void displayFailDialog() {
		clientGUI.displayFailDialog();
	}
	
	public static void main(String[] args) {
		ClientGUI cg = new ClientGUI();
		ClientCommunicator cm = new ClientCommunicator();
		ClientController ct = new ClientController(cg, cm);
		
		String serverAddress = ct.getServerAddress();
		ct.connectToServer(serverAddress);
		cm.assignController(ct);
	}

}
