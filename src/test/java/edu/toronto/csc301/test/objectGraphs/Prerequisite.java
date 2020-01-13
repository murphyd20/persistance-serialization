package edu.toronto.csc301.test.objectGraphs;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.test.AbsTest;

/**
 * This class has a single test method and a single purpose - Make sure you 
 * are not bypassing the challenging part of the assignment (and, by doing that, 
 * missing the learning goal) by letting ObjectOutputStream do all of the hard 
 * work for you.
 * 
 * In order to do that, we created dummy implementations of IUser and IPost (see code below).
 * Our implementations are incomplete (in more ways than one), but the important 
 * points are:
 * 1. All of their getters are implemented.
 *    Therefore, your serializer should be able to serialize them.
 * 2. The IPost implementation has a RenderedImage field.
 *    Therefore, Java's built-in ObjectOutputStream will not be able to serialize it.
 *    
 */
public class Prerequisite extends AbsTest{


	@Test
	public void canSerializeRenderedImageWithoutError() throws Exception{
		IUser u1 = new DummyUser();
		IUser u2 = serializeDeserialize(u1);
		
		assertEqualDimensions(u1.getPosts().next().getImage(), 
							  u2.getPosts().next().getImage()); 
	}


	//-------------------------------------------------------------------------
	// Dummy implementations of IUser and IPost ...
	// (for more info, see comment at the top of the file)
	

	private class DummyUser implements IUser {

		private LocalDateTime registrationTime;
		private Collection<IPost> posts;

		public DummyUser() throws IOException {
			this.posts = new LinkedList<IPost>();
			for (int i = 0; i < 3; i++) {
				this.posts.add(new DummyPost(this));
			}
			this.registrationTime = LocalDateTime.now();
		}

		@Override
		public String getUsername() {
			return "alice";
		}

		@Override
		public String getPassword() {
			return "123456";
		}

		@Override
		public LocalDateTime getRegistrationTime() {
			return registrationTime;
		}

		@Override
		public Iterator<IPost> getPosts() {
			return posts.iterator();
		}

		@Override
		public Iterator<IPost> getLikes() { 
			return Collections.emptyIterator(); 
		}



		@Override
		public void setPassword(String password) {}

		@Override
		public void setRegistrationTime(LocalDateTime registrationTime) {}

		@Override
		public IPost newPost(RenderedImage image, String caption) { return null; }

		@Override
		public void like(IPost post) {}

		@Override
		public void unlike(IPost post) {}
	}



	private class DummyPost implements IPost {

		IUser postedBy;
		RenderedImage image;
		LocalDateTime postedAt;

		public DummyPost(IUser user) throws IOException {
			this.postedBy = user;
			// A BufferedImage cannot be serialized by Java's ObjectOutputStream.
			this.image = new BufferedImage(32, 48, BufferedImage.TYPE_INT_RGB);
			this.postedAt = LocalDateTime.now();
		}

		@Override
		public RenderedImage getImage() {
			return image;
		}

		@Override
		public String getCaption() {
			return "Fake post";
		}

		@Override
		public IUser getPostedBy() {
			return postedBy;
		}

		@Override
		public LocalDateTime getPostedAt() {
			return postedAt;
		}

		@Override
		public Iterator<IUser> getLikes() { 
			return Collections.emptyIterator();
		}


		@Override
		public void setImage(RenderedImage profilePic) {}

		@Override
		public void setCaption(String caption) {}

		@Override
		public void setPostedBy(IUser user) {}

		@Override
		public void setPostedAt(LocalDateTime time) {}

		@Override
		public void addLike(IUser user) {}

		@Override
		public void removeLike(IUser user) {}		
	}


}
