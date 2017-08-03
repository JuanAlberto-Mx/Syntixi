package syntixi.fusion.core.analysis;

import agent.JVMExplorer;
import com.sun.tools.attach.VirtualMachineDescriptor;
import syntixi.util.bean.Checklist;
import syntixi.util.bean.Functionality;
import syntixi.util.bean.Requirement;
import syntixi.util.dbc.Component;
import syntixi.util.dbc.Interface;
import syntixi.util.reflect.ClassExplorer;
import syntixi.util.reflect.ComponentExplorer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static syntixi.fusion.core.knowledge.store.AnalysisStore.getAnalysisStore;
import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>ComponentAnalyzer</code> class represents the core of the analysis stage and is
 * the base for the planning stage of the <code>MAPE-K</code> loop.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ComponentAnalyzer {

    /**
     * Map to have the relation of functionalities and methods that solve each functionality.
     */
    private Map<Functionality, Method> candidateMethods;

    /**
     * Aggregates or replace a functionality in the map of candidate methods.
     *
     * @param functionality the functionality to aggregate.
     * @param method the method that solves the functionality required.
     * @see Functionality
     */
    private void addCandidateMethod(Functionality functionality, Method method) {
        if(candidateMethods.containsKey(functionality))
            candidateMethods.replace(functionality, method);
        else
            candidateMethods.put(functionality, method);
    }

    /**
     * Separates components according to its nature; fusionable or traditional.
     * <p>
     * A fusionable component follows the principles of Design by Contract by using both
     * <code>Component</code> and <code>Interface</code> annotations. If te component does
     * not has such properties, it is considered traditional.
     * <p>
     * All the components involved in the filtering process are stored in particular lists
     * of the <code>AnalysisStore</code> class.
     * @see Component
     * @see Interface
     */
    public void separateComponents() {
        String className;
        Class cls;

        for(VirtualMachineDescriptor vmd : getMonitoringStore().getComponents()) {
            if (vmd.displayName().contains(" "))
                className = vmd.displayName().split(" ")[1].trim();
            else
                className = vmd.displayName();

            if(!className.trim().isEmpty()) {
                cls = ClassExplorer.getClass(className);

                if(ComponentExplorer.isFusionable(cls))
                    getAnalysisStore().setFusionableComponents(cls);
                else
                    getAnalysisStore().setTraditionalComponents(cls);
            }
        }
    }

    /**
     * Performs a basic analysis of components and tries to find the suitable elements
     * to meet each functionality described in the <code>XML</code> user requirement as
     * much as it possible.
     * <p>
     * To achieve the goal, an analysis both running components and <code>Java API</code>
     * is performed.
     *
     * @param requirement the user requirement to solve.
     * @param checklist the checklist to update the user requirement status.
     * @see Requirement
     * @see Checklist
     */
    public void basicAnalysis(Requirement requirement, Checklist checklist) {
        String name = requirement.getDescription().getName();
        String goal = requirement.getDescription().getGoal();
        candidateMethods = new HashMap<>();

        boolean flag = false;

        for(Map.Entry<Functionality, Boolean> functionality : checklist.getChecklist().entrySet()) {
            System.out.println("\t\tFunctionality:\t" + functionality.getKey().getKeywords()[0]);

            for(Class cls : getAnalysisStore().getFusionableComponents()) {
                Component component = (Component)ComponentExplorer.getComponentAnnotation(cls);

                if(ComponentExplorer.hasSimilarGoal(component, goal)) {
                    Method[] methods = cls.getDeclaredMethods();

                    for(Method method : methods){
                        if(ComponentExplorer.isFusionable(method)) {
                            Interface interfaz = (Interface)ComponentExplorer.getMethodAnnotation(method);

                            if(ComponentExplorer.containsKeyword(interfaz, functionality.getKey().getKeywords())) {
                                if(ComponentExplorer.hasCompatibleParameters(method, functionality.getKey().getInput())) {
                                    checklist.setChecklist(functionality.getKey(), true);
                                    addCandidateMethod(functionality.getKey(), method);
                                    flag = true;
                                    break;
                                }
                                else
                                    addCandidateMethod(functionality.getKey(), null);
                            }
                            else
                                addCandidateMethod(functionality.getKey(), null);
                        }
                        else
                            addCandidateMethod(functionality.getKey(), null);
                    }
                }
                else
                    addCandidateMethod(functionality.getKey(), null);

                if(flag) {
                    flag = false;
                    break;
                }
            }
        }

        checklist.filterBy(false).forEach((functionality, status) -> {
            analyzeAPI(functionality, checklist);
        });

        getAnalysisStore().setCandidateMethods(requirement, candidateMethods);
    }

    /**
     * Analyzes each functionality described in the <code>XML</code> user requirement
     * in order to find the suitable elements in the <code>Java API</code> and fulfill
     * the goal.
     *
     * @param functionality the functionality to solve.
     * @param checklist the checklist to update the user requirement status.
     * @see Functionality
     * @see Checklist
     */
    public void analyzeAPI(Functionality functionality, Checklist checklist) {
        JVMExplorer jvmExplorer = new JVMExplorer();

        String keywords[] = functionality.getKeywords();
        String inputs[] = functionality.getInput();

        for (String userKeyword : keywords) {
            Method method = jvmExplorer.searchCompatibleMethod(userKeyword.trim());

            if (method != null)
                if (ComponentExplorer.hasCompatibleParameters(method, inputs)) {
                    checklist.setChecklist(functionality, true);
                    addCandidateMethod(functionality, method);
                }
                else
                    addCandidateMethod(functionality, null);
        }
    }
}