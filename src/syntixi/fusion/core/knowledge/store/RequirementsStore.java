package syntixi.fusion.core.knowledge.store;

import syntixi.util.bean.Requirement;

import java.util.Vector;

/**
 * <code>RequirementsStore</code> class stores all the information related to
 * <code>Requirement</code> class instances in order to have an easier way to
 * access to the <code>XML</code> requirement information.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Requirement
 */
public final class RequirementsStore {

    /**
     * List of <code>Requirement</code> class instances.
     */
    private Vector<Requirement> requirements = new Vector<>();

    /**
     * Instance to access to the <code>RequirementsStore</code> class.
     */
    private static RequirementsStore requirementsStore;

    /**
     * Private constructor to limit outer instantiations.
     */
    private RequirementsStore() {
    }

    /**
     * Stores a <code>Requirement</code> class instance to the list.
     *
     * @param requirement the <code>Requirement</code> class instance.
     */
    public void setRequirements(Requirement requirement) {
        requirements.add(requirement);
    }

    /**
     * Returns a list of <code>Requirement</code> class instances.
     *
     * @return the list of <code>Requirement</code> class instances.
     */
    public Vector<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * Removes a specific requirement from the requirement list.
     *
     * @param requirement the requirement to remove.
     */
    public void deleteRequirement(Requirement requirement) {
        requirements.remove(requirement);
    }

    /**
     * Returns a unique <code>RequirementsStore</code> class instance.
     *
     * @return the <code>RequirementsStore</code> class instance.
     */
    public static RequirementsStore getRequirementsStore() {
        if(requirementsStore == null)
            requirementsStore = new RequirementsStore();

        return requirementsStore;
    }

    /**
     * Prints the information contained in the list of requirements.
     */
    public void printRequirements() {
        requirements.forEach(requirement -> {
            System.out.println("Description:\t" + requirement.getDescription());
            System.out.println("\tName:\t" + requirement.getDescription().getName());
            System.out.println("\tGoal:\t" + requirement.getDescription().getGoal());

            requirement.getFunctionalities().forEach(functionality -> System.out.println("Functionality:\t" + functionality.getKeywords()));

            System.out.println("Alternative\t" + requirement.getAlternative().getAlternative());
        });
    }
}