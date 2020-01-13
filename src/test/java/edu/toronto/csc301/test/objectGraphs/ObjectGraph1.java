package edu.toronto.csc301.test.objectGraphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph1 extends AbsObjectGraphTest {

	protected IUser alice;
	protected IPost postByAlice;

	@Override
	protected void createTestData(IUserStore store) throws Exception {
		alice = userStore.createUser("alice", "123456");
		postByAlice  = alice.newPost(createTestImage(), "Alice's first post");
	}

	
	
	@Test
	public void getPostsNotNull() throws Exception{
		assertNotNull(serializeDeserialize(alice).getPosts());
	}
	
	
	@Test
	public void getPostsHasTheCorrectNumberOfPosts() throws Exception{
		Iterator<IPost> posts = serializeDeserialize(alice).getPosts();
		assertTrue(posts.hasNext());
		posts.next();
		assertFalse(posts.hasNext());
	}
	
	
	@Test
	public void testDeserializedPostCaption() throws Exception{		
		IPost p = serializeDeserialize(alice).getPosts().next();
		assertEquals(postByAlice.getCaption(), p.getCaption());
	}
	
	@Test
	public void testDeserializedPostImage() throws Exception{
		IPost p = serializeDeserialize(alice).getPosts().next();
		assertEqualDimensions(postByAlice.getImage(), p.getImage());
	}
	
	@Test
	public void testDeserializedPostPostedAt() throws Exception{
		IPost p = serializeDeserialize(alice).getPosts().next();
		assertEquals(postByAlice.getPostedAt(), p.getPostedAt());
	}
	
}
