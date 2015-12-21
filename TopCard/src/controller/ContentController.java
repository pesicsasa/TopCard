package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DbConnection;
import model.Content;
import model.RequirementCard;

public class ContentController {

	public static Content getLatestContentForRc(RequirementCard r) {
		Content c = new Content();
		try {
			Connection conn = DbConnection.open();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Content WHERE Requirement_Card_ID= ?");
			stmt.setInt(1, r.getId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int cont_id = rs.getInt("Content_ID");
				String desc = rs.getString("Description");
				String rat = rs.getString("Rationale");
				Date date = rs.getDate("Date_Created");
				String major = rs.getString("Major_Version");
				String minor = rs.getString("Minor_Version");
				c.setDescription(desc);
				c.setMajor(Integer.parseInt(major));
				c.setMinor(Integer.parseInt(minor));
				c.setRationale(rat);
				c.setId(cont_id);
				c.setDate(date);
				c.setRc(r.getId());
				
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static int insertRcContentToDB(Content c)

	{
		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		try {
			String insertContent = "INSERT INTO Content"
					+ "(Date_Created, Description, Rationale, Major_Version, Minor_Version, Requirement_Card_ID) VALUES"
					+ "(?,?,?,?,?,?)";
			stmt = conn.prepareStatement(insertContent);
			stmt.setDate(1,  new Date(new java.util.Date().getTime()));
			stmt.setString(2, c.getDescription());
			stmt.setString(3, c.getRationale());
			stmt.setInt(4, c.getMajor());
			stmt.setInt(5, c.getMinor());
			stmt.setInt(6, c.getRc());
			int ct_ID = stmt.executeUpdate();
			conn.commit();
			stmt.close();
			DbConnection.close();
			return ct_ID;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return -1;
	}
	
	public static int updateContent(Content c, RequirementCard r)
	{
		PreparedStatement stmt = null;
		Connection conn = DbConnection.open();
		
		try {
			String query = "UPDATE Content SET "
					+ "Description=?, Rationale=?, Major_Version=?, Minor_Version=? "
					+ "WHERE Content_ID = ?";
			System.out.println("update content called for " + c);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getDescription());
			stmt.setString(2, c.getRationale());
			stmt.setInt(3, c.getMajor());
			stmt.setInt(4, c.getMinor());
			stmt.setInt(5, c.getId());
			int success = stmt.executeUpdate();
			conn.commit();
			System.out.println(success);
			stmt.close();
			DbConnection.close();
			return success;

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.rollback();
		} catch (SQLException e1) { // TODO Auto-generated
			e1.printStackTrace();
		}

		return -1;
		
	}
	
	public static List<Content> getAllContents(RequirementCard r)
	{
		List<Content> l = new ArrayList<Content>();
		try {
			Connection conn = DbConnection.open();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Content WHERE Requirement_Card_ID= ?");
			stmt.setInt(1, r.getId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Content c = new Content();
				int cont_id = rs.getInt("Content_ID");
				String desc = rs.getString("Description");
				String rat = rs.getString("Rationale");
				Date date = rs.getDate("Date_Created");
				String major = rs.getString("Major_Version");
				String minor = rs.getString("Minor_Version");
				c.setDescription(desc);
				c.setMajor(Integer.parseInt(major));
				c.setMinor(Integer.parseInt(minor));
				c.setRationale(rat);
				c.setId(cont_id);
				c.setDate(date);
				c.setRc(r.getId());
				l.add(c);
				
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public static List<Content> getAllMinorVersions(RequirementCard rc, int major)
	{
		List<Content> l = new ArrayList<Content>();
		try {
			Connection conn = DbConnection.open();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Content WHERE Requirement_Card_ID= ? AND Major_Version=?");
			stmt.setInt(1, rc.getId());
			stmt.setInt(2, major);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Content c = new Content();
				int cont_id = rs.getInt("Content_ID");
				String desc = rs.getString("Description");
				String rat = rs.getString("Rationale");
				Date date = rs.getDate("Date_Created");
				String majorV = rs.getString("Major_Version");
				String minorV = rs.getString("Minor_Version");
				c.setDescription(desc);
				c.setMajor(Integer.parseInt(majorV));
				c.setMinor(Integer.parseInt(minorV));
				c.setRationale(rat);
				c.setId(cont_id);
				c.setDate(date);
				c.setRc(rc.getId());
				l.add(c);
				
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public static Content getContentForVersion(RequirementCard rc, int major, int minor)
	{
		Content c = new Content();
		try {
			Connection conn = DbConnection.open();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Content WHERE Requirement_Card_ID= ? AND Major_Version=? AND Minor_Version=?");
			stmt.setInt(1, rc.getId());
			stmt.setInt(2, major);
			stmt.setInt(3, minor);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int cont_id = rs.getInt("Content_ID");
				String desc = rs.getString("Description");
				String rat = rs.getString("Rationale");
				Date date = rs.getDate("Date_Created");
				String majorV = rs.getString("Major_Version");
				String minorV = rs.getString("Minor_Version");
				c.setDescription(desc);
				c.setMajor(Integer.parseInt(majorV));
				c.setMinor(Integer.parseInt(minorV));
				c.setRationale(rat);
				c.setId(cont_id);
				c.setDate(date);
				c.setRc(rc.getId());	
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static Content getWorkingCopy(RequirementCard rc)
	{
		Content c = new Content();
		try {
			Connection conn = DbConnection.open();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Content WHERE Requirement_Card_ID= ?");
			stmt.setInt(1, rc.getId());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int cont_id = rs.getInt("Content_ID");
				String desc = rs.getString("Description");
				String rat = rs.getString("Rationale");
				Date date = rs.getDate("Date_Created");
				String major = rs.getString("Major_Version");
				String minor = rs.getString("Minor_Version");
				c.setDescription(desc);
				c.setMajor(Integer.parseInt(major));
				c.setMinor(Integer.parseInt(minor));
				c.setRationale(rat);
				c.setId(cont_id);
				c.setDate(date);
				c.setRc(rc.getId());
				
			}
			rs.close();
			stmt.close();
			DbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}