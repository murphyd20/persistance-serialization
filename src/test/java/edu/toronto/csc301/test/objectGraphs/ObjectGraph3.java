package edu.toronto.csc301.test.objectGraphs;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph3 extends AbsObjectGraphTest {

	protected IUser   alice;
	protected IUser   bob;
	protected IPost[] postsByAlice;


	@Override
	protected void createTestData(IUserStore store) throws Exception {
		alice = userStore.createUser("alice", "123456");
		bob   = userStore.createUser("bob",   "123456");

		postsByAlice = createPosts(alice, 
				"Alice's first post",
				"Alice's second post", 
				"Alice's third post"
		);

		bob.like(postsByAlice[0]);
	}



	@Test
	public void testPostsThatBobLikes() throws Exception{
		testUser2likes(bob, postsByAlice[0]);
	}


	@Test
	public void testUsersWhoLikePostsByAlice() throws Exception{
		testUser2posts2likes(alice, bob);
	}


}
