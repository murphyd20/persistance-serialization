package edu.toronto.csc301.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.toronto.csc301.test.objectGraphs.ObjectGraph1;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph1a;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph2;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph3;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph3b;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph3c;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph4;
import edu.toronto.csc301.test.objectGraphs.ObjectGraph5;
import edu.toronto.csc301.test.objectGraphs.Prerequisite;


@RunWith(Suite.class)
@SuiteClasses({
	Prerequisite.class,
	ObjectGraph1.class, ObjectGraph1a.class, 
	ObjectGraph2.class, 
	ObjectGraph3.class, ObjectGraph3b.class, ObjectGraph3c.class, 
	ObjectGraph4.class,
	ObjectGraph5.class
})
public class ObjectGraphTest {
}
