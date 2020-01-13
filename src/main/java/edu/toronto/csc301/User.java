package edu.toronto.csc301;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements IUser {
	private String username;
	private String password;
	private LocalDateTime registerTime= LocalDateTime.now();;
	private List<IPost> postsOfUser;
	private List<IPost> likedPosts;
	
	public User(String username, String password){
		if(username == "" || username.trim().length() == 0){throw new IllegalArgumentException();}
		if(username == null || password == null){throw new NullPointerException();}
		this.username = username;
		this.password = password;
		this.postsOfUser = new ArrayList<IPost>();
		this.likedPosts = new ArrayList<IPost>();
	}
	
	public User(String username, String password, LocalDateTime date){
		this(username, password);
		this.registerTime = date;		//NOT SURE IF NEEDED
	}
//	
//	public User() {
//		this.username = "";
//		this.password = "";
//		this.registerTime = LocalDateTime.now();
//		// TODO Auto-generated constructor stub
//	}

	//THIS IS FINE
	public String getUsername() {
		if(this.username == null){
			throw new NullPointerException();
		}
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public LocalDateTime getRegistrationTime() {
		return this.registerTime;
	}

	@Override
	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registerTime = registrationTime;
	}
	
	public Iterator<IPost> getPosts() {
		return this.postsOfUser.iterator();
	}
	
	@Override
	public IPost newPost(RenderedImage image, String caption) {
		IPost postCreated = new Post(image, caption);
		postCreated.setPostedBy(this);
//		this.postsOfUser.add(postCreated);
		this.postsOfUser.add(postCreated);
		return postCreated;
	}

	@Override
	public void like(IPost post) {
		this.likedPosts.add(post);
		post.addLike(this);
	}

	@Override
	public void unlike(IPost post) {
		this.likedPosts.remove(post);
		post.removeLike(this);
	}

	@Override
	public Iterator<IPost> getLikes() {
		return likedPosts.iterator();
	}

}
