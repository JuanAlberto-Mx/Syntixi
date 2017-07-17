package syntixi.util.func;

/**
 * The <code>P2</code> interface represents a function that accept two objects and
 * return nothing.
 *
 * @param <A> the first object.
 * @param <B> the second object.
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface P2<A, B> {

	/**
	 * Executes the function.
	 *
	 * @param a the first object.
	 * @param b the second object.
	 */
	void execute(A a, B b);
}