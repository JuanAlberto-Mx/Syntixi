package syntixi.fusion.core.planning;

import syntixi.fusion.Mapek;
import syntixi.util.bean.Requirement;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Scenario;

import java.util.HashMap;
import java.util.Map;

import static syntixi.fusion.core.knowledge.store.AnalysisStore.getAnalysisStore;
import static syntixi.fusion.core.knowledge.store.PlanningStore.getPlanningStore;

/**
 * <code>Planner</code> class represents a starting point to determine the strategy to
 * fuse components as well as execute the different tasks to get the new components.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Planner implements Mapek {

    /**
     * Flag to indicate a class is not used.
     */
    private boolean noneUse = false;

    /**
     * Flag to indicate a class is partially used.
     */
    private boolean partialUse = false;

    /**
     * Flag to indicate a class is completely used.
     */
    private boolean completeUse = false;

    /**
     * The scenario detected.
     */
    private Scenario scenario;

    @Override
    public void init() {
        determineUsedComponents();
        determineScenario();
        getPlanningStore().printFusionScenario();
        selectStrategy();
    }

    /**
     * Sets to <code>false</code> all the flags related to the provision of each component
     * analyzed.
     */
    private void flagsToFalse() {
        noneUse = partialUse = completeUse = false;
    }

    /**
     * Determines the components to use in order to fulfill a specific functionality
     * based on the candidate methods retrieved during the <code>analysis</code> stage.
     */
    public void determineUsedComponents() {
        getAnalysisStore().getCandidateMethods().forEach((requirement, functionalityMethodMap) -> {
            Map<Class, Integer> usedComponents = new HashMap<>();

            functionalityMethodMap.forEach((functionality, method) -> {
                if(method != null) {
                    if (usedComponents.containsKey(method.getDeclaringClass()))
                        usedComponents.replace(method.getDeclaringClass(), usedComponents.get(method.getDeclaringClass()) + 1);
                    else
                        usedComponents.put(method.getDeclaringClass(), 1);
                }
                else
                    usedComponents.put(null, 0);
            });

            getPlanningStore().setComponentsPerRequirement(requirement, usedComponents);
        });
    }

    /**
     * Determines the fusion scenario according to the usage of each component detected.
     */
    public void determineScenario() {
        for(Map.Entry<Requirement, Map<Class, Integer>> componentEntry : getPlanningStore().getComponentsPerRequirement().entrySet()) {
            Map<Class, Provision> componentProvisions = new HashMap<>();

            componentEntry.getValue().forEach((cls, counter) -> {
                if(cls != null) {
                    if (cls.getDeclaredMethods().length == counter)
                        componentProvisions.put(cls, Provision.COMPLETE);
                    else
                        componentProvisions.put(cls, Provision.PARTIAL);
                }
                else
                    componentProvisions.put(null, Provision.NONE);
            });

            getPlanningStore().setProvisionPerComponent(componentEntry.getKey(), componentProvisions);

            for(Map.Entry<Class, Provision> provisionEntry : componentProvisions.entrySet()) {
                if(provisionEntry.getValue() == Provision.PARTIAL)
                    partialUse = true;
                else if(provisionEntry.getValue() == Provision.COMPLETE)
                    completeUse = true;
                else
                    noneUse = true;
            }

            if(partialUse && completeUse && noneUse)
                scenario = Scenario.COMPLETE_PARTIAL_NONE;
            else if(partialUse && completeUse && !noneUse)
                scenario = Scenario.COMPLETE_AND_PARTIAL;
            else if(partialUse && !completeUse && noneUse)
                scenario = Scenario.PARTIAL_AND_NONE;
            else if(partialUse && !completeUse && !noneUse)
                scenario = Scenario.ALL_PARTIAL;
            else if(!partialUse && completeUse && noneUse)
                scenario = Scenario.COMPLETE_AND_NONE;
            else if(!partialUse && completeUse && !noneUse)
                scenario = Scenario.ALL_COMPLETE;
            else if(!partialUse && !completeUse && noneUse)
                scenario = Scenario.NONE;
            else if(!partialUse && !completeUse && !noneUse)
                scenario = Scenario.NONE;

            flagsToFalse();

            getPlanningStore().setFusionScenario(componentEntry.getKey(), scenario);
        }
    }

    /**
     * Selects the appropriate fusion strategy.
     */
    public void selectStrategy() {
        for(Map.Entry<Requirement, Scenario> requirementScenarioEntry : getPlanningStore ().getFusionScenario().entrySet()) {
            Requirement requirement = requirementScenarioEntry.getKey();
            Scenario scenario = requirementScenarioEntry.getValue();

            Context context = dispatcher(requirement, scenario);

            context.execute();
        }
    }

    /**
     * Dispatches the current requirement to the fusion mechanism.
     *
     * @param requirement the current requirement.
     * @param scenario the scenario detected.
     * @return the <code>Context</code> instance to execute the fusion mechanism.
     */
    public Context dispatcher(Requirement requirement, Scenario scenario) {
        Strategy strategy = null;
        Context context = null;

        switch (requirement.getAlternative().getAlternative().trim().toUpperCase()) {
            case "GCC": strategy = new GCC(); break;
            case "GCWC1": strategy = new GCWC1(); break;
            case "GCWC2": strategy = new GCWC2(); break;
            case "OCC": strategy = new OCC(); break;
            case "OCWC": strategy = new OCWC(); break;
        }

        if(strategy != null)
            context = new Context(requirement, scenario, strategy);

        return context;
    }
}