package syntixi.fusion.core.knowledge.store;

import syntixi.util.bean.Requirement;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>StatusStore</code> class provides the means to update the status corresponding
 * to the result of the basic analysis performed by the analysis mechanism.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class StatusStore {

    /**
     * Map to set the final result of each requirement.
     */
    private Map<Requirement, Boolean> requirementStatus = new HashMap<>();

    /**
     * The instance of the class.
     */
    private static StatusStore statusStore;

    /**
     * Private constructor to limit outer instantiations.
     */
    private StatusStore() {
    }

    /**
     * Sets the status for each requirement treated in the fusion process.
     *
     * @param requirement the current requirement.
     * @param status the resulting status. <code>true</code> if the requirement
     *               is successfully completed; <code>false</code> otherwise.
     */
    public void setRequirementStatus(Requirement requirement, boolean status) {
        this.requirementStatus.put(requirement, status);
    }

    /**
     * Gets the resulting status for a specific requirement.
     *
     * @return the status of a requirement requested.
     */
    public Boolean getRequirementStatus(Requirement requirement) {
        return requirementStatus.get(requirement);
    }

    /**
     * Gets all status
     *
     * @return
     */
    public Map<Requirement, Boolean> getRequirementStatus() {
        return requirementStatus;
    }

    /**
     * Removes a specific requirement from the requirement status list.
     *
     * @param requirement the requirement to remove.
     */
    public void deleteRequirementStatus(Requirement requirement) {
        requirementStatus.remove(requirement);
    }

    /**
     * Returns a unique instance of the <code>StatusStore</code> class.
     *
     * @return the instance of the <code>StatusStore</code> class.
     */
    public static StatusStore getStatusStore() {
        if(statusStore == null)
            statusStore = new StatusStore();

        return statusStore;
    }
}