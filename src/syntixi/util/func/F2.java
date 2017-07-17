package syntixi.util.func;

/**
 * Represents a function that accept two parameters and return a value.
 *
 * @param <A> the first parameter.
 * @param <B> the second parameter.
 * @param <T> the return value.
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface F2<A, B, T> {

	/**
	 * Evaluates or execute the function.
	 *
	 * @param a the first parameter.
	 * @param b the second parameter.
	 * @return the result of execution.
	 */
	T execute(A a, B b);
}