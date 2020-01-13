package edu.toronto.csc301.test.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import edu.toronto.csc301.IUser;
import edu.toronto.csc301.test.AbsTest;

/**
 * Test the serialization/deserialization of the IUser implementation.
 * Test the properties only (i.e. not testing relations)
 */
public class UserTest extends AbsTest {


	// Basic sanity tests

	@Test
	public void createUserDoesNotThrowAnError() throws Exception{
		userStore.createUser("alice", "123456");
	}

	@Test
	public void createMultipleUsersDoesNotThrowAnError() throws Exception{
		for (int i = 0; i < 50; i++) {
			userStore.createUser("test-user-" + i, "123456");
		}
	}

	@Test
	public void createUserDoesNotReturnNull() throws Exception{
		assertNotNull(userStore.createUser("alice", "123456"));
	}


	// Test that properties are set properly on creation

	@Test
	public void createUserSetsUsernameProperlyOnCreation() throws Exception{
		String username = "alice";
		IUser u = userStore.createUser(username, "123456");
		assertEquals(username, u.getUsername());
	}

	@Test
	public void createUserSetsPasswordProperlyOnCreation() throws Exception{
		String password = "123456";
		IUser u = userStore.createUser("alice", password);
		assertEquals(password, u.getPassword());
	}

	@Test
	public void createUserSetsRegistrationTimeProperlyOnCreation() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		assertRoughlyEquals(LocalDateTime.now(), u.getRegistrationTime());
	}


	// Test that properties are set properly by setters

	@Test
	public void createUserSetsPasswordProperly() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		u.setPassword("abcdefg");
		assertEquals("abcdefg", u.getPassword());
	}

	@Test
	public void createUserSetsRegistrationTimeProperly() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		LocalDateTime t = LocalDateTime.now().minusMinutes(7);
		u.setRegistrationTime(t);
		assertEquals(t, u.getRegistrationTime());
	}


	// Test that the user-store requires user-names to be unique

	@Test(expected=IllegalArgumentException.class)
	public void usernameMustBeUnique() throws Exception{
		String username = "alice";
		userStore.createUser(username, "123456");
		userStore.createUser(username, "654321");
	}



	// Test getUser

	@Test
	public void getNonExistingUserReturnsNull() throws Exception{
		assertNull(userStore.getUser("foo"));
	}

	@Test
	public void getExistingUserReturnsTheCorrectUser() throws Exception{
		userStore.createUser("alice", "123456");

		IUser alice = userStore.getUser("alice");
		assertNotNull(alice);
		assertEquals("alice", alice.getUsername());
	}

	@Test
	public void getExistingUserReturnsTheCorrectUser2() throws Exception{
		userStore.createUser("alice", "123456");
		userStore.createUser("bob", "123456");
		userStore.createUser("charlie", "123456");

		IUser bob = userStore.getUser("bob");
		assertNotNull(bob);
		assertEquals("bob", bob.getUsername());
	}



	// Test getAllUsers

	@Test
	public void getAllUsersIsNotNull() throws Exception{
		assertNotNull(userStore.getAllUsers());
	}

	@Test
	public void getAllUsersFromAnEmptyStore() throws Exception{
		assertFalse(userStore.getAllUsers().hasNext());
	}

	@Test
	public void getAllUsersFromStoreWithOneUser() throws Exception{
		userStore.createUser("alice", "123456");
		Iterator<IUser> allUsers = userStore.getAllUsers();

		assertTrue(allUsers.hasNext());
		assertEquals("alice", allUsers.next().getUsername());
		assertFalse(allUsers.hasNext());
	}

	@Test
	public void getAllUsersFromStoreWithMultipleUser() throws Exception{
		// Create a few users
		Set<String> usernames = toSet("alice", "bob", "charlie");
		for(String name : usernames){
			userStore.createUser(name, "123456");
		}
		
		// Assert that getAllUsers() returns an iterator that visits
		// all the users we have created (and only them).
		userStore.getAllUsers().forEachRemaining((user) -> {
			assertTrue(usernames.remove(user.getUsername()));
		});
		assertTrue(usernames.isEmpty());
	}




	//-------------------------------------------------------------------------
	// Test basic serialization ...

	@Test
	public void serializationDoesNotThrowAnError() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
			serializer.serialize(u, output);
		}
	}

	@Test
	public void serializationDeserializationDoesNotThrowAnError() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		serializeDeserialize(u);
	}

	@Test
	public void serializeDeserializeDoesNotReturnNull() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		assertNotNull(serializeDeserialize(u));

	}

	@Test
	public void serializeDeserializeAndCheckUsername() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		assertEquals(u.getUsername(), serializeDeserialize(u).getUsername());
	}

	@Test
	public void serializeDeserializeAndCheckPassword() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		assertEquals(u.getPassword(), serializeDeserialize(u).getPassword());
	}

	@Test
	public void serializeDeserializeAndCheckRegistrationTime() throws Exception{
		IUser u = userStore.createUser("alice", "123456");
		LocalDateTime t = LocalDateTime.now().minusMinutes(7);
		u.setRegistrationTime(t);
		assertEquals(t, serializeDeserialize(u).getRegistrationTime());
	}


}
