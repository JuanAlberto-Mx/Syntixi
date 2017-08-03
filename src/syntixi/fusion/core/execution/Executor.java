package syntixi.fusion.core.execution;

import javassist.CtClass;
import syntixi.fusion.Mapek;
import syntixi.fusion.core.knowledge.store.AnalysisStore;
import syntixi.fusion.core.knowledge.store.PlanningStore;
import syntixi.fusion.core.knowledge.store.RequirementsStore;
import syntixi.fusion.core.knowledge.store.StatusStore;
import syntixi.util.xml.DeleteXMLInstance;
import syntixi.util.xml.Invoker;
import syntixi.util.xml.XMLManager;

import java.util.Vector;

/**
 * <code>Executor</code> class encapsulates the functionality of a requirement dispatcher.
 * This class allows determining the suitable factory to fuse the components dynamically.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Executor implements Mapek {

    /**
     * The component factory instance.
     */
    AbstractFactory componentFactory = new ComponentFactory();

    @Override
    public void init() {
        dispatcher();
    }

    /**
     * Dispatches all user requirements detected.
     */
    public void dispatcher() {
        /*getPlanningStore().getReadyComponents().forEach((requirement, ctClasses) -> {
            String alternative = requirement.getAlternative().getAlternative().trim().toUpperCase();

            determineFactory(alternative, ctClasses);
        });*/
        StatusStore.getStatusStore().getRequirementStatus().forEach((requirement, status) -> {
            if(status) {
                System.out.println("\tRequirement:\t" + requirement.getDescription().getName() + " is completed");
            }
            else {
                System.out.println("\tRequirement:\t" + requirement.getDescription().getName() + " is not completed");
            }

            XMLManager xmlManager = new XMLManager(requirement);
            DeleteXMLInstance deleteXMLInstance = new DeleteXMLInstance(xmlManager);

            new Invoker().start(deleteXMLInstance);

            //MonitoringStore.getMonitoringStore().printRequirements();

            AnalysisStore.getAnalysisStore().deleteChecklist(requirement);
            AnalysisStore.getAnalysisStore().deleteCandidateMethods(requirement);
            PlanningStore.getPlanningStore().deleteComponentsPerRequirement(requirement);
            PlanningStore.getPlanningStore().deleteFusionScenario(requirement);
            PlanningStore.getPlanningStore().deleteProvisionPerComponent(requirement);
            PlanningStore.getPlanningStore().deleteReadyComponents(requirement);
            RequirementsStore.getRequirementsStore().deleteRequirement(requirement);
        });
    }

    /**
     * Determines the suitable factory to fuse the components.
     *
     * @param alternative the alternative selected.
     * @param ctClasses the classes to fuse.
     */
    public void determineFactory(String alternative, Vector<CtClass> ctClasses) {
        if(alternative.equals("OCC") || alternative.equals("OCWC")) {
            OComponent oComponent = componentFactory.buildOComponent(alternative);
            oComponent.build(ctClasses);
        }
        else if(alternative.equals("GCC") || alternative.equals("GCWC1") || alternative.equals("GCWC2")) {
            GComponent gComponent = componentFactory.buildGComponent(alternative);
            gComponent.build(ctClasses);
        }
    }
}