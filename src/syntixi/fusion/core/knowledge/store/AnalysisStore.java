package syntixi.fusion.core.knowledge.store;

import syntixi.util.bean.Checklist;
import syntixi.util.bean.Functionality;
import syntixi.util.bean.Requirement;
import syntixi.util.dbc.Component;
import syntixi.util.dbc.Interface;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * <code>AnalysisStore</code> class stores all the information related to fusionable and
 * traditional components, as well as classes and methods that are a candidate for the
 * fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class AnalysisStore {

    /**
     * Map of requirement checklists.
     */
    private Map<Requirement, Checklist> checklists = new HashMap<>();

    /**
     * List of fusionable components.
     */
    private Vector<Class> fusionableComponents = new Vector<>();

    /**
     * List of traditional components.
     */
    private Vector<Class> traditionalComponents = new Vector<>();

    /**
     * Map of candidate methods.
     */
    private Map<Requirement, Map<Functionality, Method>> candidateMethods = new HashMap<>();

    /**
     * Instance to access to the <code>AnalysisStore</code> class.
     */
    private static AnalysisStore analysisStore;

    /**
     * Private constructor to limit outer instantiations.
     */
    private AnalysisStore(){
    }

    /**
     * Stores a <code>Requirement</code> instance and its checklist to manage their
     * functionalities.
     *
     * @param requirement the Requirement instance.
     * @param checklist the Checklist instance.
     * @see Requirement
     * @see Checklist
     */
    public void setChecklists(Requirement requirement, Checklist checklist) {
        checklists.put(requirement, checklist);
    }

    /**
     * Stores a class with a contract specified by using the <code>Component</code>
     * and <code>Interface</code> annotations.
     *
     * @param cls the class with <code>Component</code> and <code>Interface</code>
     *            annotations.
     * @see Component
     * @see Interface
     */
    public void setFusionableComponents(Class cls) {
        fusionableComponents.add(cls);
    }

    /**
     * Stores a class without <code>Component</code> and <code>Interface</code> annotations.
     *
     * @param cls the class without <code>Component</code> and <code>Interface</code>
     *           annotations.
     * @see Component
     * @see Interface
     */
    public void setTraditionalComponents(Class cls) {
        traditionalComponents.add(cls);
    }

    /**
     * Stores both a requirement and a set of methods in order to know which methods
     * meet the functionality required by the user.
     *
     * @param requirement the requirement requested by the user.
     * @param methods the methods compatible with the functionality required by the user.
     * @see Requirement
     * @see Functionality
     */
    public void setCandidateMethods(Requirement requirement, Map<Functionality, Method> methods) {
        candidateMethods.put(requirement, methods);
    }

    /**
     * Returns a hashmap of the user requirements and its corresponding checklist.
     *
     * @return the map of the user requirements and its corresponding functionalities
     *         checklist.
     */
    public Map<Requirement, Checklist> getChecklists() {
        return checklists;
    }

    /**
     * Returns a list of fusionable components.
     *
     * @return the list of fusionable components.
     */
    public Vector<Class> getFusionableComponents() {
        return fusionableComponents;
    }

    /**
     * Returns a list of traditional components.
     *
     * @return the list of traditional components.
     */
    public Vector<Class> getTraditionalComponents() {
        return traditionalComponents;
    }

    /**
     * Returns a map of requirements and candidate methods.
     *
     * @return the map of requirements and candidate methods.
     */
    public Map<Requirement, Map<Functionality, Method>> getCandidateMethods() {
        return candidateMethods;
    }

    /**
     * Removes the checklist for a specific requirement.
     *
     * @param requirement the requirement that contains the checklist.
     */
    public void deleteChecklist(Requirement requirement) {
        checklists.remove(requirement);
    }

    /**
     * Remove the candidate methods for a specific requirement.
     *
     * @param requirement the requirement that contains the candidate methods.
     */
    public void deleteCandidateMethods(Requirement requirement) {
        candidateMethods.remove(requirement);
    }

    /**
     * Returns a unique <code>AnalysisStore</code> class instance.
     *
     * @return the <code>AnalysisStore</code> class instance.
     */
    public static AnalysisStore getAnalysisStore() {
        if(analysisStore == null)
            analysisStore = new AnalysisStore();

        return analysisStore;
    }

    /**
     * Prints all the requirements and its checklists.
     */
    public void printChecklists() {
        checklists.forEach((key, value) -> {
            System.out.println(key.getDescription().getName());
            value.getChecklist().forEach((functionality, status) -> System.out.println("\t" + functionality.getKeywords()[0] + "\t" + status));
        });
    }

    /**
     * Prints all the fusionable components detected in the <code>Java Virtual Machine</code>.
     */
    public void printFusionableComponents() {
        fusionableComponents.forEach(cls -> System.out.println(cls.getName()));
    }

    /**
     * Prints all the traditional components detected in the <code>Java Virtual Machine</code>.
     */
    public void printTraditionalComponents() {
        traditionalComponents.forEach(cls -> System.out.println(cls.getName()));
    }

    /**
     * Prints all the functionalities required by the user and the methods that allow
     * to solve each one of them. Such methods are considered a candidate for the
     * fusion process.
     */
    public void printCandidateMethods() {
        candidateMethods.forEach((requirement, methods) -> {
            System.out.println("Requirement:\t" + requirement);

            methods.forEach((functionality, method) -> {
                for(String keyword : functionality.getKeywords())
                    System.out.print(keyword + "\t");

                if (method != null)
                    System.out.println("=>\t" + method.getName());
                else
                    System.out.println("=>\tNot found.");
            });
        });
    }
}