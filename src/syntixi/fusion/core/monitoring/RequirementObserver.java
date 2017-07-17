package syntixi.fusion.core.monitoring;

import syntixi.fusion.Sensor;

import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>RequirementObserver</code> class is responsible for monitoring the
 * <code>Requirement</code> class. It implements the <code>update</code> method to
 * notify to all the observers in case of new <code>XML</code> user requirements detected.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Observer
 */
public class RequirementObserver implements Observer {

    /**
     * Represents the class to be monitored.
     */
    private Environment requirement;

    /**
     * Constructor for initializing the class to be monitored.
     *
     * @param requirement the instance corresponding to the monitored class.
     */
    public RequirementObserver(Environment requirement) {
        this.requirement = requirement;
        this.requirement.attach(this);
    }

    @Override
    public void update() {
        getMonitoringStore().setRequirements(((Requirement) requirement).getRequirement());
        Sensor.setSTATE(2);
    }
}