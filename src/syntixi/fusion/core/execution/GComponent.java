package syntixi.fusion.core.execution;

import javassist.CtClass;

import java.util.Vector;

/**
 * <code>GComponent</code> represents a factory to build components by generating
 * new elements.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public abstract class GComponent {

    /**
     * Builds new components for each <code>CtClass</code> provided.
     *
     * @param classes the classes to implement.
     */
    public abstract void build(Vector<CtClass> classes);
}