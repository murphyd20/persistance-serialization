package edu.toronto.csc301.test.objectGraphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;


public class ObjectGraph4 extends AbsObjectGraphTest {


	protected IUser alice;
	protected IUser bob;
	protected IUser charlie;
	protected IUser david;
	protected IPost[] postsByAlice;
	protected IPost[] postsByBob;


	@Override
	protected void createTestData(IUserStore userStore) throws Exception{
		alice   = userStore.createUser("alice", "123456");
		bob     = userStore.createUser("bob", "123456");
		charlie = userStore.createUser("charlie", "123456");
		david   = userStore.createUser("david", "123456");

		postsByAlice = createPosts(alice, 
				"Alice's first post",
				"Alice's second post"
		);
		
		postsByBob = createPosts(bob, 
				"Bob's first post"
		);
		
		charlie.like(postsByBob[0]);

		david.like(postsByAlice[0]);
		david.like(postsByBob[0]);
	}


	// Test different walks in the graph ...

	
	// User --> posts

	@Test
	public void testPostsByAlice() throws Exception{
		testUser2posts(alice, postsByAlice);
	}
	
	@Test
	public void testPostsByBob() throws Exception{
		testUser2posts(bob, postsByBob);
	}
	
	@Test
	public void testPostsByCharlie() throws Exception{
		testUser2posts(charlie);
	}
	
	@Test
	public void testPostsByDavid() throws Exception{
		testUser2posts(david);
	}

	
	// User --> likes
	
	@Test
	public void testPostsThatAliceLikes() throws Exception{
		testUser2likes(alice);
	}
	
	@Test
	public void testPostsThatBobLikes() throws Exception{
		testUser2likes(bob);
	}
	
	@Test
	public void testPostsThatCharlieLikes() throws Exception{
		testUser2likes(charlie, postsByBob[0]);
	}
	
	@Test
	public void testPostsThatDavidLikes() throws Exception{
		testUser2likes(david, postsByBob[0], postsByAlice[0]);
	}

	
	// User --> posts --> posted-by

	@Test
	public void sanityTestPostsByAlice() throws Exception{
		testUser2posts2postedBy(alice);
	}
	
	@Test
	public void sanityTestPostsByBob() throws Exception{
		testUser2posts2postedBy(bob);
	}
	
	@Test
	public void sanityTestPostsByCharlie() throws Exception{
		testUser2posts2postedBy(charlie);
	}
	
	@Test
	public void sanityTestPostsByDavid() throws Exception{
		testUser2posts2postedBy(david);
	}


	// User --> posts --> liked-by
	
	@Test
	public void testUsersWhoLikePostsByAlice() throws Exception{
		testUser2posts2likes(alice, david);
	}
	
	@Test
	public void testUsersWhoLikePostsByBob() throws Exception{
		testUser2posts2likes(bob, charlie, david);
	}
	
	@Test
	public void testUsersWhoLikePostsByCharlie() throws Exception{
		testUser2posts2likes(charlie);
	}
	
	@Test
	public void testUsersWhoLikePostsByDavid() throws Exception{
		testUser2posts2likes(david);
	}
	
	
	// User --> likes --> posted-by
	
	@Test
	public void testUsersWhosePostsAreLikedByAlice() throws Exception{
		testUser2likes2postedBy(alice);
	}
	
	@Test
	public void testUsersWhosePostsAreLikedByBob() throws Exception{
		testUser2likes2postedBy(bob);
	}
	
	@Test
	public void testUsersWhosePostsAreLikedByCharlie() throws Exception{
		testUser2likes2postedBy(charlie, bob);
	}
	
	@Test
	public void testUsersWhosePostsAreLikedByDavid() throws Exception{
		testUser2likes2postedBy(david, alice, bob);
	}
	

	// User --> likes --> liked-by
	
	@Test
	public void testUsersWhoLikePostsLikedByAlice() throws Exception{
		testUser2likes2likedBy(alice);
	}
	
	@Test
	public void testUsersWhoLikePostsLikedByBob() throws Exception{
		testUser2likes2likedBy(bob);
	}
	
	@Test
	public void testUsersWhoLikePostsLikedByCharlie() throws Exception{
		testUser2likes2likedBy(charlie, charlie, david);
	}
	
	@Test
	public void testUsersWhoLikePostsLikedByDavid() throws Exception{
		testUser2likes2likedBy(david, charlie, david, david);
	}
	
	
	// User --> likes --> posted-by --> posts
	
	@Test
	public void testPostsByUsersWhosePostsAreLikedByAlice() throws Exception{
		testUser2likes2postedBy2Posts(alice);
	}
	
	@Test
	public void testPostsByUsersWhosePostsAreLikedByBob() throws Exception{
		testUser2likes2postedBy2Posts(bob);
	}
	
	@Test
	public void testPostsByUsersWhosePostsAreLikedByCharlie() throws Exception{
		testUser2likes2postedBy2Posts(charlie, postsByBob);
	}
	
	@Test
	public void testPostsByUsersWhosePostsAreLikedByDavid() throws Exception{
		List<IPost> posts = new LinkedList<IPost>(Arrays.asList(postsByAlice));
		posts.addAll(Arrays.asList(postsByBob));
		testUser2likes2postedBy2Posts(david, posts.toArray(new IPost[]{}));
	}
	

}
