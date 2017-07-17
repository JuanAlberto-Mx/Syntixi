package syntixi.fusion;

import javassist.CtClass;
import syntixi.util.settings.Repository;
import syntixi.instrumentation.loader.Proxy;
import syntixi.util.file.FileCommand;
import syntixi.util.file.FileManager;
import syntixi.util.misc.ReflectUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <code>Actuator</code> class encapsulates the functionality of a physical device
 * which generates an effect given an automated process. In this sense, the main
 * goal of <code>Actuator</code> class is providing to the user the resulting components
 * of the fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class Actuator {

    /**
     * Field to initialize the fused component.
     */
    private static CtClass ctClass;

    /**
     * Private constructor for avoiding class instantiation.
     */
    private Actuator() {
    }

    /**
     * Sets the resulting component of the fusion process.
     *
     * @param ctClass the fused component.
     */
    public static void setCtClass(CtClass ctClass) {
        Actuator.ctClass = ctClass;
    }

    /**
     * Gets the resulting component of the fusion process.
     *
     * @return the fused component.
     */
    public static CtClass getCtClass() {
        return ctClass;
    }

    /**
     * Starts the dynamic class loading.
     */
    public static void start() {
        CtClass ctClass = getCtClass();

        moveFiles(ctClass);

            /*Class loadedClass = new Proxy().load(null, ctClass);

            Method method = loadedClass.getDeclaredMethod("main", String[].class);

            ReflectUtil.invoke(method, loadedClass, (Object) new String[]{null});*/
    }

    /**
     * Moves a fusionable component to the generator's local repository.
     *
     * @param ctClass the fusionable component to move to the generator's local
     *                repository.
     */
    private static void moveFiles(CtClass ctClass) {
        try {
            File root = new File(".");

            File sourcePath = new File(root.getCanonicalPath() + File.separator + ctClass.getName().substring(0, 2));
            File targetPath = new File(Repository.getRepository());

            FileManager fileManager = new FileManager(sourcePath, targetPath);
            FileCommand fileCommand = new FileCommand(fileManager);

            new syntixi.util.file.Invoker().start(fileCommand);
        }
        catch (IOException e) {
        }
    }
}