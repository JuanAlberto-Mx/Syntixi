package syntixi.fusion;

/**
 * <code>MapekTemplate</code> class represents the base for implementing generators
 * or some other applications based on the <code>MAPE-K</code> loop.
 * <p>
 * The main goal of <code>MapekTemplate</code> class is providing a polymorphic
 * template such that each provided method is implemented according to a specific
 * problem, but their execution has a unique sequence which cannot be modified.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public abstract class MapekTemplate {

    /**
     * Instance to access each <code>MAPE-K</code> loop stage.
     */
    protected Mapek mapek;

    /**
     * Provides the means to monitor both <code>Java Virtual Machine</code> and the
     * generator's requirement repository in order to detect components running
     * in the <code>JVM</code> and new <code>XML</code> files specified by the user.
     */
    protected abstract void monitoring();

    /**
     * Provides the means to analyze <code>Java</code> components detected by monitoring
     * mechanism in order to determine if the components contain relevant information
     * that satisfies the user requirements.
     */
    protected abstract void analysis();

    /**
     * Provides the mechanism to plan the set of activities to construct a final
     * component based on the user requirements.
     */
    protected abstract void planning();

    /**
     * Provides the means to construct a final component based on the planning mechanism
     * result.
     */
    protected abstract void execution();

    /**
     * Encapsulates the functionality of the <code>MAPE-K</code> loop.
     */
    public final void run() {
        monitoring();
        analysis();
        planning();
        execution();
    }
}