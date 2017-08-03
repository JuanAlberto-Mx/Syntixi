package syntixi.fusion.core.planning;

import javassist.CtClass;
import syntixi.fusion.core.knowledge.store.StatusStore;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Requirement;
import syntixi.util.inst.ComponentBuilder;
import syntixi.util.reflect.ComponentExplorer;

import java.util.Map;
import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.PlanningStore.getPlanningStore;

/**
 * <code>OCWC</code> encapsulates the alternative to fusing components by using
 * the original components and communicate them directly without using connectors.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class OCWC implements Strategy {

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

        Map<Class, Provision> classProvisionMap = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE)
                    usedClasses.add(cls);
            }

            componentBuilder.makeItFacade(mostImportantClass);

            //getStatusStore().setBasicAnalysis(true);
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
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.PARTIAL && ComponentExplorer.isFusionable(cls))
                    componentBuilder.makeItThinner(cls);

                usedClasses.add(cls);
            }

            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            componentBuilder.makeItFacade(mostImportantClass);

            componentBuilder.addMainMethod(mostImportantClass);

            //getStatusStore().setBasicAnalysis(true);
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
        usedClasses = new Vector<>();
        readyClasses = new Vector<>();

        Map<Class, Provision> classProvisionMap = getPlanningStore().getProvisionPerComponent().get(requirement);

        try {
            for(Map.Entry<Class, Provision> classProvisionEntry : classProvisionMap.entrySet()) {
                Class cls = classProvisionEntry.getKey();
                Provision provision = classProvisionEntry.getValue();

                if(cls != null && provision == Provision.COMPLETE) {
                    usedClasses.add(cls);
                }
                if(cls != null && provision == Provision.PARTIAL && ComponentExplorer.isFusionable(cls)) {
                    componentBuilder.makeItThinner(cls);
                    usedClasses.add(cls);
                }
            }

            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            componentBuilder.makeItFacade(mostImportantClass);

            componentBuilder.addMainMethod(mostImportantClass);

            //getStatusStore().setBasicAnalysis(true);
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
        try {
            //getStatusStore().setBasicAnalysis(false);
            StatusStore.getStatusStore().setRequirementStatus(requirement, false);
        }
        catch (Exception e) {
        }
    }

    /**
     * Gets the most important class from a set of classes.
     *
     * @param classProvisionMap the map of <code>(Class, Provision)</code>.
     * @return the most important class.
     */
    private Class getMostImportantClass(Map<Class, Provision> classProvisionMap) {
        Vector<Class> classes = new Vector<>();

        classProvisionMap.forEach((cls , provision) -> {
            if(cls != null && ComponentExplorer.isFusionable(cls))
                classes.add(cls);
        });

        return ComponentExplorer.getMostImportantClass(classes);
    }

    /**
     * Gets the most important class from a set of classes.
     *
     * @param classes the set of classes.
     * @param mostImportantClass the most important class.
     * @return the <code>CtClass</code> representation for the resulting class.
     */
    private CtClass getMostImportantClass(Vector<CtClass> classes, Class mostImportantClass) {
        CtClass ctMostImportantClass = null;

        for(CtClass ctClass : readyClasses)
            if(ctClass.getSimpleName().equals(mostImportantClass.getSimpleName())) {
                ctMostImportantClass = ctClass;
                break;
            }

        return ctMostImportantClass;
    }
}