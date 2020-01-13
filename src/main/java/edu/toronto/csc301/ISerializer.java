package edu.toronto.csc301;


import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializer {

	public void serialize(IUser user, OutputStream output) throws Exception;
	public IUser deserializeUser(InputStream input) throws Exception;
	
}
