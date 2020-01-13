package edu.toronto.csc301.test.objectGraphs;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class ObjectGraph5 extends AbsObjectGraphTest {

	int n = 30;
	IUser[] users;
	IPost[] posts;
	
	
	@Override
	protected void createTestData(IUserStore store) throws Exception {
		// Create a "chain" of n users and n posts:
		//   user1 --posts--> post1
		//   post1 --liked-by--> user2
		//   user2 --posts--> post2
		//   post2 --liked-by--> user3
		//   ...
		
		users = new IUser[n];
		posts = new IPost[n];
		
		for (int i = 0; i < n; i++) {
			users[i] = createTestUser(store);
			posts[i] = users[i].newPost(createTestImage(), "Post " + i);
			if (i > 0){
				users[i].like(posts[i - 1]);
			}
		}
	}

	
	@Test
	public void test() throws Exception{
		IUser user = serializeDeserialize(users[0]);
		
		for (int i = 0; i < n; i++) {
			// Assert that the i'th user posted the i'th post
			Iterator<IPost> postsItr = user.getPosts();
			assertTrue(postsItr.hasNext());
			IPost post = postsItr.next();
			assertFalse(postsItr.hasNext());
			assertEquals(posts[i].getCaption(), post.getCaption());
			
			// As long as we're not in the last post
			if(i < n - 1){
				// Assert that the i'th post is liked by the i+1st user
				Iterator<IUser> likes = post.getLikes();
				assertTrue(likes.hasNext());
				user = likes.next();
				assertFalse(likes.hasNext());
			}
		}
		
	}

}
