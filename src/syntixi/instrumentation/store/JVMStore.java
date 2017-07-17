package syntixi.instrumentation.store;

/**
 * <code>JVMStore</code> class stores all the information related to classes loaded in
 * the <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class JVMStore {

    /**
     * The array to store all loaded classes.
     */
    private static Class[] allLoadedClasses;

    /**
     * The array to store the system loader classes.
     */
    private static Class[] systemLoaderClasses;

    /**
     * The array to store the class loader classes.
     */
    private static Class[] classLoaderClasses;

    /**
     * Sets all loaded classes in the <code>Java Virtual Machine</code>.
     *
     * @param allLoadedClasses the array of loaded classes.
     */
    public static void setAllLoadedClasses(Class[] allLoadedClasses) {
        JVMStore.allLoadedClasses = allLoadedClasses;
    }

    /**
     * Sets the system loader classes.
     *
     * @param systemLoaderClasses the array of classes.
     */
    public static void setSystemClassLoaderInitiatedClasses(Class[] systemLoaderClasses) {
        JVMStore.systemLoaderClasses = systemLoaderClasses;
    }

    /**
     * Sets the class loader initiated classes.
     *
     * @param classLoaderClasses the array of classes.
     */
    public static void setClassLoaderInitiatedClasses(Class[] classLoaderClasses) {
        JVMStore.classLoaderClasses = classLoaderClasses;
    }

    /**
     * Gets all loaded classes.
     *
     * @return the array of loaded classes.
     */
    public static Class[] getAllLoadedClasses() {
        return JVMStore.allLoadedClasses;
    }

    /**
     * Gets the system class loader initiates classes.
     *
     * @return the array of classes.
     */
    public static Class[] getSystemClassLoaderInitiatedClasses() {
        return JVMStore.systemLoaderClasses;
    }

    /**
     * Gets the class loader initiated classes.
     *
     * @return the array of classes.
     */
    public static Class[] getClassLoaderInitiatedClasses() {
        return JVMStore.classLoaderClasses;
    }
}