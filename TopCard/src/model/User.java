package model;
/**
 * 
 * @author Nikola Draskovic Jelcic
 *
 */
public class User {
	
	public User(int id, String firsttName, String lastName, String username, String email) {
		super();
		this.id = id;
		this.firsttName = firsttName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
	}
	private int id;
	private String firsttName;
	private String lastName;
	private String username;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirsttName() {
		return firsttName;
	}
	public void setFirsttName(String firsttName) {
		this.firsttName = firsttName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
