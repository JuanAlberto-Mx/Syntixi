package syntixi.fusion.core.planning;

import syntixi.util.bean.Requirement;
import syntixi.util.bean.Scenario;

/**
 * <code>Strategy</code> interface provides a set of methods to perform the fusion
 * of components according to the elements provided for each one.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public interface Strategy {

    /**
     * Determines the fusion strategy according to a specific scenario.
     *
     * @param requirement the current requirement.
     * @param scenario the scenario found.
     */
    default void fuse(Requirement requirement, Scenario scenario) {
        switch (scenario) {
            case ALL_COMPLETE: allComplete(requirement); break;
            case ALL_PARTIAL: allPartial(requirement); break;
            case COMPLETE_AND_PARTIAL: completeAndPartial(requirement); break;
            case COMPLETE_PARTIAL_NONE: completePartialNone(requirement); break;
            case COMPLETE_AND_NONE: completeAndNone(requirement); break;
            case PARTIAL_AND_NONE: partialAndNone(requirement); break;
            case NONE: none(requirement); break;
        }
    }

    /**
     * Strategy to fuse components that provide all their elements.
     *
     * @param requirement the current requirement.
     */
    void allComplete(Requirement requirement);

    /**
     * Strategy to fuse components that provide their elements partially.
     *
     * @param requirement the current requirement.
     */
    void allPartial(Requirement requirement);

    /**
     * Fusion strategy applied where some components provide their elements completely
     * but others do it partially.
     *
     * @param requirement the current requirement.
     */
    void completeAndPartial(Requirement requirement);

    /**
     * Fusion strategy applied where some components provide their elements completely,
     * others do it partially and some others do not provide anything.
     *
     * @param requirement the current requirement.
     */
    void completePartialNone(Requirement requirement);

    /**
     * Fusion strategy applied where some components provide their elements completely,
     * but others do not provide anything.
     *
     * @param requirement the current requirement.
     */
    void completeAndNone(Requirement requirement);

    /**
     * Fusion strategy applied where some components provide their elements partially,
     * but others do not provide anything.
     *
     * @param requirement the current requirement.
     */
    void partialAndNone(Requirement requirement);

    /**
     * Fusion strategy applied where the components do not provide anything.
     *
     * @param requirement the current requirement.
     */
    void none(Requirement requirement);
}