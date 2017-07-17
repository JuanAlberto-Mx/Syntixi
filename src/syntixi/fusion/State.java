package syntixi.fusion;

/**
 * <code>State</code> interface provides a set of methods for representing the different
 * states where a specific task can be.
 * <p>
 * The main goal of <code>State</code> interface is to complement the Finite-State Machines
 * functionality.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface State {

    /**
     * Goes to the next state in a specific Finite-State Machine according to the
     * current point in which the executed task is.
     */
    void next();

    /**
     * Goes to the previous state in a specific Finite-State Machine according to the
     * current point in which the executed task is.
     */
    void back();

    /**
     * Keeps executing a specific task in the same state in a specific Finite-State Machine.
     */
    void working();
}