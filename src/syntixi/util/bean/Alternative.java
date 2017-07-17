package syntixi.util.bean;

/**
 * <code>Alternative</code> class encapsulates the information corresponding to the
 * alternative described in the <code>XML</code> requirement.
 * <p>
 * The alternatives accepted in the fusion process are the following:
 * <code>OCC, OCWC, GCC</code>, and <code>GCWC</code>.
 * <p>
 * Each alternative is related to the way to build new components by the generator.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Alternative {

    /**
     * <code>Alternative</code> selected to perform the fusion.
     */
    private String alternative;

    /**
     * Sets the name of the alternative to using to perform the fusion.
     *
     * @param alternative the name of the alternative.
     */
    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    /**
     * Returns the name of the alternative.
     *
     * @return the name of the alternative.
     */
    public String getAlternative() {
        return alternative;
    }
}