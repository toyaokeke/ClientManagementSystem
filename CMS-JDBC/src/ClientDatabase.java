import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDatabase {
	
	public Connection jdbc_connection;
	public Statement statement;
	public PreparedStatement stmt;
	public String databaseName = "InventoryDB", tableName = "ClientTable", dataFile = "clients.txt";
	
	public String connectionInfo = "jdbc:mysql://localhost:3306/clientdb",  
				  login          = "charlesaccess",
				  password       = "student";

	public ClientDatabase()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}

	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" +
				     "ID INT(4) NOT NULL AUTO_INCREMENT, " +
				     "FIRSTNAME VARCHAR(20) NOT NULL, " + 
				     "LASTNAME VARCHAR(20) NOT NULL, " + 
				     "ADDRESS VARCHAR(50) NOT NULL, " + 
				     "POSTALCODE CHAR(7) NOT NULL, " + 
				     "PHONENUMBER CHAR(12) NOT NULL," +
				     "CLIENTTYPE CHAR(1) NOT NULL," +
				     "PRIMARY KEY ( id ))";
		try{
			stmt = jdbc_connection.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
			System.out.println("Created Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void removeTable()
	{
		String sql = "DROP TABLE " + tableName;
		try{
			stmt = jdbc_connection.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
			System.out.println("Removed Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void fillTable()
	{
		try{
			Scanner sc = new Scanner(new FileReader(dataFile));
			while(sc.hasNext())
			{
				String clientInfo[] = sc.nextLine().split(";");
				addItem(new Client(clientInfo[0], clientInfo[1], clientInfo[2],
						            clientInfo[3], clientInfo[4], clientInfo[5]));
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + dataFile + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addItem(Client client)
	{
		String sql = " insert into " + tableName + " (FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CLIENTTYPE) values (?, ?, ?, ?, ?, ?)";
		try{
			stmt = jdbc_connection.prepareStatement(sql);
			
			stmt.setString(1, client.getFirstName());
			stmt.setString(2, client.getLastName());
			stmt.setString(3, client.getAddress());
			stmt.setString(4, client.getPostalCode());
			stmt.setString(5, client.getPhoneNumber());
			stmt.setString(6, client.getClientType());
			
			stmt.executeUpdate();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void removeItem(int clientId) {
		String sql = " delete from " + tableName + " where ID=?";
		try{
			stmt = jdbc_connection.prepareStatement(sql);
			
			stmt.setInt(1, clientId);
			
			stmt.executeUpdate();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void modifyItem(Client client) {
		//String clientId = Integer.toString(client.getID());
		String sql = " update " + tableName + " set FIRSTNAME=?, LASTNAME=?, ADDRESS=?, POSTALCODE=?, PHONENUMBER=?, CLIENTTYPE=? where ID=?";
		
		try{
			stmt = jdbc_connection.prepareStatement(sql);
			
			stmt.setString(1, client.getFirstName());
			stmt.setString(2, client.getLastName());
			stmt.setString(3, client.getAddress());
			stmt.setString(4, client.getPostalCode());
			stmt.setString(5, client.getPhoneNumber());
			stmt.setString(6, client.getClientType());
			stmt.setInt(7, client.getID());
			
			stmt.executeUpdate();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Client> searchClient(String searchType, String searchParameter) 
	{
		String query = null;
		ResultSet client;
		ArrayList <Client> searchList = new ArrayList<Client>();
		int idSearch = 0;
		if(searchType == "Client Type") {
			query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CLIENTTYPE FROM " + tableName + " WHERE CLIENTTYPE=" + "?";
		}
		if(searchType == "Client Id") {
			try {
				idSearch = Integer.parseInt(searchParameter);
			}
			catch(NumberFormatException e){
			}
			query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CLIENTTYPE FROM " + tableName + " WHERE ID=" + "?";
		}
		if(searchType == "Client Last Name") {
			query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CLIENTTYPE FROM " + tableName + " WHERE LASTNAME=" + "?";
		}
		try {
			stmt = jdbc_connection.prepareStatement(query); 
			if(searchType == "Client Type")
				stmt.setString(1, searchParameter); 
			if(searchType == "Client Id")
				stmt.setInt(1, idSearch);
			if(searchType == "Client Last Name")
				stmt.setString(1, searchParameter);
		    client = stmt.executeQuery(); 
			while(client.next())
			{
				searchList.add(new Client(client.getInt("ID"),client.getString("FIRSTNAME"),
											client.getString("LASTNAME"), client.getString("CLIENTTYPE")));
			}
			stmt.close();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return searchList;
	}

	/**
	 * Remove later
	 */
	public Client searchClient(int clientID)
	{
		//String sql = "SELECT * FROM " + tableName + " WHERE ID=" + toolID;
		String query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CLIENTTYPE FROM " + tableName + " WHERE ID=" + "?";
		ResultSet client;
		try {
			stmt = jdbc_connection.prepareStatement(query); // create a statement
		    stmt.setInt(1, clientID); // set input parameter
		    client = stmt.executeQuery(); // extract data from the ResultSet
			while(client.next())
			{
				return new Client(client.getString("FIRSTNAME"),
								client.getString("LASTNAME"), 
								client.getString("ADDRESS"), 
								client.getString("POSTALCODE"), 
								client.getString("PHONENUMBER"),
								client.getString("CLIENTTYPE"));
			}
			stmt.close();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	// Prints all the items in the database to console
	public void printTable()
	{
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();		//10
			ResultSet tools = statement.executeQuery(sql);		//11
			System.out.println("Tools:");
			while(tools.next())
			{
				System.out.println(tools.getInt("ID") + " " + 
								   tools.getString("TOOLNAME") + " " + 
								   tools.getInt("QUANTITY") + " " + 
								   tools.getDouble("PRICE") + " " + 
								   tools.getInt("SUPPLIERID"));
			}
			tools.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}