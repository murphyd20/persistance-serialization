package edu.toronto.csc301.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;
import edu.toronto.csc301.User;

public class UserStore implements IUserStore {
	private ArrayList<IUser> userArrayList = new ArrayList<IUser>();
	public UserStore(){}
	
	@Override
	public IUser createUser(String username, String password) throws Exception {
		User u = new User(username, password);
		for(int i = 0; i < userArrayList.size() ;i++){
			if(userArrayList.get(i).getUsername().equals(username)){
				throw new IllegalArgumentException();
			}
		}
		userArrayList.add(u);
		return u;
	}

	@Override
	public IUser getUser(String username) {
		if(username == null){
			throw new NullPointerException();
		}
		for(int i = 0; i<userArrayList.size();i++){
			if(userArrayList.get(i).getUsername().equals(username)){
				return userArrayList.get(i);
			}
		}
		return null;
	}

	@Override
	public Iterator<IUser> getAllUsers() {
		return userArrayList.iterator();
	}
}
