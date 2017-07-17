package syntixi.fusion.core.planning;

import javassist.CtClass;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Requirement;
import syntixi.util.inst.ComponentBuilder;
import syntixi.util.reflect.ComponentExplorer;

import java.util.Map;
import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.PlanningStore.getPlanningStore;
import static syntixi.fusion.core.knowledge.store.StatusStore.getStatusStore;

/**
 * <code>OCC</code> encapsulates the alternative to fusing components by using the
 * original components and communicate them through the using of generated connectors.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class OCC implements Strategy {

    /**
     * The component builder instance.
     */
    private ComponentBuilder componentBuilder;

    /**
     * The used classes.
     */
    private Vector<Class> usedClasses;

    /**
     * The classes ready to implement.
     */
    private Vector<CtClass> readyClasses;

    @Override
    public void allComplete(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        Map<Class, Provision> usedComponents = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classIntegerEntry : usedComponents.entrySet()) {
                Class cls = classIntegerEntry.getKey();
                Provision provision = classIntegerEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE)
                    usedClasses.add(cls);
            }

            CtClass ctFacade = componentBuilder.createFacadeConnector(usedClasses, "sg.occ.");
            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.occ.", "MainClass", ctFacade);

            readyClasses.add(ctFacade);
            readyClasses.add(ctSimpleConnector);

            getStatusStore().setBasicAnalysis(true);
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
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        Map<Class, Provision> usedComponents = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for (Map.Entry<Class, Provision> classIntegerEntry : usedComponents.entrySet()) {
                Class cls = classIntegerEntry.getKey();
                Provision provision = classIntegerEntry.getValue();

                if (cls != null && provision == Provision.PARTIAL) {
                    if (ComponentExplorer.isFusionable(cls))
                        componentBuilder.makeItThinner(cls);

                    usedClasses.add(cls);
                }
            }

            CtClass ctFacade = componentBuilder.createFacadeConnector(usedClasses, "sg.occ.");
            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.occ.", "MainClass", ctFacade);

            readyClasses.add(ctFacade);
            readyClasses.add(ctSimpleConnector);

            getStatusStore().setBasicAnalysis(true);
        }
        catch (Exception e){
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    @Override
    public void completeAndPartial(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> provisionEntry : classProvisionMap.entrySet()) {
                Class cls = provisionEntry.getKey();
                Provision provision = provisionEntry.getValue();

                if(cls != null) {
                    if(provision == Provision.COMPLETE) {
                        usedClasses.add(cls);
                    }
                    else if(provision == Provision.PARTIAL) {
                        if(ComponentExplorer.isFusionable(cls)) {
                            componentBuilder.makeItThinner(cls);
                            usedClasses.add(cls);
                        }
                    }
                }
            }

            CtClass ctFacade = componentBuilder.createFacadeConnector(usedClasses, "sg.occ.");
            CtClass ctSimpleConnector = componentBuilder.createSimpleConnector("sg.occ.", "MainClass", ctFacade);

            readyClasses.add(ctFacade);
            readyClasses.add(ctSimpleConnector);

            getStatusStore().setBasicAnalysis(true);
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

            getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void completeAndNone(Requirement requirement) {
        try {
            allComplete(requirement);

            getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void partialAndNone(Requirement requirement) {
        try {
            allPartial(requirement);

            getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void none(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        try {
            getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
        finally {
            getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }
}