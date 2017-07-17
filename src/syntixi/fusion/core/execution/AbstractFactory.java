package syntixi.fusion.core.execution;

/**
 * <code>AbstractFactory</code> provides a couple of methods to fuse components
 * dynamically.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public abstract class AbstractFactory {

    /**
     * Builds a new component by using components running in the <code>JVM</code>.
     * Such components can be modified dynamically by adding or removing features
     * to fulfill a specific requirement.
     *
     * @param alternative the alternative to fuse the components, which can be
     *                    <code>OCC</code> to use original components and connectors,
     *                    and <code>OCWC</code> to use original components without
     *                    connectors.
     * @return the new fused component.
     */
    public abstract OComponent buildOComponent(String alternative);

    /**
     * Builds a new component from the components running in the <code>JVM</code>.
     * Such components are considered as <i>Thinner Components</i> due to they only
     * have the necessary elements to fulfill a specific requirement.
     *
     * @param alternative the alternative to fuse the components, which can be
     *                    <code>GCC</code> to generate both components and connectors
     *                    and <code>GCWC</code> to generate components without using
     *                    connectors.
     * @return the new fused component.
     */
    public abstract GComponent buildGComponent(String alternative);
}