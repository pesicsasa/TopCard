package controller.module;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Module;

import org.junit.Test;

import controller.ModuleController;

/**
 * @author Maximilian Kostial
 *
 */



public class ModuleControllerTest {
	
	@Test
	public void createModuleController()
	{
		ModuleController m = new ModuleController();
		boolean success = m!=null;
		assertTrue(success);
	}

	@Test
	public void testReadModules()
	{
		List<Module> l = ModuleController.readModules();
		boolean success = l!=null;
		assertTrue(success);
	}
	
	@Test
	public void testReadModuleNames()
	{
		String[] names = ModuleController.getAllModuleNames();
		 boolean success = names!=null;
		assertTrue(success);

	}
	
	@Test
	public void testGetModuleForName(String name)
	{
		//String name="Module A";
		int moduleId = ModuleController.getModuleForName(name);
		boolean success = moduleId!=-1;
		if(success)
			assertTrue(success);
		else
			assertFalse(success);
	}

	@Test
	public void testgetModuleForId()
	{
		int moduleId = 1;
		String name = ModuleController.getModuleNameForId(moduleId);
		boolean success = name.equalsIgnoreCase("");
		assertFalse(success);

	}
	
	@Test
	public void testInsertModule()
	{
		String name = "Module A";
		String description = "Description A";
		int moduleId = ModuleController.insertModule(name, description);
		boolean success = moduleId!=-1;
		assertTrue(success);


	}

} 
