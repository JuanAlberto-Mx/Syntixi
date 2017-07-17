package syntixi.util.func;

/**
 * The <code>P3</code> interface represents a function that accept three objects and
 * returns nothing.
 *
 * @param <A> the first object.
 * @param <B> the second object.
 * @param <C> the third object.
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface P3<A, B, C> {

	/**
	 * Executes the function.
	 *
	 * @param a the first object.
	 * @param b the second object.
	 * @param c the third object.
	 */
	void execute(A a, B b, C c);
}