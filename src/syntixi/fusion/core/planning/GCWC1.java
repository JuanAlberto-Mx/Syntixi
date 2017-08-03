package syntixi.fusion.core.planning;

import javassist.CtClass;
import syntixi.fusion.core.knowledge.store.PlanningStore;
import syntixi.fusion.core.knowledge.store.StatusStore;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Requirement;
import syntixi.util.inst.ComponentBuilder;

import java.util.Map;
import java.util.Vector;

/**
 * <code>GCWC1</code> encapsulates the alternative to fusing components by
 * generating new components without using connectors to communicate them.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class GCWC1 implements Strategy {

    /**
     * The component builder instance.
     */
    private ComponentBuilder componentBuilder;

    /**
     * The classes ready to implement.
     */
    private Vector<CtClass> readyClasses;

    @Override
    public void allComplete(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = PlanningStore.getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            CtClass ctMonolithicClass = componentBuilder.createBasicClass("sg.gcwc1.");

            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE) {
                    componentBuilder.addAllMethods(cls, ctMonolithicClass);
                }
            }

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcwc1.", "MainClass", ctMonolithicClass);

            readyClasses.add(ctMonolithicClass);
            readyClasses.add(ctSimpleConnector);

            //getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            PlanningStore.getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void allPartial(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = PlanningStore.getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            CtClass ctMonolithicClass = componentBuilder.createBasicClass("sg.gcwc1.");

            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.PARTIAL) {
                    componentBuilder.addCandidateMethods(ctMonolithicClass);
                }
            }

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcwc1.", "MainClass", ctMonolithicClass);

            readyClasses.add(ctMonolithicClass);
            readyClasses.add(ctSimpleConnector);

            //getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            PlanningStore.getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void completeAndPartial(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = PlanningStore.getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            CtClass ctMonolithicClass = componentBuilder.createBasicClass("sg.gcwc1.");

            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE) {
                    componentBuilder.addAllMethods(cls, ctMonolithicClass);
                }
                else if(cls != null && provision == Provision.PARTIAL) {
                    componentBuilder.addCandidateMethods(ctMonolithicClass);
                }
            }

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcwc1.", "MainClass", ctMonolithicClass);

            readyClasses.add(ctMonolithicClass);
            readyClasses.add(ctSimpleConnector);

            //getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            PlanningStore.getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void completePartialNone(Requirement requirement) {
        try {
            completeAndPartial(requirement);

            //getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void completeAndNone(Requirement requirement) {
        try {
            allComplete(requirement);

            //getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void partialAndNone(Requirement requirement) {
        try {
            allPartial(requirement);

            //getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void none(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        try {
            //getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {

        }
        finally {
            PlanningStore.getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }
}