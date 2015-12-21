package model;

import java.sql.Date;

public class Content {
	
	private int id;
	private Date date;
	private String description;
	private String rationale;
	private int major;
	private int minor;
	private int rcId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desfription) {
		this.description = desfription;
	}
	public String getRationale() {
		return rationale;
	}
	public void setRationale(String rationale) {
		this.rationale = rationale;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int minor) {
		this.minor = minor;
	}
	public int getRc() {
		return rcId;
	}
	public void setRc(int i) {
		this.rcId = i;
	}
	@Override
	public String toString() {
		return "Content [id=" + id + ", date=" + date + ", description=" + description + ", rationale=" + rationale
				+ ", major=" + major + ", minor=" + minor + ", rc=" + rcId + "]";
	}
	

}