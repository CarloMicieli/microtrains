/*
   Copyright 2021 (C) Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.carlomicieli.util;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * It represent a container object that may or may not complete with a valid result.
 *
 * @author Carlo Micieli
 */
public interface Try<T> {

  /**
   * Checks whether it represents a computation that ends with a failure.
   *
   * @return <em>true</em> if this is a failure; <em>false</em> otherwise.
   */
  default boolean isFailure() {
    return !isSuccess();
  }

  /**
   * Checks whether it represents a computation that ends successfully.
   *
   * @return <em>true</em> if this is a success; <em>false</em> otherwise.
   */
  boolean isSuccess();

  /**
   * Returns the inner cause for failure. <em>Optional.empty()</em> for success.
   *
   * @return the inner cause for failure.
   */
  default Optional<Throwable> exception() {
    return Optional.empty();
  }

  /**
   * Returns the result value from the computation if <em>this</em> completed successfully, or
   * throws a <em>RuntimeException</em> wrapping the failure cause.
   *
   * @return the result value from <em>Success</em> or throw a <em>RuntimeException</em> after a
   *     <em>Failure</em>.
   */
  T get();

  /**
   * Applies the given <em>Supplier</em> f if this is a <em>Failure</em>, otherwise returns
   * <em>this</em> if this is a <em>Success</em>.
   *
   * @param f the function that supply the value to recover the computation
   * @return a Try object
   */
  Try<T> recoverWith(Supplier<Try<T>> f);

  /** Converts this to a <em>Failure</em> if the predicate is not satisfied. */
  Try<T> filter(Predicate<? super T> predicate);

  <U> Try<U> map(Function<? super T, ? extends U> mapper);

  <U> Try<U> flatMap(Function<? super T, Try<U>> mapper);

  /**
   * It execute the computation defined by the provided <em>Supplier</em> and wrap the result in a
   * <em>Try</em> container.
   *
   * <p>
   *
   * <p>The get() method for the provided Supplier can throw any kind ofValues exception. The
   * Exception subclasses are wrapped in <em>Failure</em> instances. Any other subclass ofValues
   * <em>Throwable</em> are wrapped in a unchecked exception avoiding the clients the requirement to
   * catch or rethrow the exceptions.
   *
   * <p>
   *
   * <pre>
   * <code>
   * 		// computation that succeeds
   * 		Try&lt;Integer&gt; success = Try.apply( () -&gt; 1 + 1 );
   * 		assert(success.isSuccess() == true);
   *
   * 		// computation that fails
   * 		Try&lt;Integer&gt; fail = Try.apply( () -&gt; 1 / 0 );
   * 		assert(fail.isFailure() == true);
   * </code>
   * </pre>
   *
   * @param op the computation that supplies a result if succeeds
   * @return a success object or a failure object
   */
  static <T> Try<T> apply(Supplier<T> op) {
    try {
      T result = op.get();
      return success(result);
    } catch (Exception ex) {
      return failure(ex);
    } catch (Throwable th) {
      throw new FatalException(th);
    }
  }

  /**
   * Returns <em>empty</em> if this is a Failure or an <em>Optional</em> containing the value if
   * this is a <em>Success</em>.
   *
   * @return an Optional that contains the computation result if Success, <em>empty</em> otherwise.
   */
  default Optional<T> toOptional() {
    if (isSuccess()) return Optional.ofNullable(get());

    return Optional.empty();
  }

  /**
   * Return the computation result if <em>Success</em>, or the value provided by the given function.
   *
   * @param supplier the function to supply a computation result for <em>Failure</em>
   * @return the computation result if <em>Supplier</em>, or a default value supplied by a function.
   */
  default T getOrElse(Supplier<T> supplier) {
    if (isSuccess()) return get();

    return supplier.get();
  }

  /**
   * Creates a <em>Success</em> object with the provided value.
   *
   * @param t the computation result
   * @return a <em>Success</em> object
   */
  static <T> Try<T> success(T t) {
    return new Success<>(t);
  }

  /**
   * Creates a <em>Failure</em> object with the provided value.
   *
   * @param ex the wrapped exception
   * @return a <em>Failure</em> object
   */
  static <T> Try<T> failure(Throwable ex) {
    return new Failure<>(ex);
  }

  /** It represents a successful computation that ends with a result. */
  class Success<T> implements Try<T> {
    private final T t;

    public Success(T t) {
      this.t = t;
    }

    @Override
    public boolean isSuccess() {
      return true;
    }

    @Override
    public T get() {
      return t;
    }

    @Override
    public Try<T> filter(Predicate<? super T> predicate) {
      if (predicate.test(get())) return this;

      return new Failure<>();
    }

    @Override
    public <U> Try<U> map(Function<? super T, ? extends U> mapper) {
      return success(mapper.apply(get()));
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
      return mapper.apply(get());
    }

    @Override
    public Try<T> recoverWith(Supplier<Try<T>> f) {
      return this;
    }
  }

  /** It represents a failed computation that ends up in a non fatal exception. */
  class Failure<T> implements Try<T> {
    private final Throwable th;

    private Failure() {
      this.th = new NoSuchElementException();
    }

    public Failure(Throwable th) {
      this.th = th;
    }

    @Override
    public Optional<Throwable> exception() {
      return Optional.ofNullable(th);
    }

    @Override
    public boolean isSuccess() {
      return false;
    }

    @Override
    public T get() {
      if (th instanceof RuntimeException) {
        throw (RuntimeException) th;
      }

      throw new RuntimeException(th);
    }

    @Override
    public Try<T> filter(Predicate<? super T> predicate) {
      return this;
    }

    @Override
    public <U> Try<U> map(Function<? super T, ? extends U> mapper) {
      return failure(th);
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
      return failure(th);
    }

    @Override
    public Try<T> recoverWith(Supplier<Try<T>> f) {
      return f.get();
    }
  }

  class FatalException extends RuntimeException {
    public FatalException(Throwable th) {
      super(th);
    }
  }
}
