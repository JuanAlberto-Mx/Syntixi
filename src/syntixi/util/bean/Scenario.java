package syntixi.util.bean;

/**
 * <code>Scenario</code>} class provides a set of options corresponding to each fusion
 * in which a component can be.
 * <p>
 * The scenario is based on the number of elements that each involved component
 * provides during the fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public enum Scenario {
    ALL_COMPLETE,
    ALL_PARTIAL,
    COMPLETE_AND_PARTIAL,
    COMPLETE_PARTIAL_NONE,
    PARTIAL_AND_NONE,
    COMPLETE_AND_NONE,
    NONE
}