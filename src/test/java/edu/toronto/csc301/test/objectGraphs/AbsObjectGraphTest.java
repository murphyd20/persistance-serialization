package edu.toronto.csc301.test.objectGraphs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;
import edu.toronto.csc301.test.AbsTest;

public abstract class AbsObjectGraphTest extends AbsTest {

	protected abstract void createTestData(IUserStore store) throws Exception;

	@Before
	public void setup() throws Exception{
		super.setup();
		createTestData(this.userStore);
	}
	
	
	//-------------------------------------------------------------------------
	// Convenience helpers
	
	protected IPost[] createPosts(IUser user, String ... postCaptions) throws IOException{
		IPost[] postArray = new IPost[postCaptions.length];
		for (int i = 0; i < postCaptions.length; i++) {
			postArray[i] = user.newPost(createTestImage(), postCaptions[i]);
			postArray[i].setPostedAt(LocalDateTime.now().minusMinutes(random.nextInt(15)));
		}
		return postArray;
	}
	
	//-------------------------------------------------------------------------
	// Helper methods that test different walks in the graph ...
	

	protected void testUser2posts(IUser user, IPost ... expectedPosts) throws Exception{
		user = serializeDeserialize(user);
		assertEqualPostCaptionsRegardlessOfOrdering(expectedPosts, user.getPosts());
	}
	
	
	protected void testUser2likes(IUser user, IPost ... expectedPosts) throws Exception{
		user = serializeDeserialize(user);
		assertEqualPostCaptionsRegardlessOfOrdering(expectedPosts, user.getLikes());
	}
	
	
	protected void testUser2posts2likes(IUser user, IUser ... expectedLikers) throws Exception{
		user = serializeDeserialize(user);
		
		List<IUser> actualLikers = new LinkedList<IUser>();
		user.getPosts().forEachRemaining((p) -> {
			p.getLikes().forEachRemaining((u) -> {
				actualLikers.add(u);
			});
		});
		
		assertEqualUsernamesRegardlessOfOrdering(expectedLikers, actualLikers);
	}
	
	
	protected void testUser2posts2postedBy(IUser user) throws Exception{
		Iterator<IPost> posts = serializeDeserialize(user).getPosts();
		while (posts.hasNext()) {
			IPost post = (IPost) posts.next();
			assertNotNull(post.getPostedBy());
			assertEquals(user.getUsername(), post.getPostedBy().getUsername());
		}
	}
	
	
	protected void testUser2likes2postedBy(IUser user, IUser ... expectedPosters) throws Exception{
		user = serializeDeserialize(user);
		List<IUser> actualPosters = new LinkedList<IUser>();
		user.getLikes().forEachRemaining((p) -> {
			actualPosters.add(p.getPostedBy());
		});
		assertEqualUsernamesRegardlessOfOrdering(expectedPosters, actualPosters);
	}
	
	
	protected void testUser2likes2postedBy2Posts(IUser user, IPost ... expectedPosts) throws Exception{
		user = serializeDeserialize(user);
		List<IPost> actualPosts = new LinkedList<IPost>();
		user.getLikes().forEachRemaining((p) -> {
			p.getPostedBy().getPosts().forEachRemaining((p2) -> {
				actualPosts.add(p2);
			});
		});
		assertEqualPostCaptionsRegardlessOfOrdering(expectedPosts, actualPosts);
	}
	
	
	protected void testUser2likes2likedBy(IUser user, IUser ... expectedLikers) throws Exception{
		user = serializeDeserialize(user);
		List<IUser> actualLikers = new LinkedList<IUser>();
		user.getLikes().forEachRemaining((p) -> {
			p.getLikes().forEachRemaining((u) -> {
				actualLikers.add(u);
			});
			
		});
		assertEqualUsernamesRegardlessOfOrdering(expectedLikers, actualLikers);
	}
	
	
	//-------------------------------------------------------------------------
	
	 
	
	private <T> void assertEqualElementsRegardlessOfOrdering(T[] expected, Collection<T> actual){
		assertEquals(expected.length, actual.size());
		
		@SuppressWarnings("unchecked")
		T[] actualArray = (T[]) actual.toArray();
		
		Arrays.sort(expected);
		Arrays.sort(actualArray);
		
		assertArrayEquals(expected, actualArray);
	}
	
	
	private void assertEqualPostCaptionsRegardlessOfOrdering(IPost[] expected, 
			Iterator<IPost> actual) throws Exception{ 
		Collection<IPost> actualPosts = new LinkedList<IPost>();
		actual.forEachRemaining((p) -> actualPosts.add(p));
		assertEqualPostCaptionsRegardlessOfOrdering(expected, actualPosts );
	}
	
	
	private void assertEqualPostCaptionsRegardlessOfOrdering(IPost[] expected, 
			Collection<IPost> actual) throws Exception{ 
		
		assertEquals(expected.length, actual.size());
		
		String[] expectedCaptions = new String[expected.length];
		for (int i = 0; i < expected.length; i++) {
			expectedCaptions[i] = expected[i].getCaption();
		}
		
		List<String> actualCaptions = new LinkedList<String>();
		for(IPost post : actual){
			actualCaptions.add(post.getCaption());
		}
		
		assertEqualElementsRegardlessOfOrdering(expectedCaptions, actualCaptions);
	}
	
	
	private void assertEqualUsernamesRegardlessOfOrdering(IUser[] expected, 
			Collection<IUser> actual) throws Exception{ 
		
		assertEquals(expected.length, actual.size());
		
		String[] expectedUsernames = new String[expected.length];
		for (int i = 0; i < expected.length; i++) {
			expectedUsernames[i] = expected[i].getUsername();
		}
		
		List<String> actualUsernames = new LinkedList<String>();
		for(IUser user : actual){
			actualUsernames.add(user.getUsername());
		}
		
		assertEqualElementsRegardlessOfOrdering(expectedUsernames, actualUsernames);
	}
	
}
