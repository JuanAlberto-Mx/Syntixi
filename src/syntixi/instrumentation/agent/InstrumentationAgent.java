package syntixi.instrumentation.agent;

import syntixi.instrumentation.store.JVMStore;
import java.lang.instrument.Instrumentation;

/**
 * <code>InstrumentationAgent</code> class allows initializing, loading and executing a
 * <code>Java</code> agent before the <code>Sýntixi</code>'s main method execution in
 * order to get information of classes loaded in the <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class InstrumentationAgent {

    /**
     * The <code>Instrumentation</code> instance used by the agent.
     */
    private static Instrumentation instrumentation;

    /**
     * Loads the instrumentation agent and sets the loaded classes before the
     * <code>main</code> method execution.
     */
    public static void init() {
        if (instrumentation == null)
            AgentLoader.loadAgent();

        setAllLoadedClasses();
        setSystemClassLoaderInitiatedClasses();
        setClassLoaderInitiatedClasses(JVMExplorer.class.getClassLoader());
    }

    /**
     * Initializes the instrumentation agent to be used before the <code>main</code>
     * method execution.
     *
     * @param agentArgs the agent arguments.
     * @param instrumentation the <code>Instrumentation</code> instance.
     * @throws Exception
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
        InstrumentationAgent.instrumentation = instrumentation;
        init();
    }

    /**
     * Initializes the <code>Instrumentation</code> instance.
     *
     * @param agentArgs the agent arguments.
     * @param instrumentation the <code>Instrumentation</code> instance.
     */
    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        InstrumentationAgent.instrumentation = instrumentation;
    }

    /**
     * Stores in the <code>JVMStore</code> class all loaded classes.
     */
    public static void setAllLoadedClasses() {
        JVMStore.setAllLoadedClasses(instrumentation.getAllLoadedClasses());
    }

    /**
     * Stores in the <code>JVMStore</code> class all system class loader initiated
     * classes.
     */
    public static void setSystemClassLoaderInitiatedClasses() {
        JVMStore.setSystemClassLoaderInitiatedClasses(instrumentation.getInitiatedClasses(ClassLoader.getSystemClassLoader()));
    }

    /**
     * Stores in the <code>JVMStore</code> class the class loader initiated classes.
     *
     * @param classLoader the class loader used.
     */
    public static void setClassLoaderInitiatedClasses(final ClassLoader classLoader) {
        JVMStore.setClassLoaderInitiatedClasses(instrumentation.getInitiatedClasses(classLoader));
    }

    /**
     * Gets the <code>Instrumentation</code> instance.
     *
     * @return the <code>Instrumentation</code> instance.
     */
    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}