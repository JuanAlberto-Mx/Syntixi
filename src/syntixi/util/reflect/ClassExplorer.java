package syntixi.util.reflect;

import java.util.Vector;

/**
 * <code>ClassExplorer</code> provides a set of methods that allow the generator to get
 * information of classes. Among the information, it is possible to obtain class files
 * and lists of parents for a specific class, the number of parents of a specific class,
 * and to know which class has a higher hierarchy.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ClassExplorer {

    /**
     * The list of parents of a specific class.
     */
    private static Vector<Class> parents = new Vector<>();

    /**
     * Returns a class according to a specific path.
     *
     * @param path the full class path.
     * @return the class for the specified path.
     */
    public static Class getClass(String path) {
        Class cls = null;

        try {
            cls = Class.forName(path);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cls;
    }

    /**
     * Returns a list of the parent classes of a specific class.
     *
     * @param cls the class to analyze.
     * @return the list of parents of a specific class.
     */
    public static Vector<Class> getParents(Class cls) {
        if(cls != null) {
            parents.add(cls.getSuperclass());
            getParents(cls.getSuperclass());
        }
        else
            parents.remove(parents.lastElement());

        return parents;
    }

    /**
     * Returns the number of parents of a specific class.
     *
     * @param cls the class to analyse.
     * @return the number of parents of a specific class.
     */
    public static int getNumberOfParents(Class cls) {
        if(cls == null)
            return -1;
        else
            return 1 + getNumberOfParents(cls.getSuperclass());
    }

    /**
     * Returns the class with more parents between several classes.
     *
     * @param classes the list of classes to analyze.
     * @return the class with more parents.
     */
    public static Class getMoreParents(Vector<Class> classes) {
        int parentsCounter = 0;
        Class greatest = null;

        for(Class cls : classes) {
            int number = getNumberOfParents(cls);

            if(number >= parentsCounter) {
                greatest = cls;
                parentsCounter = number;
            }
        }

        return greatest;
    }
}