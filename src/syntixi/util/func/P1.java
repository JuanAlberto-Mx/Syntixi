package syntixi.util.func;

/**
 * Function that accept one object and return nothing.
 *
 * @param <A> the unique object.
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface P1<A> {

	/**
	 * Evaluates the function.
	 *
	 * @param obj the object.
	 */
	void execute(A obj);
}