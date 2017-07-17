package syntixi.util.func;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <code>Functions</code> class provides a set of method which applies some principles of
 * <code>Functional Programming</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Functions extends GeneratedFunctions {

	/**
	 * Executes a specific action.
	 *
	 * @param action the action to execute.
	 * @return the <code>Runnable</code> action.
	 */
	public static Runnable runnable(final P0 action) {
		return new Runnable() {
			public void run() {
				action.execute();
			}
		};
	}

	/**
	 * Stores the object to the collection.
	 *
	 * @param collection the collection to execute.
	 * @return the <code>P1</code> instance.
	 */
	public static <A> P1<A> store(final Collection<A> collection) {
		return new P1<A>() {
			public void execute(A a) {
				collection.add(a);
			}
		};
	}

	/**
	 * Sets an atomic reference with an object instance.
	 *
	 * @param atomicReference the atomic reference.
	 * @param <A> the function type.
	 * @return the new instance.
	 */
	public static <A> P1<A> setter(final AtomicReference<A> atomicReference) {
		return new P1<A>(){
			public void execute(A obj) {
				atomicReference.set(obj);
			}
		};
	}

	/**
	 * Invokes all objects in the collection.
	 *
	 * @param collection the collection.
	 * @param a the <code>A</code> instance.
	 * @param <A> the new <code>A</code> instance.
	 */
	public static <A> void invokeAll(final Collection<P1<A>> collection, A a) {
		for (P1<A> p1 : collection)
			p1.execute(a);
	}
}