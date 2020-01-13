package edu.toronto.csc301;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Post implements IPost {
	private RenderedImage image;
	private String caption;
	private IUser owner = null;
	private List<IUser> usersThatLiked;
	private LocalDateTime postedAt;
	
	public Post(RenderedImage argImage, String argCaption){
		if(argImage == null && argCaption == null){
			throw new IllegalArgumentException();
		}
		this.image = argImage;
		this.caption = argCaption;
		this.postedAt = LocalDateTime.now();
		this.usersThatLiked = new ArrayList<IUser>();
	}
	@Override
	public RenderedImage getImage() {
		return this.image;
	}
	
	@Override
	public void setImage(RenderedImage profilePic) {
		this.image = profilePic;
	}
	
	@Override
	public String getCaption() {
		return this.caption.toString();
	}
	
	@Override
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	@Override
	public IUser getPostedBy() {
		return this.owner;
	}
	
	@Override
	public void setPostedBy(IUser user) {
		this.owner = user;
	}
	
	@Override
	public LocalDateTime getPostedAt() {
		return this.postedAt;
	}
	
	@Override
	public void setPostedAt(LocalDateTime time) {
		this.postedAt = time;
	}
	
	@Override
	public Iterator<IUser> getLikes() {
		return usersThatLiked.iterator();
	}
	
	@Override
	public void addLike(IUser user) {
		usersThatLiked.add(user);
//		user.like(post);
		Iterator<IPost> likedUserPosts = user.getLikes();
		boolean alreadyCalledUserLike = false;
		while(likedUserPosts.hasNext()){
			if(likedUserPosts.next().equals(this)){
				alreadyCalledUserLike = true;
			}
		}
		if(alreadyCalledUserLike == false){
			user.like(this);
		}
	}
	
	@Override
	public void removeLike(IUser user) {
		usersThatLiked.remove(user);
		Iterator<IPost> likedUserPosts = user.getLikes();
		boolean alreadyCalledUserUnLike = true;
		while(likedUserPosts.hasNext()){
			if(likedUserPosts.next().equals(this)){
				alreadyCalledUserUnLike = false;
			}
		}
		if(alreadyCalledUserUnLike == false){
			user.unlike(this);
		}
	}
	
}
