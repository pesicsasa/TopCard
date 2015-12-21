package model;

import java.sql.Date;

public class RationaleVote {

	public RationaleVote(int id, int precise, int understandable, String traceable, Date dateCast, int userId,
			int contentId) {
		super();
		this.id = id;
		this.precise = precise;
		this.understandable = understandable;
		this.traceable = traceable;
		this.dateCast = dateCast;
		this.userId = userId;
		this.contentId = contentId;
	}
	private int id;
	private int precise;
	private int understandable;
	private String traceable;
	private Date dateCast;
	private int userId;
	private int contentId;
	
	public int getPrecise() {
		return precise;
	}
	public void setPrecise(int precise) {
		this.precise = precise;
	}
	public int getUnderstandable() {
		return understandable;
	}
	public void setUnderstandable(int understandable) {
		this.understandable = understandable;
	}
	public String getTraceable() {
		return traceable;
	}
	public void setTraceable(String traceable) {
		this.traceable = traceable;
	}
	public Date getDateCast() {
		return dateCast;
	}
	public void setDateCast(Date dateCast) {
		this.dateCast = dateCast;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
