package syntixi.fusion;

import syntixi.fusion.core.planning.Planner;

/**
 * <code>PlanningFSM</code> class represents the third stage in the <code>MAPE-K</code>
 * loop-based Finite-State Machine.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class PlanningFSM implements State {

    /**
     * Private instance for accessing to the <code>Planner</code> class functionalities.
     */
    private Planner planner;

    /**
     * Constructor for initializing the <code>Planner</code> class features.
     */
    public PlanningFSM() {
        planner = new Planner();
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
        planner.init();

        Sensor.setSTATE(2);
    }
}