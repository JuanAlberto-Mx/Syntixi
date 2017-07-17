package syntixi.util.func;


/**
 * Represents a function that accept no parameter and return a value.
 *
 * @param <T> the return value.
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface F0<T> {

    /**
     * Evaluates or execute the function.
     *
     * @return the result of execution.
     */
    T execute();
}