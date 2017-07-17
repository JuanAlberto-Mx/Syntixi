package syntixi.util.reflect;

import javassist.CtClass;
import javassist.CtMethod;
import syntixi.util.bean.Functionality;
import syntixi.util.bean.Requirement;
import syntixi.util.dbc.Component;
import syntixi.util.dbc.Interface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.RequirementsStore.getRequirementsStore;

/**
 * <code>ComponentExplorer</code> class provides a set of methods that allow to perform
 * an analysis of components in order to get suitable elements to proceed with the
 * fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ComponentExplorer {

    /**
     * Determines if a class is fusionable. A fusionable class follows the principles
     * of Design by Contract by using the <code>Component</code> annotation.
     *
     * @param cls the class to be analyzed.
     * @return <code>true</code> if the class contains the <code>Component</code>
     *         annotation; <code>false</code> otherwise.
     */
    public static boolean isFusionable(Class cls) {
        return cls.isAnnotationPresent(Component.class);
    }

    /**
     * Determines if a method is fusionable. A fusionable method follows the principles
     * of Design by Contract by using the <code>Interface</code> annotation.
     *
     * @param method the method to be analyzed.
     * @return <code>true</code> if the method contains the <code>Interface</code>
     *          annotation; <code>false</code> otherwise.
     */
    public static boolean isFusionable(Method method) {
        return method.isAnnotationPresent(Interface.class);
    }

    /**
     * Obtains the <code>Component</code> annotation of a specific class.
     *
     * @param cls the class to analyze.
     * @return the <code>Component</code> annotation of the class.
     */
    public static Annotation getComponentAnnotation(Class cls) {
        return cls.getAnnotation(Component.class);
    }

    /**
     * Obtains the <code>Interface</code> annotation of a specific method.
     *
     * @param method the method to analyze.
     * @return the <code>Interface</code> annotation of the method.
     */
    public static Annotation getMethodAnnotation(Method method) {
        return method.getAnnotation(Interface.class);
    }

    /**
     * Verifies if a class of the <code>Java API</code> has a similar goal based on the
     * user requirement.
     *
     * @param cls the class to analyze.
     * @param goal the goal to compare.
     * @return <code>true</code> if the class has a similar goal; <code>false</code>
     *          otherwise.
     */
    public static boolean hasSimilarGoal(Class cls, String goal) {
        boolean answer = false;
        int counter = 0;
        String[] singleWords = goal.split(" ");

        for(String word : singleWords) {
            if(cls.getName().contains(word))
                counter++;
        }

        if(counter > (singleWords.length / 2))
            answer = true;

        return answer;
    }

    /**
     * Verifies if a fusionable component or method has a similar goal based on the
     * user requirement.
     *
     * @param annotation the <code>Component/Interface</code> annotation that contains
     *                  the goal of the fusionable component or method.
     * @param goal the goal to compare.
     * @return <code>true</code> if the component or method has a similar goal;
     *          <code>false</code> otherwise.
     */
    public static boolean hasSimilarGoal(Annotation annotation, String goal) {
        boolean answer = false;
        String annotationGoal = null;
        String singleWords[] = goal.split(" ");
        int counter = 0;

        if(annotation instanceof Component)
            annotationGoal = ((Component)annotation).goal();
        else if(annotation instanceof Interface)
            annotationGoal = ((Interface) annotation).goal();

        for(String word : singleWords) {
            if(annotationGoal.contains(word))
                counter++;
        }

        if(counter > (singleWords.length / 2))
            answer = true;

        return answer;
    }

    /**
     * Verifies if a method contains the keywords specified in the user requirement.
     *
     * @param method the method to analyze.
     * @param keywords the keywords to find.
     * @return <code>true</code> if the method has similar keywords; <code>false</code>
     *          otherwise.
     */
    public static boolean containsKeyword(Method method, String... keywords) {
        boolean answer = false;

        for(String singleKeyword : keywords) {
            if(method.getName().equals(singleKeyword)) {
                answer = true;
                break;
            }
        }

        return answer;
    }

    /**
     * Verifies if a fusionable component or method contains the keywords specified
     * in the user requirement.
     *
     * @param annotation the <code>Component/Interface</code> annotation that contains
     *                  the keywords of the fusionable component or method.
     * @param keywords the keywords to compare.
     * @return <code>true</code> if the component or method contains the keyword;
     *          <code>false</code> otherwise.
     */
    public static boolean containsKeyword(Annotation annotation, String... keywords) {
        boolean answer = false;
        String annotationKeywords[] = null;

        if(annotation instanceof Component)
            annotationKeywords = ((Component) annotation).keywords();
        else if(annotation instanceof Interface)
            annotationKeywords = ((Interface) annotation).keywords();

        for(String componentKeyword : annotationKeywords) {
            for(String userKeyword : keywords) {
                if(componentKeyword.equals(userKeyword.trim())) {
                    answer = true;
                    break;
                }
            }
        }

        return answer;
    }

    /**
     * Verifies if a specific method has compatible parameters.
     *
     * @param method the method to analyze.
     * @param parameters the parameters to compare.
     * @return <code>true</code> if the parameters are compatible; <code>false</code>
     *          otherwise.
     */
    public static boolean hasCompatibleParameters(Method method, String... parameters) {
        Class parameterTypes[] = method.getParameterTypes();
        Class userTypes[] = fillUserTypes(parameters);

        return isCompatible(parameterTypes,userTypes);
    }

    /**
     * Compares the parameters data types of two methods in order to determine if they
     * are compatible.
     *
     * @param methodParameters the parameters data types of the method.
     * @param userParameters the parameters data types specified in the user requirement.
     * @return <code>true</code> if the parameters data types are compatible;
     *          <code>false</code> otherwise.
     */
    public static boolean isCompatible(Class[] methodParameters, Class[] userParameters) {
        boolean answer = false;

        if(methodParameters.length != userParameters.length)
            answer = false;
        else if(methodParameters.length == 0 && userParameters.length == 0)
            answer = true;
        else{
            for(int i = 0; i < methodParameters.length; i++) {
                if(isCompatible(userParameters[i], methodParameters[i]))
                    answer = true;
                else {
                    answer = false;
                    break;
                }
            }
        }

        return answer;
    }

    /**
     * Determines the compatibility between two parameters.
     *
     * @param methodParameter the parameter data type.
     * @param userParameter the parameter data type described in the user requirement.
     * @return <code>true</code> if the parameters are compatible; <code>false</code>
     *          otherwise.
     */
    public static boolean isCompatible(Class methodParameter, Class userParameter) {
        boolean answer = false;
        final Vector<Class> wrapperTypes = new Vector<>();

        wrapperTypes.add(Boolean.class);
        wrapperTypes.add(Byte.class);
        wrapperTypes.add(Character.class);
        wrapperTypes.add(Short.class);
        wrapperTypes.add(Long.class);
        wrapperTypes.add(Integer.class);
        wrapperTypes.add(Float.class);
        wrapperTypes.add(Double.class);

        try {
            if(methodParameter.getName().equals(userParameter.getName()))
                answer = true;
            else if(wrapperTypes.contains(methodParameter)) {
                if(methodParameter.getDeclaredField("TYPE").get(null).equals(userParameter))
                    answer = true;
            }
            else if(wrapperTypes.contains(userParameter)) {
                if(userParameter.getDeclaredField("TYPE").get(null).equals(methodParameter))
                    answer = true;
            }
            else if(methodParameter.isAssignableFrom(userParameter) || userParameter.isAssignableFrom(methodParameter)) {
                answer = true;
            }
            else if(methodParameter.isPrimitive() && userParameter.isPrimitive()) {
                if(!methodParameter.getName().contains("char") && !methodParameter.getName().contains("boolean") &&
                        !userParameter.getName().contains("char") && !userParameter.getName().contains("boolean")) {
                    answer = true;
                }
            }
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return answer;
    }

    /**
     * Stores a set of parameters in a primitive data type array.
     *
     * @param parameters the parameter to store.
     * @return an array of primitive data types.
     */
    public static Class[] fillUserTypes(String... parameters) {
        Class userTypes[] = new Class[parameters.length];

        try {
            for (int i = 0; i < userTypes.length; i++) {
                switch (parameters[i].trim()) {
                    case "boolean":
                        userTypes[i] = boolean.class;
                        break;
                    case "byte":
                        userTypes[i] = byte.class;
                        break;
                    case "char":
                        userTypes[i] = char.class;
                        break;
                    case "short":
                        userTypes[i] = short.class;
                        break;
                    case "long":
                        userTypes[i] = long.class;
                        break;
                    case "int":
                        userTypes[i] = int.class;
                        break;
                    case "float":
                        userTypes[i] = float.class;
                        break;
                    case "double":
                        userTypes[i] = double.class;
                        break;
                    default:
                        userTypes[i] = Class.forName(parameters[i]);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return userTypes;
    }

    /**
     * Verifies if the return type of a specific method is similar to the return
     * type described in the user requirement.
     *
     * @param method the method to analyze.
     * @param returnType the return type specified in the user requirement.
     * @return <code>true</code> if the method has a similar return type; <code>false</code>
     *          otherwise.
     */
    public static boolean hasCompatibleReturnType(Method method, String returnType) {
        boolean answer = false;

        if(method.getReturnType().getSimpleName().equals(returnType))
            answer = true;

        return answer;
    }

    /**
     * Obtains the most important class of a set of classes.
     *
     * @param classes the list of classes to analyze.
     * @return the class with the highest importance.
     */
    public static Class getMostImportantClass(Vector<Class> classes) {
        Class mostImportant = null;

        Vector<Class> veryHigh = new Vector<>();
        Vector<Class> high = new Vector<>();
        Vector<Class> medium = new Vector<>();
        Vector<Class> low = new Vector<>();
        Vector<Class> temporal = null;

        for(Class cls : classes) {
            Component annotation = (Component)cls.getAnnotation(Component.class);

            if(annotation.importance() == Component.Measure.VERY_HIGH)
                veryHigh.add(cls);
            else if(annotation.importance() == Component.Measure.HIGH)
                high.add(cls);
            else if(annotation.importance() == Component.Measure.MEDIUM)
                medium.add(cls);
            else if(annotation.importance() == Component.Measure.LOW)
                low.add(cls);
        }

        if(!veryHigh.isEmpty())
            temporal = veryHigh;
        else if(!high.isEmpty())
            temporal = high;
        else if(!medium.isEmpty())
            temporal = medium;
        else if(!low.isEmpty())
            temporal = low;

        if(temporal.size() > 1)
            mostImportant = getMostSusceptibleClass(temporal);
        else
            mostImportant = temporal.firstElement();

        return mostImportant;
    }

    /**
     * Obtains the most important method of a set of methods.
     *
     * @param methods the list of methods to analyze.
     * @return the method with the highest importance.
     */
    public static Method getMostImportantOperation(Vector<Method> methods) {

        Method mostImportant = null;

        Vector<Method> veryHigh = new Vector<>();
        Vector<Method> high = new Vector<>();
        Vector<Method> medium = new Vector<>();
        Vector<Method> low = new Vector<>();
        Vector<Method> temporal = null;

        for(Method method : methods) {
            Interface annotation = (Interface)method.getAnnotation(Interface.class);

            if(annotation.importance() == Interface.Measure.VERY_HIGH)
                veryHigh.add(method);
            else if(annotation.importance() == Interface.Measure.HIGH)
                high.add(method);
            else if(annotation.importance() == Interface.Measure.MEDIUM)
                medium.add(method);
            else if(annotation.importance() == Interface.Measure.LOW)
                low.add(method);
        }

        if(!veryHigh.isEmpty())
            temporal = veryHigh;
        else if(!high.isEmpty())
            temporal = high;
        else if(!medium.isEmpty())
            temporal = medium;
        else if(!low.isEmpty())
            temporal = low;

        if(temporal.size() > 1)
            mostImportant = getMostSusceptibleOperation(temporal);
        else
            mostImportant = temporal.firstElement();

        return mostImportant;
    }

    /**
     * Obtains the class most susceptible to changes between a set of classes.
     *
     * @param classes the list of classes to analyze.
     * @return the most susceptible class.
     */
    public static Class getMostSusceptibleClass(Vector<Class> classes) {
        Class mostSusceptible = null;

        Vector<Class> veryHigh = new Vector<>();
        Vector<Class> high = new Vector<>();
        Vector<Class> medium = new Vector<>();
        Vector<Class> low = new Vector<>();

        for(Class cls : classes) {
            Component annotation = (Component)cls.getAnnotation(Component.class);

            if(annotation.susceptible() == Component.Measure.VERY_HIGH)
                veryHigh.add(cls);
            else if(annotation.susceptible() == Component.Measure.HIGH)
                high.add(cls);
            else if(annotation.susceptible() == Component.Measure.MEDIUM)
                medium.add(cls);
            else if(annotation.susceptible() == Component.Measure.LOW)
                low.add(cls);
        }

        if(!veryHigh.isEmpty())
            mostSusceptible = veryHigh.firstElement();
        else if(!high.isEmpty())
            mostSusceptible = high.firstElement();
        else if(!medium.isEmpty())
            mostSusceptible = medium.firstElement();
        else if(!low.isEmpty())
            mostSusceptible = low.firstElement();

        return mostSusceptible;
    }

    /**
     * Obtains the method most susceptible to changes between a set of methods.
     *
     * @param methods the list of methods to analyze.
     * @return the most susceptible method.
     */
    public static Method getMostSusceptibleOperation(Vector<Method> methods) {
        Vector<Method> veryHigh = new Vector<>();
        Vector<Method> high = new Vector<>();
        Vector<Method> medium = new Vector<>();
        Vector<Method> low = new Vector<>();

        Method mostSusceptible = null;

        for(Method method : methods) {
            Interface annotation = (Interface)method.getAnnotation(Interface.class);

            if(annotation.susceptible() == Interface.Measure.VERY_HIGH)
                veryHigh.add(method);
            else if(annotation.susceptible() == Interface.Measure.HIGH)
                high.add(method);
            else if(annotation.susceptible() == Interface.Measure.MEDIUM)
                medium.add(method);
            else if(annotation.susceptible() == Interface.Measure.LOW)
                low.add(method);
        }

        if(!veryHigh.isEmpty())
            mostSusceptible = veryHigh.firstElement();
        else if(!high.isEmpty())
            mostSusceptible = high.firstElement();
        else if(!medium.isEmpty())
            mostSusceptible = medium.firstElement();
        else if(!low.isEmpty())
            mostSusceptible = low.firstElement();

        return mostSusceptible;
    }

    /**
     * Converts a set of parameters specified as strings to a signature pattern
     * of input parameters for a specific method.
     *
     * @param input the parameters to convert.
     * @return a signature pattern of input parameters.
     */
    public static String toInputParameters(String... input) {
        String concatenation = "";
        char parameterName = 'a';
        int parameterIndex = 0;

        for(int i = 0; i < input.length; i++) {
            if(i >= 26 && i % 26 == 0) {
                parameterIndex++;
                parameterName = 'a';
            }

            concatenation += input[i] + " " + parameterName + "" + parameterIndex + ", ";

            parameterName++;
        }

        return concatenation.substring(0, concatenation.lastIndexOf(','));
    }

    /**
     * Converts input parameters specified as string to a signature pattern of output
     * parameters for a specific method.
     *
     * @param input the input parameters to convert.
     * @return a signature pattern of output parameters.
     */
    public static String toOutputParameters(String input) {
        String parameters[] = input.split(",");
        String concatenation = "";

        for(String parameter : parameters) {
            String auxiliar[] = parameter.trim().split(" ");
            concatenation += auxiliar[1] + ",";
        }

        String finalParameters = concatenation.substring(0, concatenation.lastIndexOf(','));

        return finalParameters;
    }

    /**
     * Casts the parameters according to a specific method.
     *
     * @param outputParameters the parameters to cast.
     * @param candidateMethod the method that contain the target parameters.
     * @return a signature pattern of casted parameters.
     */
    public static String castInputParameters(String outputParameters, CtMethod candidateMethod) {
        String concatenation = "";

        try {
            CtClass methodParameters[] = candidateMethod.getParameterTypes();

            String output[] = outputParameters.trim().split(",");

            for (int i = 0; i < methodParameters.length; i++)
                concatenation += "(" + methodParameters[i].getSimpleName() + ")" + output[i] + ",";
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return concatenation.substring(0, concatenation.lastIndexOf(','));
    }

    /**
     * Casts the parameters according to a specific method.
     *
     * @param outputParameters the parameters to cast.
     * @param candidateMethod the method that contain the target parameters.
     * @return a signature pattern of casted parameters.
     */
    public static String castInputParameters(String outputParameters, Method candidateMethod) {
        String concatenation = "";

        try {
            Class methodParameters[] = candidateMethod.getParameterTypes();

            String output[] = outputParameters.trim().split(",");

            for (int i = 0; i < methodParameters.length; i++)
                concatenation += "(" + methodParameters[i].getSimpleName() + ")" + output[i] + ",";
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return concatenation.substring(0, concatenation.lastIndexOf(','));
    }

    /**
     * Finds the index of a candidate method.
     *
     * @param method the method to find.
     * @return the index of the candidate method.
     */
    public static int getCandidateMethodIndex(Method method) {
        int index = 0;

        for(Requirement requirement : getRequirementsStore().getRequirements()) {
            for (int i = 0; i < requirement.getFunctionalities().size(); i++) {
                Functionality functionality = requirement.getFunctionalities().get(i);

                for (String keyword : functionality.getKeywords())
                    if (method.getName().contains(keyword)) {
                        index = i;
                        break;
                    }
            }
        }

        return index;
    }
}