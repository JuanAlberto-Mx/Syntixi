package syntixi.util.reflect;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * <code>MethodExplorer</code> class provides a set of methods that allow the generator
 * to obtain information about methods contained in a specific class. Such operations
 * correspond to know which methods are compatible with a specific functionality
 * described in an <code>XML</code> user requirement, to know the number of methods
 * contained in a particular class, and make compatible a set of provided methods.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class MethodExplorer {

    /**
     * Map to store compatible methods.
     */
    private Map<Method, Method> compatibleMethods = new LinkedHashMap<Method, Method>();

    /**
     * Stores a pair of compatible methods.
     *
     * @param methodA the first compatible method.
     * @param methodB the second compatible method.
     */
    public void setCompatibleMethods(Method methodA, Method methodB) {
        compatibleMethods.put(methodA, methodB);
    }

    /**
     * Returns a map with a set of pairs of compatible methods.
     *
     * @return the map of compatible methods.
     */
    public Map<Method, Method> getCompatibleMethods() {
        return compatibleMethods;
    }

    /**
     * Returns the number of methods contained in a specific class.
     *
     * @param cls the class to inspect.
     * @return the number of methods contained in the provided class.
     */
    public int getNumberOfMethods(Class cls) {
        return cls.getDeclaredMethods().length;
    }

    /**
     * Obtains the compatible methods given a set of methods.
     *
     * @param methods the list of method arrays belonging to different classes.
     */
    public void getCompatibleMethods(Vector<Method[]> methods) {
        for(int i = 0, j = 1; i < methods.size() && j < methods.size(); i++, j++) {
            Method[] currentMethod = methods.elementAt(i);
            Method[] otherMethod = methods.elementAt(j);

            for(Method methodA : currentMethod) {
                Class paramMethodA[] = methodA.getParameterTypes();

                for(Method methodB : otherMethod) {
                    Class paramMethodB[] = methodB.getParameterTypes();

                    try {
                        if(isCompatible(paramMethodA, paramMethodB))
                            setCompatibleMethods(methodA, methodB);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Verifies the compatibility between two arrays of classes corresponding to the
     * parameter data types of two methods.
     *
     * @param paramMethodA the array of data types corresponding to the first method.
     * @param paramMethodB the array of data types corresponding to the second method.
     * @return <code>true</code> if the parameters of both methods are compatible;
     *          <code>false</code> otherwise.
     * @throws Exception
     */
    public boolean isCompatible(Class[] paramMethodA, Class[] paramMethodB) throws Exception {
        boolean flag = false;

        if(paramMethodA.length != paramMethodB.length)
            flag = false;
        else if(paramMethodA.length == 0 && paramMethodB.length == 0)
            flag = true;
        else {
            for (int i = 0; i < paramMethodA.length; i++) {
                Class pma = paramMethodA[i];
                Class pmb = paramMethodB[i];

                if(isCompatible(pma, pmb))
                    flag = true;
                else {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * Verifies the compatibility between wrapper data types and primitive data types.
     *
     * @param pma the class of the first parameter.
     * @param pmb the class of the second parameter.
     * @return <code>true</code> if the parameters are compatible; <code>false</code>
     *          otherwise.
     * @throws Exception
     */
    private boolean isCompatible(Class pma, Class pmb) throws Exception {
        boolean flag = false;
        final Vector<Class> wrappers = new Vector<>();

        wrappers.add(Boolean.class);
        wrappers.add(Byte.class);
        wrappers.add(Character.class);
        wrappers.add(Short.class);
        wrappers.add(Long.class);
        wrappers.add(Integer.class);
        wrappers.add(Float.class);
        wrappers.add(Double.class);

        try {
            if(pma.getName().equals(pmb.getName()))
                flag = true;
            else if(wrappers.contains(pma)) {
                if(pma.getDeclaredField("TYPE").get(null).equals(pmb))
                    flag = true;
            }
            else if(wrappers.contains(pmb)) {
                if(pmb.getDeclaredField("TYPE").get(null).equals(pma))
                    flag = true;
            }
            else if(pma.isAssignableFrom(pmb) || pmb.isAssignableFrom(pma))
                flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * Obtains the methods compatible with the methods of a specific set of classes.
     *
     * @param candidateClasses the list of classes to inspect.
     * @param candidateMethods the list of methods to verify.
     * @return the list of compatible methods.
     */
    private Vector<Method[]> toCompatibleMethods(Vector<Class> candidateClasses, Vector<Method> candidateMethods) {
        Vector<Method[]> vector = new Vector<>();
        int size = candidateClasses.size();

        for(int i = 0; i < size; i++) {
            int index = 0;
            Method[] methods = null;

            for(Method method : candidateMethods) {
                if(method.getDeclaringClass().equals(candidateClasses.elementAt(i)))
                    index++;
            }

            methods = new Method[index];

            int aux = 0;

            for(Method method : candidateMethods) {
                if(method.getDeclaringClass().equals(candidateClasses.elementAt(i))) {
                    methods[aux] = method;
                    aux++;
                }
            }

            vector.add(methods);
        }

        return vector;
    }

    /**
     * Prints the map of compatible methods.
     */
    public void printCompatibleMethods() {
        compatibleMethods.forEach((m1, m2) -> System.out.println(m1.getName() + "\t" + m2.getName()));
    }
}