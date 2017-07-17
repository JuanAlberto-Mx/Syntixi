package syntixi.fusion.core.monitoring;

import syntixi.fusion.Mapek;
import syntixi.fusion.core.knowledge.store.MonitoringStore;

/**
 * <code>Monitor</code> class represents the first stage in the <code>MAPE-K</code> loop
 * and provides a getaway to perform operations for detecting components running in the
 * <code>Java Virtual Machine</code> as well as new user requirements specified through
 *  <code>XML</code> files.
 * <p>
 * All the information collected by <code>Monitor</code> is stored and make it available
 * through several lists of <code>MonitoringStore</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see MonitoringStore
 */
public class Monitor implements Mapek {

    private Requirement requirement;
    private Component component;

    @Override
    public void init() {
        requirement = new Requirement();
        RequirementObserver requirementObserver = new RequirementObserver(requirement);

        component = new Component();
        ComponentObserver componentObserver = new ComponentObserver(component);
    }

    /**
     * Searches for components running in the <code>Java Virtual Machine</code>.
     * <p>
     * All the components detected are stored in a list of classes that in turn is
     * available for future operations and stages through the fusion process.
     *
     * @see MonitoringStore
     */
    public void searchComponents() {
        try {
            component.findComponents();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for new requirements in a local repository.
     * <p>
     * Each requirement is specified via a <code>XML</code> file and its address is stored
     * in a list of files that is available through all stages of the fusion process.
     *
     * @see MonitoringStore
     */
    public void searchRequirements() {
        try{
            requirement.findRequirements();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}