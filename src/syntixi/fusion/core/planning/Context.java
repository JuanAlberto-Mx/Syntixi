package syntixi.fusion.core.planning;

import syntixi.util.bean.Requirement;
import syntixi.util.bean.Scenario;

/**
 * <code>Context</code> class provides a method to execute the fusion strategy
 * selected by the planning mechanism.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Context {

    /**
     * The current requirement instance.
     */
    private Requirement requirement;

    /**
     * The scenario detected.
     */
    private Scenario scenario;

    /**
     * The strategy selected.
     */
    private Strategy strategy;

    /**
     * Encapsulates the context to be executed by the fusion mechanism.
     *
     * @param requirement the current requirement instance.
     * @param scenario the scenario detected.
     * @param strategy the strategy selected.
     */
    public Context(Requirement requirement, Scenario scenario, Strategy strategy) {
        this.requirement = requirement;
        this.scenario = scenario;
        this.strategy = strategy;
    }

    /**
     * Executes an strategy selected.
     */
    public void execute() {
        strategy.fuse(requirement, scenario);
    }
}