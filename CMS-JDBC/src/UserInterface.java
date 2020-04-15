import javax.swing.JFrame;

public class UserInterface {
	
	public static void main (String [] args){
		
		ClientMS view = new ClientMS();
		ClientDatabase model = new ClientDatabase();
		ClientController controller = new ClientController (view, model);
	/**
	 * Uncomment to create and fill the table
	 */
//		model.createTable();
//		model.fillTable();
		
		String test = view.searchBox.getText();
		System.out.println(test);
	}
}
