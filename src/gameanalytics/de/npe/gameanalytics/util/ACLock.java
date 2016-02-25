/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.util;

import java.util.concurrent.locks.ReentrantLock;


/**
 * An auto closeable lock. It can be used to lock blocks of code by using
 * try-with-resource instead of having to keep an eye on proper unlock manually.<br>
 * <br>
 * <b>Usage:</b>
 *
 * <pre>
 * private ACLock myLock = new ACLock();
 *
 * public void someMethod() {
 * 	try (ACLock acl = myLock.lockAC()) {
 * 		// this block can only be entered by one thread at a time
 * 		doStuffThatNeedsThreadSafety();
 * 	}
 * }
 * </pre>
 *
 * @author NPException
 *
 */
public class ACLock extends ReentrantLock implements AutoCloseable {
	private static final long serialVersionUID = -2604054164317029860L;

	public ACLock() {
		super(false);
	}

	public ACLock(boolean fair) {
		super(fair);
	}

	public ACLock lockAC() {
		lock();
		return this;
	}

	@Override
	public void close() {
		unlock();
	}
}
