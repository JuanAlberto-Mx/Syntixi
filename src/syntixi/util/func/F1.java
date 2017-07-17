package syntixi.util.func;

/**
 * Represents a function that accept one parameter and return a value.
 *
 * @param <A> the only parameter
 * @param <T> the return value
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface F1<A, T> {

    /**
     * Evaluates or execute the function.
     *
     * @param obj the parameter.
     * @return the result of execution.
     */
    T execute(A obj);
}