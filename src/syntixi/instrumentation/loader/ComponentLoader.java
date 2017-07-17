package syntixi.instrumentation.loader;

import javassist.CtClass;
import syntixi.util.settings.Repository;

/**
 * <code>ComponentLoader</code> class allows initializing the name and the
 * <code>URL</code> of the class to be loaded by the dynamic loader.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Invoker
 * @see DynamicClassLoader
 */
public class ComponentLoader implements Invoker {

    /**
     * The class <code>URL</code>.
     */
    private String classURL;

    /**
     * The class name.
     */
    private String className;

    /**
     * Sets the class <code>URL</code>.
     *
     * @param classURL the class <code>URL</code>.
     */
    public void setClassURL(String classURL) {
        this.classURL = classURL;
    }

    /**
     * Sets the class name.
     *
     * @param className the class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public Class load(ComponentLoader clsLoader, CtClass ctClass) {
        clsLoader.setClassURL(Repository.getRepository());
        clsLoader.setClassName(ctClass.getName());

        return new DynamicClassLoader(classURL).load(className);
    }
}