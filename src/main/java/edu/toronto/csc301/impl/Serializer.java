package edu.toronto.csc301.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;
import edu.toronto.csc301.Post;
import edu.toronto.csc301.Util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;



public class Serializer implements ISerializer {
	private static JsonFactory jfactory = new JsonFactory();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:nnnnnnnnn");
	
	public Serializer(){	
	}
	
	public void serialize(IUser user, OutputStream output) throws Exception {
		
		//Check Input
		if(user == null || output == null){
			throw new NullPointerException();
		}
		
		JsonGenerator jGenerator = jfactory.createJsonGenerator(output, JsonEncoding.UTF8);
		
		jGenerator.writeStartArray();
		
		bfs(user, jGenerator);
		
		jGenerator.writeEndArray();
		
		jGenerator.close();
	}

	@Override
	public IUser deserializeUser(InputStream input) throws Exception {
		
		if(input == null){
			throw new NullPointerException();
		}
		
		JsonParser jParser = jfactory.createJsonParser(input);
		
		IUserStore userStore = new UserStore();
		
		Map<IPost, Set<String>> PostToSetOfUsers = new HashMap<IPost, Set<String>>();
		
		IUser currentUser = null;
		IUser returnedUser = null;
		boolean isFirst = true;
		
		while(jParser.nextToken()!=JsonToken.END_ARRAY){
			currentUser = deserializeUserHelper(jParser,userStore,PostToSetOfUsers);
			if(isFirst){
				isFirst = false;
				returnedUser = currentUser;
			}
		}
		Iterator<IPost> keyIterPosts =  PostToSetOfUsers.keySet().iterator();
		
		while(keyIterPosts.hasNext()){
			IPost currentPost = keyIterPosts.next();
			Iterator<String> valueItrUsers = PostToSetOfUsers.get(currentPost).iterator();
			while(valueItrUsers.hasNext()){
				String s = valueItrUsers.next();
				if(!s.equals("")){
					userStore.getUser(s).like(currentPost);
				}
			}
		}
		
		return returnedUser;
	}


////HELPERS
	
	public void bfs(IUser start, JsonGenerator jGenerator) throws Exception{
		
		List<IUser> queue = new ArrayList<IUser>();
		Set<String> alreadyVisited = new HashSet<String>();
		queue.add(start);
		
		while(!queue.isEmpty()){
			IUser removedUser = ((IUser) queue.remove(0));
			
			// Visit the user and make sure we haven't already visited it,
			if(!alreadyVisited.contains(removedUser.getUsername())){
				alreadyVisited.add(removedUser.getUsername());	
				softSerializer(removedUser, jGenerator);
			}
			
			Collection<IUser> neighbours = getNeighbours(removedUser);
			for(IUser neighbour : neighbours){
				if(! alreadyVisited.contains(neighbour.getUsername())){
					queue.add(neighbour);
				}
			}
		}
	}
	
	private Collection<IUser> getNeighbours(IUser user){
		Map<String,IUser> username2user = new HashMap<String,IUser>();
		
		Iterator<IPost> myPosts = user.getPosts();
		while (myPosts.hasNext()) {
			Iterator<IUser> usersWhoLikeMyPosts = myPosts.next().getLikes();
			while (usersWhoLikeMyPosts.hasNext()) {
				IUser u = usersWhoLikeMyPosts.next();
				username2user.put(u.getUsername(), u);
			}			
		}
		
		// TODONE: People whose posts I like ...
		Iterator<IPost> myLikedPosts = user.getLikes();
		while(myLikedPosts.hasNext()){
			IPost usedWhoCreatedPosts = myLikedPosts.next();
			username2user.put(usedWhoCreatedPosts.getPostedBy().getUsername(), usedWhoCreatedPosts.getPostedBy());
		}
		
		return username2user.values();
	}
		
	private void softSerializer(IUser user, JsonGenerator jGenerator) throws IOException{
		
		jGenerator.writeStartObject();	//{
		//USERNAME
		jGenerator.writeStringField("username", user.getUsername());
		
		//PASSWORD
		jGenerator.writeStringField("password", user.getPassword());
		
		//Registration Time
		String date = formatter.format(user.getRegistrationTime());
		jGenerator.writeStringField("date", date);
		
		//The User's Posts
		jGenerator.writeFieldName("postsOfUser"); // "postsOfUser" :
		jGenerator.writeStartArray(); // [
		
		Iterator<IPost> iterPosts = user.getPosts();
		//while the iterator still has Posts
		while(iterPosts.hasNext()){
			
			//get the current Post
			IPost thisPost = iterPosts.next();
			
			//Start writing the current post to a New object
			jGenerator.writeStartObject(); 		// {
			
			jGenerator.writeStringField("caption", thisPost.getCaption());
			
			jGenerator.writeBinaryField("image", Util.imageToByteArray(thisPost.getImage()));
			
			String postDate = formatter.format(user.getRegistrationTime());
			jGenerator.writeStringField("datePosted", postDate);
			
			jGenerator.writeFieldName("UserWhoLike"); // "UserWhoLike" :
			jGenerator.writeStartArray(); // [
			Iterator<IUser> UsersWhoLikeThisPost = thisPost.getLikes();
			while(UsersWhoLikeThisPost.hasNext()){
				jGenerator.writeString(UsersWhoLikeThisPost.next().getUsername());
				
			}
			jGenerator.writeEndArray(); // ]
			
			jGenerator.writeEndObject(); 		// }
		}
		jGenerator.writeEndArray(); // ]
		
		jGenerator.writeEndObject();	// }
		
//		jGenerator.close();
	}
		
	private IUser deserializeUserHelper(JsonParser jParser, IUserStore userStore,Map<IPost, Set<String>> PostToSetOfUsers) throws Exception{
		IUser currentUser = null;
		

//		System.out.println("-------------NEW OBJECT--------");
		while (jParser.nextToken() != JsonToken.END_OBJECT) {
			String fieldname = jParser.getCurrentName();
			if ("username".equals(fieldname)) {
				jParser.nextToken();
				currentUser = userStore.createUser(jParser.getText(), "");
			}
			if ("password".equals(fieldname)) {
				jParser.nextToken();
				currentUser.setPassword(jParser.getText());
			}
			if ("date".equals(fieldname)) {
				jParser.nextToken();
				currentUser.setRegistrationTime(LocalDateTime.parse(jParser.getText(), formatter));
			}
			
			if("postsOfUser".equals(fieldname)) {
				jParser.nextToken();
				while (jParser.nextToken() != JsonToken.END_ARRAY) {
					
					//INSIDE POST OBJECT
					IPost currentPost = currentUser.newPost(null, "");
					Set<String> userStringSet = new HashSet<String>();
					
					while (jParser.nextToken() != JsonToken.END_OBJECT) {
						fieldname = jParser.getCurrentName();
						
						if ("caption".equals(fieldname)) {
							jParser.nextToken();currentPost.setCaption(jParser.getText());
						}
						
						if ("image".equals(fieldname)) {
							jParser.nextToken();
							currentPost.setImage(Util.byteArrayToImage(jParser.getBinaryValue()));
						}
						
						if ("datePosted".equals(fieldname)) {
							jParser.nextToken();
							currentPost.setPostedAt(LocalDateTime.parse(jParser.getText(), formatter));
						}
						if ("UserWhoLike".equals(fieldname)) {
							jParser.nextToken();
							while (jParser.nextToken() != JsonToken.END_ARRAY) {
								userStringSet.add(jParser.getText());
							}
						}
					}
					PostToSetOfUsers.put(currentPost, userStringSet);
				}
			}
		}
		return currentUser;
	}

}
