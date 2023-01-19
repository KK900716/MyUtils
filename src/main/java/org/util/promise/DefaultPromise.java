package org.util.promise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A default Promise.
 *
 * @author 44380
 * @since 1
 */
public final class DefaultPromise<T> implements Promise<T> {
  private static final long serialVersionUID = 1L;
  private T obj;
  private Throwable e;
  private volatile boolean hasValue = false;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition con = lock.newCondition();

  @Override
  public void set(T obj) {
    this.obj = obj;
    hasValue = true;
    lock.lock();
    try {
      con.signal();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void setFailed(Throwable e) {
    this.e = e;
    hasValue = true;
    lock.lock();
    try {
      con.signal();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public T get() {
    if (hasValue) {
      return getRes();
    }
    lock.lock();
    try {
      con.await();
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    } finally {
      lock.unlock();
    }
    return getRes();
  }

  private T getRes() {
    if (e != null) {
      throw new RuntimeException(e);
    }
    return obj;
  }
}
