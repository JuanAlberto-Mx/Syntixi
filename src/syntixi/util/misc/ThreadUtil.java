package syntixi.util.misc;

import syntixi.util.func.F0;
import syntixi.util.func.Functions;
import syntixi.util.func.P0;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The <code>ThreadUtil</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ThreadUtil {

	/**
	 * The executor pool.
	 */
	private static final ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * Executes a given function.
	 *
	 * @param function the function to execute.
	 */
	public static void runStrong(final P0 function) {
		executorService.execute(Functions.runnable(function));
	}

	/**
	 * Executes a function with thread local cache.
	 *
	 * @param function the function to execute.
	 * @param <A> the function type.
	 * @return the ThreadLocalCache.
	 */
	public static <A> ThreadLocalCache<A> threadLocalCache(final F0<A> function) {
		final ThreadLocal<A> threadLocal = new ThreadLocal<A>();

		ThreadLocalCache<A> threadLocalCache = new ThreadLocalCache<A>();

		threadLocalCache.cacheF0 = new F0<A>() {
			public A execute() {
				A a = threadLocal.get();

				if(a == null) {
					a = function.execute();

					threadLocal.set(a);
				}

				return a;
			}
		};

		threadLocalCache.removeF0 = new F0<A>() {
			public A execute() {
				A a = threadLocal.get();

				threadLocal.set(null);

				return a;
			}
		};

		return threadLocalCache;
	}

	/**
	 * <code>ThreadLocalCache<A></code> class.
	 *
	 * @param <A> the class type.
	 */
	public static class ThreadLocalCache<A> {
		public F0<A> cacheF0;
		public F0<A> removeF0;
	}

	/**
	 * Sleep and wake on <code>InterruptedException</code>.
	 *
	 * @param time the time expressed in milliseconds.
	 */
	public static void sleep(long time) {
		if(time <= 0)
			return;

		try {
			Thread.sleep(time);
		}
		catch(InterruptedException e) {
		}
	}
}