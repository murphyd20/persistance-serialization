package edu.toronto.csc301.test.objectGraphs;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph1a extends AbsObjectGraphTest {

	protected IUser alice;
	protected IPost postByAlice;
	protected IUser bob;
	protected IPost postByBob;

	@Override
	protected void createTestData(IUserStore store) throws Exception {
		alice        = userStore.createUser("alice", "123456");
		postByAlice  = alice.newPost(createTestImage(), "Alice's first post");
		
		bob        = userStore.createUser("bob", "123456");
		postByBob  = bob.newPost(createTestImage(), "Bob's first post");
	}
	
	
	@Test
	public void serializeDeserializeTwoUsersToTwoDifferentStreams() throws Exception{  
		try(ByteArrayOutputStream output1 = new ByteArrayOutputStream();
			ByteArrayOutputStream output2 = new ByteArrayOutputStream()){
			
			createSerializer().serialize(alice, output1);
			createSerializer().serialize(bob, output2);
			output1.flush();
			output2.flush();
			
			try(ByteArrayInputStream  input2  = new ByteArrayInputStream(output2.toByteArray());
				ByteArrayInputStream  input1  = new ByteArrayInputStream(output1.toByteArray())){
				
				IUser u2 = createSerializer().deserializeUser(input2);
				IUser u1 = createSerializer().deserializeUser(input1);
				
				assertEquals("alice", u1.getUsername());
				assertEquals("Alice's first post", u1.getPosts().next().getCaption());
				assertEquals("bob", u2.getUsername());
				assertEquals("Bob's first post", u2.getPosts().next().getCaption());
			}
		}
	}


}
