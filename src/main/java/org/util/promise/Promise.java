package org.util.promise;

import java.io.Serializable;

/**
 * Promise.
 *
 * @author 44380
 * @since 1
 */
public interface Promise<T> extends Serializable {
  /**
   * set a object.
   *
   * @param obj object
   */
  void set(T obj);

  /**
   * set a exception.
   *
   * @param e exception
   */
  void setFailed(Throwable e);

  /**
   * get a result or throw.
   *
   * @return result
   */
  T get();
}
