package model;

/** @author Stanislav Lokhtin
 *
 */
public class Module {
	
	public Module(String name, String description, int id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
	}

	private String name;
	private String description;
	private int id;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
