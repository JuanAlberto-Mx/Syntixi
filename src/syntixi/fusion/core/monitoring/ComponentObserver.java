package syntixi.fusion.core.monitoring;

import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>ComponentObserver</code> class is responsible for monitoring the
 * <code>Component</code> class. It implements the update method to notify to all the
 * observers in case of new components detected in the <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Observer
 */
public class ComponentObserver implements Observer {

    /**
     * Represents the component to be monitored.
     */
    private Environment component;

    /**
     * Constructor for initializing the component to be monitored.
     *
     * @param component the instance corresponding to the monitored component.
     * @see Environment
     */
    public ComponentObserver(Environment component) {
        this.component = component;
        this.component.attach(this);
    }

    @Override
    public void update() {
        getMonitoringStore().setComponents(((Component) component).getVirtualMachineDescriptor());
    }
}