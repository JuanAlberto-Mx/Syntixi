package syntixi.util.func;

/**
 * <code>GeneratedFunctions</code> class provides a set of methods to execute
 * and convert functions.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class GeneratedFunctions {

	/**
	 * Converts from <code>P0</code> to <code>P1</code>.
	 *
	 * @return <code>P1</code>.
	 */
	public static <A> P1<A> p1(final P0 function) {
		return new P1<A>() {
			public void execute(final A a) {
				function.execute();
			}
		};
	}

	/**
	 * Calls to <code>P1</code> and return the fixed value.
	 *
	 * @param function the function to call before return the value.
	 * @param value the fixed value to return.
	 * @return value
	 */
	public static <A, R> F1<A, R> f1(final P1<A> function, final R value) {
		return new F1<A,R>() {
			public R execute(final A a) {
				function.execute(a);
				return value;
			}
		};
	}

	/**
	 * Calls to <code>P2</code> and return the fixed value.
	 *
	 * @param function the function to call before return the value.
	 * @param value the fixed value to return.
	 * @return value.
	 */
	public static <A, B, R> F2<A, B, R> f2(final P2<A, B> function, final R value) {
		return new F2<A, B, R>() {
			public R execute(final A a, final B b) {
				function.execute(a, b);
				return value;
			}
		};
	}
}