package edu.toronto.csc301;


import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.Iterator;

public interface IUser {

	// Properties

	public String getUsername();

	public String getPassword();
	public void setPassword(String password);

	public LocalDateTime getRegistrationTime();
	public void setRegistrationTime(LocalDateTime registrationTime);


	// Users can create posts 

	public IPost newPost(RenderedImage image, String caption);
	public Iterator<IPost> getPosts();


	// Users can like posts

	public void like(IPost post);
	public void unlike(IPost post);
	public Iterator<IPost> getLikes();

}
