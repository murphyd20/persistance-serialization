package edu.toronto.csc301.test.basic;

import org.junit.Test;

import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUserStore;
import edu.toronto.csc301.test.AbsTest;
import edu.toronto.csc301.util.CSC301TestBase;


public class ImplementationStructureTest extends CSC301TestBase{
	
	// Verify that the required classes exist
	
	@Test
	public void userStoreImplExists() {
		assertClassExists(AbsTest.CLASS_NAME_USER_STORE);
	}
	
	@Test
	public void serializerImplExists() {
		assertClassExists(AbsTest.CLASS_NAME_SERIALIZER);
	}
	
	
	
	// Verify that the required classes implement the appropriate interfaces.
	
	@Test
	public void userStoreImplementIUserStore() throws ClassNotFoundException {
		assertClassImplementsInterface(AbsTest.CLASS_NAME_USER_STORE, IUserStore.class);
	}
		
	@Test
	public void serializerImplementISerializer() throws ClassNotFoundException {
		assertClassImplementsInterface(AbsTest.CLASS_NAME_SERIALIZER, ISerializer.class);
	}
	
	
	
	// Verify that the required classes have the required constructors
	
	@Test
	public void userStoreHasDefaultConstructor() throws ClassNotFoundException {
		assertClassHasConstructor(AbsTest.CLASS_NAME_USER_STORE);
	}
	
	@Test
	public void serializerHasDefaultConstructor() throws ClassNotFoundException {
		assertClassHasConstructor(AbsTest.CLASS_NAME_SERIALIZER);
	}
	
	
}
