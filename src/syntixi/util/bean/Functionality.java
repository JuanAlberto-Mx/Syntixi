package syntixi.util.bean;

/**
 * <code>Functionality</code> class provides a set of methods to create new instances
 * with information related to the functionality of the final component.
 * <p>
 * The functionality consists of a set of keywords that allow the generator to find
 * compatible elements, a set of input data type and an output data type.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Functionality {

    /**
     * Set of keywords to describe the kind of operation that the component performs.
     */
    private String[] keywords;

    /**
     * Set of input data types to describe the type of information that the component
     * receives.
     */
    private String[] input;

    /**
     * Data type that returns the component.
     */
    private String output;

    /**
     * Sets the keywords to describe the kind of operation that the component performs.
     *
     * @param keywords the keywords to describe the kind of operation to perform.
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    /**
     * Sets the input data types to describe the type of information that the component
     * receives.
     *
     * @param input the input data types.
     */
    public void setInput(String[] input) {
        this.input = input;
    }

    /**
     * Sets the data type that the component returns.
     *
     * @param output the resulting data type.
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     *  Returns an array that contains the keywords that describe  the kind of operation
     *  that the component performs.
     *
     * @return an array with the keywords.
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Returns an array that contains the input data types received by the component.
     *
     * @return an array with the input data types.
     */
    public String[] getInput() {
        return input;
    }

    /**
     * Returns the output data type.
     *
     * @return the output data type.
     */
    public String getOutput() {
        return output;
    }
}