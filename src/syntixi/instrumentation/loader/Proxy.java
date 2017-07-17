package syntixi.instrumentation.loader;

import javassist.CtClass;

/**
 * <code>Proxy</code> class establishes a starting point to load a <code>CtClass</code> file
 *  dynamically in the <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see ComponentLoader
 */
public class Proxy implements Invoker {

    /**
     * The ComponentLoader instance.
     */
    ComponentLoader componentLoader;

    @Override
    public Class load(ComponentLoader componentLoader, CtClass ctClass) {
        if(this.componentLoader == null)
            this.componentLoader = new ComponentLoader();

        return this.componentLoader.load(this.componentLoader, ctClass);
    }
}