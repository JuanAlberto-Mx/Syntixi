package syntixi.util.bean;

/**
 * <code>Description</code> class provides a set of methods to initialize the description
 * of a component required by the user.
 * <p>
 * A <code>Description</code> object encapsulates the information corresponding to the
 * name and the goal of a final component.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Description {

    /**
     * Instance corresponding to the name of the final component.
     */
    private String name;

    /**
     * Instance corresponding to the goal of the final component.
     */
    private String goal;

    /**
     * Sets the name of the final component according to the <code>XML</code> requirement.
     *
     * @param name the name of the final component.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the goal of the final component according to the <code>XML</code> requirement.
     *
     * @param goal the goal of the final component.
     */
    public void setGoal(String goal) {
        this.goal = goal;
    }

    /**
     * Returns the name of the final component.
     *
     * @return the name of the final component.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the goal of the final component.
     *
     * @return the goal of the final component.
     */
    public String getGoal() {
        return goal;
    }
}