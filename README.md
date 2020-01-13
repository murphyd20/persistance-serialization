## Learning goals

 - Persistence & Serialization, advanced concepts
   - Serialize objects, without relying entirely on `java.io.Serializable` and `java.io.ObjectOutputStream`
   - Serialize a network of objects.

## Getting Started

 1. [Fork][github-fork] this repo.

     > _Note:_ Your fork is private and is only visible to you, the TAs and the instructors.

 2. Make sure that [issues][github-issues] are [enabled](https://help.github.com/articles/disabling-issues/) in your fork.     
     > You are not required to use issues for your individual assignments, but it's probably a good idea.
 

## Your Task

Implement the code to pass the provided unit tests.        
See the marking scheme below for more details.

## Guidance

Before you start coding, it is important that you understand the challenges and make a plan.        


#### Challenge 1 - Serializing without `ObjectOutputStream`

One of the limitation of Java's `ObjectOutputStream` is that it can only serialize objects that implement the `Serializable` interface. [`RenderedImage`](http://docs.oracle.com/javase/8/docs/api/java/awt/image/RenderedImage.html) is an example of a class that does *not* implement the `Serializable` interface. Therefore, instances of `RenderedImage` cannot be serialized using `ObjectOutputStream`. Also, any object that stores a `RenderedImage` instance variable cannot be serialized using `ObjectOutputStream`.

For this assignment, the [`IPost`](src/main/java/edu/toronto/csc301/IPost.java) interface declares the following methods:
 
```java
public RenderedImage getImage();
public void setImage(RenderedImage profilePic);
```

Your serializer is expected to be able to serialize any implementation of `IPost`. Particularly, implementations that have a [`RenderedImage`](http://docs.oracle.com/javase/8/docs/api/java/awt/image/RenderedImage.html) instance variable (that's what the [Prerequisite](src/test/java/edu/toronto/csc301/test/objectGraphs/Prerequisite.java) is all about).       
Therefore, you will not be able to use `ObjectOutputStream` for serialization (at least not directly).

**Q:** _How should you handle this challenge?_

Serialize objects to JSON or XML (or even your own custom format).

That being said, you might find a solution that, with some extra work, will allow you to use Java's `ObjectOutputStream` (I haven't tried to implement such a solution myself). Just make sure your serializer can handle _any_ implementation of `IUser` and `IPost`.


**Q:** _How can you serialize `RenderedImage` instances?_

For the purpose of this assignment, feel free to use [this utility class](src/main/java/edu/toronto/csc301/Util.java).       
It has simple helper functions that convert `RenderedImage` to/from `byte[]` (i.e. data that can be written somewhere).      

#### Challenge 2 - Serializing an object graph

When you serialize a user, you must also serialize its full object graph.         
That is, the user, the posts he/she posted and/or liked, other users
who posted and/or liked these posts, all of their posts, and so on.

Before you start coding, make sure you have a clear idea of what a serialized object graph will look like, and how you would deserialize it back into in-memory objects.

You might find it useful to break this task into two:
 1. Given a `IUser`, visit every object in its object graph.          
    (think of _CSC263_ and graph algorithms like _breadth/depth first search_)
 2. Representing an object graph (i.e. users, posts and the relations between them) as a chunk of data. 
 
_Note:_ You can assume that users in the same object graph have unique usernames (it is enforced by the user store). 

_Hint:_ When reassembling a network of objects (during deserialization), unique identifiers are often useful.


## Deliverables

Your code, submitted as a single, non-conflicting [pull-request][github-pull-requests] from your fork to the handout repo (i.e. the repo you forked).

## Marking Scheme

Your code will be marked automatically, according to the following scheme:

 * 50%, [Basic tests](src/test/java/edu/toronto/csc301/test/basic)
   * To get the mark, you must pass all of the tests. No partial marks.
 * 50%, [Advanced tests](src/test/java/edu/toronto/csc301/test/ObjectGraphTest.java)
   * For a full mark (50 out of 50), you must pass all of the tests.
   * For a partial mark (25 out of 50), you must pass the [Prerequisite](src/test/java/edu/toronto/csc301/test/objectGraphs/Prerequisite.java) test and fail no more than 10 tests.



## Important Notes

 1. Do not add any collaborators or teams to your fork!

    > Since you are the owner of your fork repo, GitHub allows you to share it with
others. Note that GitHub also allows us (the instructors and TA's) to see if
share your fork with anyone.

  If you share your fork with anyone, you will **automatically get a 0 mark** for this assignment.
  
 2. After you submit your assignment, make sure to check the results of Travis CI.
 
     > If your code passes the tests on your computer, but fails on Travis, then your code is broken.       

    It is **your responsibility** to make sure your code compiles!
  
 3. Do not modify any of given interfaces or testing code!
 
    > If you do, then Travis will no longer be useful, since it will no longer run the same tests as our auto-marker.
    


[github-issues]: https://guides.github.com/features/issues/
[github-guides]: https://guides.github.com/ "GitHub guides"
[github-fork]: https://guides.github.com/activities/forking/ "Guide to GitHub fork"
[github-pull-requests]: https://help.github.com/articles/using-pull-requests/ "Guide to GitHub Pull-Requests"

