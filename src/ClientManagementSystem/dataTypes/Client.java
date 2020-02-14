package Ex6Task2Files.dataTypes;

import java.io.Serializable;


public class Client implements Serializable, Comparable<Client> {

	private static final long serialVersionUID = 1L;
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
	
	public int getID()
	{
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
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
	
	public String toStringShortDescription()
	{
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.clientType;
		return client;
	}
	
	public String toStringLongDescription()
	{
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.address + " " + this.postalCode + " " + this.phoneNumber + " " + this.clientType;
		return client;
	}
	
	@Override
	public int compareTo(Client arg0) {
		return this.id - arg0.id;
	}
	
	@Override
	public String toString() {
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.clientType;
		return client; 
	}
}
