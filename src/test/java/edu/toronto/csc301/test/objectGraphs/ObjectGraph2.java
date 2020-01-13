package edu.toronto.csc301.test.objectGraphs;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph2 extends AbsObjectGraphTest {


	protected IUser alice;
	protected IPost[] posts;


	@Override
	protected void createTestData(IUserStore store) throws Exception {
		alice   = userStore.createUser("alice", "123456");
		posts = createPosts(alice, 
			"Alice's first post",
			"Alice's second post", 
			"Alice's third post"
		);
	}
	
	
	@Test
	public void testPostsByAlice() throws Exception{
		testUser2posts(alice, posts);
	}
	
	@Test
	public void sanityTestPostsByAlice() throws Exception{
		testUser2posts2postedBy(alice);
	}
	
}
