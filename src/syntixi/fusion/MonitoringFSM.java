package syntixi.fusion;

import syntixi.fusion.core.monitoring.Monitor;

/**
 * <code>MonitoringFSM</code> class represents the first stage in the <code>MAPE-K</code>
 * loop-based Finite-State Machine.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class MonitoringFSM implements State {

    /**
     * Private instance for accessing to the <code>Monitor</code> class functionalities.
     */
    private Monitor monitor;

    /**
     * Constructor for initializing the <code>Monitor</code> class features.
     */
    public MonitoringFSM() {
        monitor = new Monitor();
        monitor.init();
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
        monitor.searchRequirements();
        monitor.searchComponents();
    }
}