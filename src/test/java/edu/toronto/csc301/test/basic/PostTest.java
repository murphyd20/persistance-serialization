package edu.toronto.csc301.test.basic;

import static org.junit.Assert.*;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Iterator;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.test.AbsTest;



public class PostTest extends AbsTest {


	@Test
	public void createPostWithoutError() throws Exception{
		createTestUser().newPost(createTestImage(), "Test post");
	}

	@Test
	public void createPostWithoutError2() throws Exception{
		createTestUser().newPost(null, "Caption only post");
	}

	@Test
	public void createPostWithoutError3() throws Exception{
		createTestUser().newPost(createTestImage(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void cannotCreatePostWithNeitherImageNorCaption() throws Exception{
		createTestUser().newPost(null, null);
	}




	@Test
	public void createPostAndCheckCaption() throws Exception{
		IPost p = createTestUser().newPost(createTestImage(), "Test post");
		assertEquals("Test post", p.getCaption());
	}

	@Test
	public void createPostAndCheckImage() throws Exception{
		RenderedImage img = createTestImage();
		IPost p = createTestUser().newPost(img, "Test post");
		assertEqualDimensions(img, p.getImage());
	}

	@Test
	public void createPostAndCheckPostTime() throws Exception{
		IPost p = createTestUser().newPost(createTestImage(), "Test post");
		assertRoughlyEquals(LocalDateTime.now(), p.getPostedAt());
	}

	@Test
	public void createPostAndCheckPostedBy() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		assertEquals(u.getUsername(), p.getPostedBy().getUsername());
	}
	
	@Test
	public void createPostAndCheckUserPosts() throws Exception{
		IUser u = createTestUser();
		u.newPost(createTestImage(), "Test post");
		
		Iterator<IPost> posts = u.getPosts();
		assertTrue(posts.hasNext());
		assertEquals("Test post", posts.next().getCaption());
		assertFalse(posts.hasNext());
	}


	
	@Test
	public void testSetCaption() throws Exception{
		IPost  p = createTestUser().newPost(createTestImage(), "Test post");
		String c = "A new caption";
		p.setCaption(c);
		assertEquals(c, p.getCaption());
	}

	@Test
	public void testSetImage1() throws Exception{
		IPost p = createTestUser().newPost(createTestImage(), "Test post");
		RenderedImage img = createTestImage();
		p.setImage(img);
		assertEqualDimensions(img, p.getImage());
	}

	@Test
	public void testSetImage2() throws Exception{
		IPost p = createTestUser().newPost(createTestImage(), "Test post");
		p.setImage(null);
		assertNull(p.getImage());
	}
	
	@Test
	public void testSetPostedAt() throws Exception{
		IPost p = createTestUser().newPost(createTestImage(), "Test post");
		LocalDateTime t = LocalDateTime.now().minusMinutes(42);
		p.setPostedAt(t);
		assertRoughlyEquals(t, p.getPostedAt());
	}

	
	
	
	@Test
	public void likePostThenCheckPostLikes1() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		u.like(p);
		
		Iterator<IUser> users = p.getLikes();
		assertTrue(users.hasNext());
		assertEquals(u.getUsername(), users.next().getUsername());
		assertFalse(users.hasNext());
	}

	@Test
	public void likePostThenCheckPostLikes2() throws Exception{
		IUser u1 = createTestUser();
		IUser u2 = createTestUser();
		IPost p = u1.newPost(createTestImage(), "Test post");
		u2.like(p);
		
		Iterator<IUser> users = p.getLikes();
		assertTrue(users.hasNext());
		assertEquals(u2.getUsername(), users.next().getUsername());
		assertFalse(users.hasNext());
	}
	
	@Test
	public void likePostThenCheckPostLikes3() throws Exception{
		IUser u1 = createTestUser();
		IUser u2 = createTestUser();
		IUser u3 = createTestUser();
		IPost p = u1.newPost(createTestImage(), "Test post");
		u2.like(p);
		u3.like(p);
		
		Iterator<IUser> users = p.getLikes();
		
		for (int i = 0; i < 2; i++) {
			assertTrue(users.hasNext());
			String username = users.next().getUsername();
			assertTrue(u2.getUsername().equals(username) || 
					   u3.getUsername().equals(username));
		}
		assertFalse(users.hasNext());
	}
	
	@Test
	public void addLikeToPostThenCheckUserLikes() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		p.addLike(u);
		
		Iterator<IPost> posts = u.getLikes();
		assertTrue(posts.hasNext());
		assertEquals("Test post", posts.next().getCaption());
		assertFalse(posts.hasNext());
	}
	
	@Test
	public void addLikeToPostThenCheckUserLikes2() throws Exception{
		IUser u1 = createTestUser();
		IPost p1 = u1.newPost(createTestImage(), "Test post 1");
		IUser u2 = createTestUser();
		IPost p2 = u2.newPost(createTestImage(), "Test post 2");
		
		IUser u3 = createTestUser();
		p1.addLike(u3);
		p2.addLike(u3);

		Iterator<IPost> posts = u3.getLikes();
		
		for (int i = 0; i < 2; i++) {
			assertTrue(posts.hasNext());
			String c = posts.next().getCaption();
			assertTrue("Test post 1".equals(c) || "Test post 2".equals(c));
		}
		assertFalse(posts.hasNext());
	}
	
	
	
	
	@Test
	public void testLikeUnlike1() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		u.like(p);
		u.unlike(p);
		
		assertFalse(p.getLikes().hasNext());
	}
	
	@Test
	public void testLikeUnlike2() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		u.like(p);
		u.unlike(p);
		
		assertFalse(u.getLikes().hasNext());
	}
	
	@Test
	public void testLikeUnlike3() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		p.addLike(u);
		p.removeLike(u);
		
		assertFalse(p.getLikes().hasNext());
	}
	
	@Test
	public void testLikeUnlike4() throws Exception{
		IUser u = createTestUser();
		IPost p = u.newPost(createTestImage(), "Test post");
		p.addLike(u);
		p.removeLike(u);
		
		assertFalse(u.getLikes().hasNext());
	}
	
	
	
	@Test
	public void serializingUserWithPostsDoesNotThrowAnError() throws Exception{
		IUser user = createTestUser();
		user.newPost(createTestImage(), "Test post");
		try(OutputStream output = new ByteArrayOutputStream()){
			serializer.serialize(user, output);
		}
	}

}
