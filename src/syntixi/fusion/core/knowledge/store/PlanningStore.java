package syntixi.fusion.core.knowledge.store;

import javassist.CtClass;
import syntixi.util.bean.Requirement;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Scenario;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * <code>PlanningStore</code> class stores all the information related to the
 * fusion scenarios and strategies performed to fuse components dynamically.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class PlanningStore {

    /**
     * The <code>Map</code> of components per requirements.
     */
    private Map<Requirement, Map<Class, Integer>> componentsPerRequirement = new HashMap<>();

    /**
     * The <code>Map</code> of provisions per components.
     */
    private Map<Requirement, Map<Class, Provision>> provisionPerComponent = new HashMap<>();

    /**
     * The <code>Map</code> of fusion scenarios.
     */
    private Map<Requirement, Scenario> fusionScenario = new HashMap<>();

    /**
     * The <code>Map</code> of ready components.
     */
    private Map<Requirement, Vector<CtClass>> readyComponents = new HashMap<>();

    /**
     * The <code>PlanningStore</code> instance.
     */
    private static PlanningStore planningStore;

    /**
     * Private constructor to restrict new class instantiation.
     */
    private PlanningStore() {
    }

    /**
     * Sets the components used to fulfill a specific requirement.
     *
     * @param requirement the source requirement.
     * @param cls the instance containing a pair of <code>(Class, Integer)</code>.
     */
    public void setComponentsPerRequirement(Requirement requirement, Map<Class, Integer> cls) {
        componentsPerRequirement.put(requirement, cls);
    }

    /**
     * Sets the provision for a specific component.
     *
     * @param requirement the source requirement.
     * @param cls the instance containing a pair of <code>(Class, Provision)</code>.
     */
    public void setProvisionPerComponent(Requirement requirement, Map<Class, Provision> cls) {
        provisionPerComponent.put(requirement, cls);
    }

    /**
     * Sets the fusion scenario for a specific requirement.
     *
     * @param requirement the source requirement.
     * @param scenario the scenario found.
     */
    public void setFusionScenario(Requirement requirement, Scenario scenario) {
        fusionScenario.put(requirement, scenario);
    }

    /**
     * Sets the components ready to implement.
     *
     * @param requirement the source requirement.
     * @param ctClasses the components involved in the requirement fulfillment.
     */
    public void setReadyComponents(Requirement requirement, Vector<CtClass> ctClasses) {
        readyComponents.put(requirement, ctClasses);
    }

    /**
     * Gets the components used by a specific requirement.
     *
     * @return the components used by a particular requirement.
     */
    public Map<Requirement, Map<Class, Integer>> getComponentsPerRequirement() {
        return componentsPerRequirement;
    }

    /**
     * Gets the kind of provision for a specific component.
     *
     * @return the provision of the component.
     */
    public Map<Requirement, Map<Class, Provision>> getProvisionPerComponent() {
        return provisionPerComponent;
    }

    /**
     * Gets the fusion scenario for a specific requirement.
     *
     * @return the fusion scenario.
     */
    public Map<Requirement, Scenario> getFusionScenario() {
        return fusionScenario;
    }

    /**
     * Gets the ready components produced by planning strategies.
     *
     * @return the components ready to implement.
     */
    public Map<Requirement, Vector<CtClass>> getReadyComponents() {
        return readyComponents;
    }

    /**
     * Removes the components related to a specific requirement.
     *
     * @param requirement the current requirement.
     */
    public void deleteComponentsPerRequirement(Requirement requirement) {
        componentsPerRequirement.remove(requirement);
    }

    /**
     * Removes the provision and the components related to a specific requirement.
     *
     * @param requirement the requirement to remove.
     */
    public void deleteProvisionPerComponent(Requirement requirement) {
        provisionPerComponent.remove(requirement);
    }

    /**
     * Removes the fusion scenario for a specific requirement.
     *
     * @param requirement the requirement tha contains the scenario to remove.
     */
    public void deleteFusionScenario(Requirement requirement) {
        fusionScenario.remove(requirement);
    }

    /**
     * Removes the components related to a specific requirement.
     *
     * @param requirement the requirement associated to the components.
     */
    public void deleteReadyComponents(Requirement requirement) {
        readyComponents.remove(requirement);
    }

    /**
     * Returns a <code>PlanningStore</code> instance.
     *
     * @return the <code>PlanningStore</code> instance.
     */
    public static PlanningStore getPlanningStore() {
        if(planningStore == null)
            planningStore = new PlanningStore();

        return planningStore;
    }

    /**
     * Prints the used components per requirements.
     */
    public void printComponentsPerRequirement() {
        componentsPerRequirement.forEach((requirement, classIntegerMap) -> {
            System.out.println("\tRequirement:\t" + requirement.getDescription().getName());

            classIntegerMap.forEach((cls, counter) -> {
                if(cls != null)
                    System.out.println("\t\tComponent:\t" + cls.getName() + "\tCounter:\t" + counter);
                else
                    System.out.println("\t\tComponent:\t" + null + "\tCounter:\t" + counter);
            });
        });
    }

    /**
     * Prints the provisions per component.
     */
    public void printProvisionPerComponent() {
        provisionPerComponent.forEach(((requirement, classProvisionMap) -> {
            System.out.println("\tRequirement:\t" + requirement);

            classProvisionMap.forEach((cls, provision) -> System.out.println("\t\tClass:\t" + cls + "\tProvision:\t" + provision));
        }));
    }

    /**
     * Prints the fusion scenarios for each requirement.
     */
    public void printFusionScenario() {
        fusionScenario.forEach(((requirement, scenario) -> System.out.println("\tRequirement:\t" + requirement.getDescription().getName() + "\tScenario:\t" + scenario)));
    }
}