package syntixi.instrumentation.loader;

import javassist.CtClass;

/**
 * <code>Invoker</code> interface provides a method for <code>CtClass</code> loading.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface Invoker {

    /**
     * Loads a specific <code>CtClass</code> file in the <code>Java Virtual Machine</code>.
     *
     * @param clsLoader the class loader to be used.
     * @param ctClass the <code>CtClass</code> file.
     * @return
     */
    Class load(ComponentLoader clsLoader, CtClass ctClass);
}