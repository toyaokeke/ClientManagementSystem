
public class Client {

	private int id;
	private String firstName, lastName, address, postalCode, phoneNumber, clientType;
	
	
	public Client(String firstName, String lastName, String address, String postalCode, String phoneNumber, String clientType)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.clientType = clientType;
		
	}
	
	public Client(int id, String firstName, String lastName, String address, String postalCode, String phoneNumber, String clientType)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.clientType = clientType;
	}
	
	public Client(int id, String firstName, String lastName, String clientType)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.clientType = clientType;
		this.address = "";
		this.postalCode = "";
		this.phoneNumber = "";
		
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getPostalCode()
	{
		return postalCode;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public String getClientType()
	{
		return clientType;
	}
	
	public String toString1()
	{
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.clientType;
		return client;
	}
	
	public String toString()
	{
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.address + " " + this.postalCode + " " + this.phoneNumber + " " + this.clientType;
		return client;
	}
}
