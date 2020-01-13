package edu.toronto.csc301.test.objectGraphs;

import org.junit.Test;

import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph3c extends ObjectGraph3b {

	protected IUser charlie;


	@Override
	protected void createTestData(IUserStore store) throws Exception {
		super.createTestData(store);
		charlie = store.createUser("charlie", "123456");
		charlie.like(postsByAlice[1]);
	}

	
	@Test
	public void testPostsThatCharlieLikes() throws Exception{
		testUser2likes(charlie, postsByAlice[1]);
	}


	@Override
	@Test
	public void testUsersWhoLikePostsByAlice() throws Exception{
		testUser2posts2likes(alice, bob, bob, charlie);
	}


}
