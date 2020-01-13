package edu.toronto.csc301.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;

import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;
import edu.toronto.csc301.util.CSC301TestBase;

public class AbsTest extends CSC301TestBase{
	
	
	private static final String PACKAGE = "edu.toronto.csc301.impl.";
	

	public static final String CLASS_NAME_USER_STORE = PACKAGE + "UserStore";
	public static final String CLASS_NAME_SERIALIZER = PACKAGE + "Serializer";
	
	
	protected IUserStore userStore;
	protected ISerializer serializer;
	protected Random random = new Random();
	
	
	@Before
	public void setup() throws Exception{
		userStore = createUserStore();
		serializer = createSerializer();
	}
	
	protected IUserStore createUserStore() throws Exception{
		return newInstance(CLASS_NAME_USER_STORE);
	}
	
	protected ISerializer createSerializer() throws Exception{
		return newInstance(CLASS_NAME_SERIALIZER);
	}
	
	//-------------------------------------------------------------------------
	
	
	protected RenderedImage createTestImage() throws IOException{
		return new BufferedImage(16 + random.nextInt(96), 
				16 + random.nextInt(96), BufferedImage.TYPE_INT_RGB);
	}
	
	protected IUser createTestUser() throws Exception{
		return createTestUser(this.userStore);
	}
	
	protected IUser createTestUser(IUserStore userStore) throws Exception{
		return userStore.createUser(UUID.randomUUID().toString(), 
									UUID.randomUUID().toString());
	}
	
	protected Set<String> toSet(String ... strings){
		Set<String> s = new HashSet<String>();
		Collections.addAll(s, strings);
		return s;
	}

	//-------------------------------------------------------------------------
	
	
	protected void assertRoughlyEquals(LocalDateTime t1, LocalDateTime t2){
		long milli1 = t1.toInstant(ZoneOffset.UTC).toEpochMilli();
		long milli2 = t2.toInstant(ZoneOffset.UTC).toEpochMilli();
		long diffInMilliSec = Math.abs(milli2 - milli1);
		assertTrue("Difference is " + diffInMilliSec + " ms.", diffInMilliSec < 100);
	}
	
	protected void assertEqualDimensions(RenderedImage expected, RenderedImage actual){
		Objects.requireNonNull(expected);
		assertNotNull(actual);
		assertEquals(expected.getWidth(),  actual.getWidth());
		assertEquals(expected.getHeight(), actual.getHeight());
	}
	
	protected void assertIteratorVisitsExactly(Iterator<IUser> iterator, String ... usernames){
		Set<String> usernamesFromIterator = new HashSet<String>();
		while (iterator.hasNext()) {
			usernamesFromIterator.add(iterator.next().getUsername());
		}
		assertEquals(toSet(usernames), usernamesFromIterator);
	}
	
	protected void assertIteratorVisitsExactly(Iterator<IUser> iterator, IUser ... users){
		String[] usernames = new String[users.length];
		for (int i = 0; i < users.length; i++) {
			usernames[i] = users[i].getUsername();
		}
		assertIteratorVisitsExactly(iterator, usernames);
	}
	
	
	//-------------------------------------------------------------------------
	
	
	protected IUser serializeDeserialize(IUser user) throws Exception{
		try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
			createSerializer().serialize(user, output);
			output.flush();
			
			try(ByteArrayInputStream  input  = new ByteArrayInputStream(output.toByteArray())){
				return createSerializer().deserializeUser(input);
			}
		}
	}
	
}
