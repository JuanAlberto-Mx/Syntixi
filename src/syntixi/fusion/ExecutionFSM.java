package syntixi.fusion;

import syntixi.fusion.core.execution.Executor;

/**
 * <code>ExecutionFSM</code> class represents the fourth stage in the <code>MAPE-K</code>
 * loop-based Finite-State Machine.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ExecutionFSM implements State {

    /**
     * Private instance for accessing to the <code>Executor</code> class functionalities.
     */
    private Executor executor;

    /**
     * Constructor for initializing the {@code Executor} class features.
     */
    public ExecutionFSM() {
        executor = new Executor();
    }

    @Override
    public void next() {
        Sensor.setSTATE(1);
    }

    @Override
    public void back() {
        Sensor.setSTATE(0);
    }

    @Override
    public void working() {
        executor.init();

        Sensor.setSTATE(2);
    }
}