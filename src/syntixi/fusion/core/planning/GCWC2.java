package syntixi.fusion.core.planning;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import syntixi.fusion.core.knowledge.store.PlanningStore;
import syntixi.fusion.core.knowledge.store.StatusStore;
import syntixi.util.bean.Provision;
import syntixi.util.bean.Requirement;
import syntixi.util.inst.ComponentBuilder;
import syntixi.util.reflect.ComponentExplorer;

import java.util.Map;
import java.util.Vector;

/**
 * <code>GCWC2</code> encapsulates the alternative to fusing components by
 * generating new components without using connectors to communicate them.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class GCWC2 implements Strategy {

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
            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            classProvisionMap.forEach((cls, provision) -> {
                if(cls != null && provision == Provision.COMPLETE) {
                    CtClass ctCopy = componentBuilder.createCopyClass(cls, "sg.gcwc2.");

                    readyClasses.add(ctCopy);
                }
            });

            CtClass ctMostImportantClass = getMostImportantClass(readyClasses, mostImportantClass);

            componentBuilder.makeItFacade(ctMostImportantClass);

            addMainMethod(ctMostImportantClass);

            StatusStore.getStatusStore().setBasicAnalysis(true);
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
            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            classProvisionMap.forEach((cls, provision) -> {
                if(cls != null && provision == Provision.PARTIAL) {
                    CtClass ctThinnerClass = componentBuilder.createThinnerClass(cls, "sg.gcwc2.");

                    readyClasses.add(ctThinnerClass);
                }
            });

            CtClass ctMostImportantClass = getMostImportantClass(readyClasses, mostImportantClass);

            componentBuilder.makeItFacade(ctMostImportantClass);

            addMainMethod(ctMostImportantClass);

            StatusStore.getStatusStore().setBasicAnalysis(true);
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
            Class mostImportantClass = getMostImportantClass(classProvisionMap);

            classProvisionMap.forEach((cls, provision) -> {
                if(cls != null) {
                    CtClass ctClass = null;

                    if(provision == Provision.COMPLETE)
                        ctClass = componentBuilder.createCopyClass(cls, "sg.gcwc2.");
                    else if(provision == Provision.PARTIAL)
                        ctClass = componentBuilder.createThinnerClass(cls, "sg.gcwc2.");

                    readyClasses.add(ctClass);
                }
            });

            CtClass ctMostImportantClass = getMostImportantClass(readyClasses, mostImportantClass);

            componentBuilder.makeItFacade(ctMostImportantClass);

            addMainMethod(ctMostImportantClass);

            StatusStore.getStatusStore().setBasicAnalysis(true);
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

            StatusStore.getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void completeAndNone(Requirement requirement) {
        try {
            allComplete(requirement);

            StatusStore.getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void partialAndNone(Requirement requirement) {
        try {
            allPartial(requirement);

            StatusStore.getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
    }

    @Override
    public void none(Requirement requirement) {
        componentBuilder = new ComponentBuilder(requirement);
        readyClasses = new Vector<>();

        try {
            StatusStore.getStatusStore().setBasicAnalysis(false);
        }
        catch (Exception e) {
        }
        finally {
            PlanningStore.getPlanningStore().setReadyComponents(requirement, readyClasses);
        }
    }

    private void addMainMethod(CtClass ctClass) {
        try {
            String mainBody = "public static void main(String[] args){" +
                    "System.out.println(\"Main method executed\");" +
                    "}";

            CtMethod ctMainMethod = CtNewMethod.make(mainBody, ctClass);
            ctClass.addMethod(ctMainMethod);
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