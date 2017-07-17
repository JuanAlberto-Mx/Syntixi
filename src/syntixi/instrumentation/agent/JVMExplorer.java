package syntixi.instrumentation.agent;

import syntixi.instrumentation.store.JVMStore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <code>JVMExplorer</code> class explores the <code>Java Virtual Machine</code> to get
 * information of loaded classes.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class JVMExplorer {

    /**
     * Gets the loaded classes for a specific class loader.
     *
     * @param classLoader the class loader.
     * @return the list of names of loaded <code>Java</code> classes.
     */
    public List<String> getLoadedClasses(final ClassLoader classLoader) {
        List<String> classNames = null;

        try {
            Field field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);

            List<Class> classes = new ArrayList<>((Vector<Class>) field.get(classLoader));

            classNames = new ArrayList<>(classes.size());

            for(Class cls : classes)
                classNames.add(cls.getCanonicalName());

            return classNames;
        }
        catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(JVMExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return classNames;
    }

    /**
     * Searches for compatible methods given a specific method.
     *
     * @param userRequirement the name of the method to search for.
     * @return the compatible method found.
     */
    public Method searchCompatibleMethod(String userRequirement) {
        Method compatibleMethod = null;

        for(Class cls : JVMStore.getAllLoadedClasses()) {
            for(Method method : cls.getDeclaredMethods()) {
                if(method.getName().contains(userRequirement)) {
                    compatibleMethod = method;
                    break;
                }
            }
        }

        return compatibleMethod;
    }
}