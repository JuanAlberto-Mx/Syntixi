package syntixi.util.bean;

import java.util.Vector;

/**
 * <code>Requirement</code> class provides a set of methods to initialize an object with
 * values corresponding to an <code>XML</code> user requirement.
 * <p>
 * Each <code>XML</code> user requirement consists of three parts: a description composed
 * by a name and a goal, a set of functionalities, each one specified by keywords,
 * input data, and output data, and finally, an alternative to proceed with the fusion
 * of components.
 * <p>
 * The information is encapsulated and structured in an equivalent way of the <code>XML</code>
 * user requirement to keep its consistency and accessibility.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Description
 * @see Functionality
 * @see Alternative
 */
public class Requirement {

    /**
     * Instance to set the values of the <code>XML</code> requirement description.
     */
    private Description description;

    /**
     * List of functionalities described in the <code>XML</code> requirement.
     */
    private Vector<Functionality> functionalities = new Vector<>();

    /**
     * Instance to set the values of the <code>XML</code> requirement alternative.
     */
    private Alternative alternative;

    /**
     * Initialize the values corresponding to the <code>XML</code> requirement description.
     *
     * @param description the instance corresponding to the <code>XML</code> requirement
     *                   description.
     * @see Description
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Initialize the values corresponding to the <code>XML</code> requirement functionalities.
     *
     * @param functionality the instance corresponding to the functionalities described
     *                     in the <code>XML</code> requirement.
     * @see Functionality
     */
    public void setFunctionalities(Functionality functionality) {
        this.functionalities.add(functionality);
    }

    /**
     * Initialize the value corresponding to the alternative described in the
     * <code>XML</code> requirement.
     *
     * @param alternative the instance corresponding to the alternative described in the
     *                    <code>XML</code> requirement.
     * @see Alternative
     */
    public void setAlternative(Alternative alternative) {
        this.alternative = alternative;
    }

    /**
     * Returns a <code>Description</code> class object with the information of the
     * <code>XML</code> requirement description.
     *
     * @return the <code>Description</code> class object.
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Returns a list of functionalities corresponding to the functionalities described
     * in the <code>XML</code> requirement.
     *
     * @return the list of functionalities.
     */
    public Vector<Functionality> getFunctionalities() {
        return functionalities;
    }

    /**
     * Returns an <code>Alternative</code> class object with the information of the
     * alternative described in the <code>XML</code> requirement.
     *
     * @return the <code>Alternative</code> class object.
     */
    public Alternative getAlternative() {
        return alternative;
    }
}