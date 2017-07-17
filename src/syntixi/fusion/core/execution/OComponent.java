package syntixi.fusion.core.execution;

import javassist.CtClass;

import java.util.Vector;

/**
 * <code>OComponent</code> represents a factory to build components by modifying
 * existing elements.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public abstract class OComponent {

    /**
     * Builds new components for each <code>CtClass</code> provided.
     *
     * @param classes the classes to implement.
     */
    public abstract void build(Vector<CtClass> classes);
}