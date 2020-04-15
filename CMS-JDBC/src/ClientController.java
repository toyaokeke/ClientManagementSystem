import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class ClientController {
	private ClientMS clientSystem;
	private ClientDatabase clientDB;
	
	public ClientController (ClientMS cs, ClientDatabase cdb) {
		clientSystem = cs;
		clientDB = cdb;
		
		clientSystem.registerRadioButtonListener1(new RadioListener1());
		clientSystem.registerRadioButtonListener2(new RadioListener2());
		clientSystem.registerRadioButtonListener3(new RadioListener3());
		
		clientSystem.registerTextAreaListener(new TextListener());
		clientSystem.registerModifyListeners(new ModifyListener());
		clientSystem.closeWIndow(new CloseWindow());
		clientSystem.clientIdMessage(new ClientIdMessage());
	}
	
	class RadioListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == clientSystem.clientId)
			{
				clientSystem.registerSearchListener2(new SearchButtonListener2());
			}
		}
	}
	
	class SearchButtonListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientSystem.searchButton && clientSystem.clientId.isSelected())
			{
				ArrayList<Client> search = clientDB.searchClient("Client Id", clientSystem.searchBox.getText());
				Object[] clientList = search.toArray();
				clientSystem.textArea.setListData(clientList);
			}
			if(e.getSource() == clientSystem.clearSearch) {
				/**
				 * See if you can find a better way to do this 
				 */
				ArrayList<String> emptyField = new ArrayList<>();
				Object[] b = emptyField.toArray(); // emptyField.toArray();
				clientSystem.textArea.setListData(b);
			}
		}	
	}
	
	class RadioListener2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == clientSystem.lastName)
			{
				clientSystem.registerSearchListener3(new SearchButtonListener3());
			}
		}
	}
	
	class SearchButtonListener3 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientSystem.searchButton && clientSystem.lastName.isSelected())
			{
				ArrayList<Client> search = clientDB.searchClient("Client Last Name", clientSystem.searchBox.getText());
				Object[] clientList = search.toArray();
				clientSystem.textArea.setListData(clientList);
			}
			if(e.getSource() == clientSystem.clearSearch) {
				/**
				 * See if you can find a better way to do this 
				 */
				ArrayList<String> emptyField = new ArrayList<>();
				Object[] b = emptyField.toArray();
				clientSystem.textArea.setListData(b);
			}
		}
	}
	
	class RadioListener3 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientSystem.clientType) 
			{
				clientSystem.registerSearchListener1(new SearchButtonListener1());
			}
		}
	}
	
	class SearchButtonListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == clientSystem.searchButton && clientSystem.clientType.isSelected())
			{
				//System.out.println("Search Button Clicked");
				//System.out.println(clientSystem.searchBox.getText());
				ArrayList<Client> search = clientDB.searchClient("Client Type", clientSystem.searchBox.getText());
				Object[] clientList = search.toArray();
				clientSystem.textArea.setListData(clientList);
			}
			if(e.getSource() == clientSystem.clearSearch) {
				
				/**
				 * See if you can find a better way to do this 
				 */
				ArrayList<String> emptyField = new ArrayList<>();
				Object[] b = emptyField.toArray();
				clientSystem.textArea.setListData(b);
			}
		}
	}
	
	class ModifyListener implements ActionListener{

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
				JOptionPane.showMessageDialog(null, "The postal code enetered is too short. Make sure your postal code"
						+ " is in the format A1A 1A1");
			}
			
			return test;
		}
		
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
				JOptionPane.showMessageDialog(null, "The phone number enetered is too short. Make sure the phone number"
						+ " is in the format 111-111-1111");
			}
			return test;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clientSystem.eb1) {
				String firstName = clientSystem.et2.getText();
				String lastName = clientSystem.et3.getText();
				String address = clientSystem.et4.getText();
				String postalCode = clientSystem.et5.getText();
				String phoneNumber = clientSystem.et6.getText();
				String clientType = (String)clientSystem.dropDown.getSelectedItem();
				
					if(!(checkPostalCode(postalCode))) {
						JOptionPane.showMessageDialog(null, "An invalid postal code was entered. Ensure your postal code follows"
								+ "the convention: A1A 1A1");
					}
					else if (!(checkPhoneNumber(phoneNumber))) {
							JOptionPane.showMessageDialog(null, "An invalid phone number was entered. Ensure your phone number follows"
								+ "the convention: 111-111-1111");
					}
					else if(firstName.length() > 20 || lastName.length() >20) {
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
							clientDB.addItem(client);
					}
			}
			
			if (e.getSource() == clientSystem.eb2) {
				try{
					int clientId = Integer.parseInt(clientSystem.et1.getText());
					clientDB.removeItem(clientId);
				}
				catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "The requested operation cannot be performed. Please ensure "
							+ " that all relevant fields have been entered accurately ");
				}
			}
		
			if (e.getSource() == clientSystem.eb3) {
				try {
					int clientId = Integer.parseInt(clientSystem.et1.getText());
					String firstName = clientSystem.et2.getText();
					String lastName = clientSystem.et3.getText();
					String address = clientSystem.et4.getText();
					String postalCode = clientSystem.et5.getText();
					String phoneNumber = clientSystem.et6.getText();
					String clientType = (String)clientSystem.dropDown.getSelectedItem();
					if(!(checkPostalCode(postalCode))) {
						JOptionPane.showMessageDialog(null, "An invalid postal code was entered. Ensure your postal code follows"
								+ "the convention: LALALA");
					}
					else if (!(checkPhoneNumber(phoneNumber))) {
						JOptionPane.showMessageDialog(null, "An invalid phone number was entered. Ensure your phone number follows"
								+ " the convention: 111-111-1111");
					}
					else if(firstName.length() > 20 || lastName.length() >20) {
						JOptionPane.showMessageDialog(null, "The names entered were too long. Client names cannot exceed 20 letters");
					}
					else if(address.length() > 50) {
						JOptionPane.showMessageDialog(null, "The address entered was too long. Client address cannot exceed 50 characters");
					}
					else {
						Client client  = new Client(clientId, firstName, lastName, address, postalCode, phoneNumber, clientType);
						clientDB.modifyItem(client);
					}
				}
			catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "The requested operation can not be performed. Please ensure that all relevant"
						+ " fields have been entered accurately");
			}
		}
			
			if (e.getSource() == clientSystem.eb4) {
				clientSystem.et1.setText("");
				clientSystem.et2.setText("");
				clientSystem.et3.setText("");
				clientSystem.et4.setText("");
				clientSystem.et5.setText("");
				clientSystem.et6.setText("");
				clientSystem.dropDown.setSelectedItem(" ");	
			}
		}	
	}
	
	class TextListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			try{
				String item = clientSystem.textArea.getSelectedValue().toString();
				String[] item1 = item.split(" ");
				int clientID = Integer.parseInt(item1[0]);
				
				Client client = clientDB.searchClient(clientID);
				clientSystem.et1.setText(item1[0]);
				clientSystem.et2.setText(client.getFirstName());
				clientSystem.et3.setText(client.getLastName());
				clientSystem.et4.setText(client.getAddress());
				clientSystem.et5.setText(client.getPostalCode());
				clientSystem.et6.setText(client.getPhoneNumber());
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
	
	class CloseWindow implements WindowListener{
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			try {
				clientDB.jdbc_connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Disconneted from Database");
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
	
	class ClientIdMessage implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			JOptionPane.showMessageDialog(null, "WARNING!!! The Client ID has to be assigned by the system");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
