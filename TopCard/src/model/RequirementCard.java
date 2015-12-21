package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RequirementCard {

	private StringProperty module;

	private int id;

	private StringProperty source;

	private StringProperty status;

	private StringProperty user;

	private StringProperty supportingMaterials;

	private StringProperty userStory;
	
	private StringProperty name;

	public RequirementCard(String module, String source, String status, String user, String supportingMaterials,
			String userStory, String name) {
		super();
		this.module = new SimpleStringProperty(module);
		this.source = new SimpleStringProperty(source);
		this.status = new SimpleStringProperty(status);
		this.user = new SimpleStringProperty(user);
		this.supportingMaterials = new SimpleStringProperty(supportingMaterials);
		this.userStory = new SimpleStringProperty(userStory);
		this.name = new SimpleStringProperty(name);

	}

	public String getModule() {
		return module.get();
	}

	public int getId() {
		return id;
	}

	public String getSource() {
		return source.get();
	}

	public String getStatus() {
		return status.get();
	}

	public String getUser() {
		return user.get();
	}

	public String getSupportingMaterials() {
		return supportingMaterials.get();
	}

	public String getUserStory() {
		return userStory.get();
	}
	
	public String getName() {
		return name.get();
	}

	public int idProperty() {
		return id;
	}

	public StringProperty sourceProperty() {
		return source;
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	@Override
	public String toString() {
		return "RequirementCard [module=" + module + ", id=" + id + ", source=" + source + ", status=" + status
				+ ", user=" + user + ", supportingMaterials=" + supportingMaterials + ", userStory=" + userStory
				+ ", name=" + name + ", getModule()=" + getModule() + ", getId()=" + getId() + ", getSource()="
				+ getSource() + ", getStatus()=" + getStatus() + ", getUser()=" + getUser()
				+ ", getSupportingMaterials()=" + getSupportingMaterials() + ", getUserStory()=" + getUserStory()
				+ ", getName()=" + getName() + ", idProperty()=" + idProperty() + ", sourceProperty()="
				+ sourceProperty() + ", nameProperty()=" + nameProperty() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setModule(StringProperty module) {
		this.module = module;
	}

	public void setSource(StringProperty source) {
		this.source = source;
	}

	public void setStatus(String status) {
		this.status.setValue(status);
	}

	public void setUser(StringProperty user) {
		this.user = user;
	}

	public void setSupportingMaterials(StringProperty supportingMaterials) {
		this.supportingMaterials = supportingMaterials;
	}

	public void setUserStory(StringProperty userStory) {
		this.userStory = userStory;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}
}
