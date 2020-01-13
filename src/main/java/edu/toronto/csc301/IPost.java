package edu.toronto.csc301;


import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.Iterator;

public interface IPost {

	public RenderedImage getImage();
	public void setImage(RenderedImage profilePic);
	
	public String getCaption();
	public void setCaption(String caption);
	
	public IUser getPostedBy();
	public void setPostedBy(IUser user);
	
	public LocalDateTime getPostedAt();
	public void setPostedAt(LocalDateTime time);
	
	public Iterator<IUser> getLikes();
	public void addLike(IUser user);
	public void removeLike(IUser user);
	
}
