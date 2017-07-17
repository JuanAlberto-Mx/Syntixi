package syntixi.fusion.core.monitoring;

/**
 * <code>Observer</code> interface provides an update method to each observer class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface Observer {

    /**
     * The method update is intended to communicate to each observer class, the changes
     * performed in the state of a specific property related to an observed class.
     */
    void update();
}