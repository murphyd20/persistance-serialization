package edu.toronto.csc301.test.objectGraphs;

import org.junit.Test;

import edu.toronto.csc301.IUserStore;

public class ObjectGraph3b extends ObjectGraph3 {

	@Override
	protected void createTestData(IUserStore store) throws Exception {
		super.createTestData(store);
		bob.like(postsByAlice[2]);
	}



	@Override
	@Test
	public void testPostsThatBobLikes() throws Exception{
		testUser2likes(bob, postsByAlice[0], postsByAlice[2]);
	}


	@Override
	@Test
	public void testUsersWhoLikePostsByAlice() throws Exception{
		// Make sure that Bob appears twice, because he likes two of Alice's posts
		testUser2posts2likes(alice, bob, bob);
	}


}
