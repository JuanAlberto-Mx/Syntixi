package syntixi.fusion.core.planning;

import javassist.CtClass;
import syntixi.fusion.core.knowledge.store.StatusStore;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Requirement;
import syntixi.util.inst.ComponentBuilder;

import java.util.Map;
import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.PlanningStore.getPlanningStore;

/**
 * <code>GCC</code> class encapsulates the alternative to fuse components by
 * generating components and connectors.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class GCC implements Strategy {

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

        Map<Class, Provision> usedComponents = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classIntegerEntry : usedComponents.entrySet()) {
                Class cls = classIntegerEntry.getKey();
                Provision provision = classIntegerEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE) {
                    CtClass ctCopyClass = componentBuilder.createCopyClass(cls, "sg.gcc.");

                    readyClasses.add(ctCopyClass);
                }
            }

            CtClass ctFacadeConnector = componentBuilder.createFacadeConnector("sg.gcc.", readyClasses);

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcc.", "MainClass", ctFacadeConnector);

            readyClasses.add(ctFacadeConnector);
            readyClasses.add(ctSimpleConnector);

            //StatusStore.getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void allPartial(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        Map<Class, Provision> usedComponents = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classIntegerEntry : usedComponents.entrySet()) {
                Class cls = classIntegerEntry.getKey();
                Provision provision = classIntegerEntry.getValue();

                if(cls != null && provision == Provision.PARTIAL) {
                    CtClass ctThinnerClass = componentBuilder.createThinnerClass(cls, "sg.gcc.");

                    readyClasses.add(ctThinnerClass);
                }
            }

            CtClass ctFacadeConnector = componentBuilder.createFacadeConnector("sg.gcc.", readyClasses);

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcc.", "MainClass", ctFacadeConnector);

            readyClasses.add(ctFacadeConnector);
            readyClasses.add(ctSimpleConnector);

            //StatusStore.getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void completeAndPartial(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        Map<Class, Provision> provisionMap = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classProvisionEntry : provisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                CtClass ctClass = null;

                if(cls != null) {
                    if(provision == Provision.COMPLETE) {
                        ctClass = componentBuilder.createCopyClass(cls, "sg.gcc.");
                    }
                    else if(provision == Provision.PARTIAL) {
                        ctClass = componentBuilder.createThinnerClass(cls, "sg.gcc.");
                    }

                    readyClasses.add(ctClass);
                }
            }

            CtClass ctFacadeConnector = componentBuilder.createFacadeConnector("sg.gcc.", readyClasses);

            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.gcc.", "MainClass", ctFacadeConnector);

            readyClasses.add(ctFacadeConnector);
            readyClasses.add(ctSimpleConnector);

            //StatusStore.getStatusStore().setBasicAnalysis(true);
            StatusStore.getStatusStore().setRequirementStatus(requirement, true);
        }
        catch (Exception e) {
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void completePartialNone(Requirement requirement) {
        try {
            completeAndPartial(requirement);

            //StatusStore.getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void completeAndNone(Requirement requirement) {
        try {
            allComplete(requirement);

            //StatusStore.getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void partialAndNone(Requirement requirement) {
        try {
            allPartial(requirement);

            //StatusStore.getStatusStore().setBasicAnalysis(false);
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
            //StatusStore.getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }
}