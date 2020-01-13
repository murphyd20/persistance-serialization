package edu.toronto.csc301.test.basic;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.Test;

import edu.toronto.csc301.IUser;
import edu.toronto.csc301.test.AbsTest;

public class InvalidArgumentsTest extends AbsTest {

	
	//-------------------------------------------------------------------------

	
	@Test(expected=NullPointerException.class)
	public void createUserWithNullUsername() throws Exception{
		userStore.createUser(null, "123456");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createUserWithEmptyUsername() throws Exception{
		userStore.createUser("", "123456");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createUserWithWhiteSpaceOnlyUsername() throws Exception{
		userStore.createUser("  \t ", "123456");
	}
	
	
	@Test(expected=NullPointerException.class)
	public void createUserWithNullPassword() throws Exception{
		userStore.createUser("alice", null);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void getUserWithNullUsername() throws Exception{
		userStore.getUser(null);
	}
	


	//-------------------------------------------------------------------------
	
	
	@Test(expected=NullPointerException.class)
	public void serializeNullUser() throws Exception{
		try(OutputStream out = new ByteArrayOutputStream()){
			serializer.serialize((IUser) null, out);
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void serializeUserToNullOutputStream() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		serializer.serialize(u, null);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void deserializeUserFromNullInputStream() throws Exception{
		serializer.deserializeUser(null);
	}
	
	
}
