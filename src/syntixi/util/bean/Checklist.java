package syntixi.util.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>Checklist</code> class allows to update the status of each functionality
 * requested by the user in order to know which of them are successfully accomplished
 * through the fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Functionality
 */
public class Checklist {

    /**
     * The map where the functionalities are stored.
     */
    private Map<Functionality, Boolean> checklist = new HashMap<>();

    /**
     * Initializes the map with all the functionalities requested by the user via
     * an <code>XML</code> requirements file.
     * <p>
     * Each functionality has an initial value of false to indicate that its solution
     * is pending.
     *
     * @param requirement the <code>Requirement</code> instance that contains the
     *                    functionalities to solve.
     */
    public Checklist(Requirement requirement) {
        requirement.getFunctionalities().forEach(functionality -> setChecklist(functionality, false));
    }

    /**
     * Updates the status corresponding to a specific functionality.
     *
     * @param functionality the <code>Functionality</code> instance to update.
     * @param status the status of the functionality. <code>true</code> if the
     *               functionality is solved; <code>false</code> otherwise.
     */
    public void setChecklist(Functionality functionality, Boolean status) {
        checklist.put(functionality, status);
    }

    /**
     * Returns the checklist of requirements.
     *
     * @return the checklist of requirements.
     */
    public Map<Functionality, Boolean> getChecklist() {
        return checklist;
    }

    /**
     * Filtrates the functionalities according to a boolean value in order to know which
     * of them are solved or not solved.
     *
     * @param filter the boolean variable or value that indicates the filtering type.
     *               <code>true</code> for obtaining solved functionalities;
     *               <code>false</code> otherwise.
     * @return the map with solved or unsolved functionalities.
     */
    public Map<Functionality, Boolean> filterBy(boolean filter) {
        return checklist.entrySet().stream().filter((map -> map.getValue() == filter)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }

    /**
     * Prints the map of functionalities with its corresponding status.
     */
    public void printChecklist() {
        checklist.forEach((key, value) -> System.out.println(key.getKeywords()[0] + "\t" + value));
    }
}