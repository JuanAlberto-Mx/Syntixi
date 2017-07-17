package syntixi.util.inst;

import javassist.*;
import syntixi.util.bean.Functionality;
import syntixi.util.bean.Requirement;
import syntixi.util.reflect.ComponentExplorer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.AnalysisStore.getAnalysisStore;

/**
 * <code>ComponentBuilder</code> class provides a set of methods to build new components.
 * Among the main features of this class are the following:
 * <p>
 * <code>1)</code> Creation of copies of existing components, the creation of thinner
 * components containing only the main necessary elements to operate, and creation of
 * basic components, <code>2)</code> creation of simple and facade connectors to establish
 * communication with one or more existing components and <code>3)</code> creation o class
 * invocations and wrapper methods.
 * <p>
 * In addition, <code>ComponentBuilder</code> contains several methods to make components
 * thinner, convert existing components in facade connectors, adding wrapper methods and
 * new methods to new or existing components.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ComponentBuilder {

    /**
     * The <code>Requirement</code> instance.
     */
    private Requirement requirement;

    /**
     * The <code>ClassPool</code> instance.
     */
    private ClassPool classPool;

    /**
     * The <code>Loader</code> instance.
     */
    private Loader loader;

    /**
     * Constructor to initialize the instances used by <code>ComponentBuilder</code>.
     *
     * @param requirement the <code>Requirement</code> instance.
     */
    public ComponentBuilder(Requirement requirement) {
        this.requirement = requirement;
        classPool = ClassPool.getDefault();
        loader = new Loader(classPool);
    }

    /**
     * Creates a copy class in a specific target directory.
     *
     * @param source the source class.
     * @param targetPackage the target package.
     * @return the <code>CtClass</code> that represents the copied class.
     */
    public CtClass createCopyClass(Class source, String targetPackage) {
        CtClass ctClass = null;

        try {
            if(source != null) {
                ctClass = classPool.get(source.getCanonicalName());
                ctClass.setName(targetPackage + source.getSimpleName());
            }
        }
        catch (Exception e) {
        }

        return ctClass;
    }

    /**
     * Creates a thinner class which contains only the necessary elements from an
     * existing <code>.class</code>.
     *
     * @param source the source class that will be modified.
     * @param targetPackage the target package for the thinner class.
     * @return the <code>CtClass</code> that represents the thinner class.
     */
    public CtClass createThinnerClass(Class source, String targetPackage) {
        Map<Functionality, Method> candidateMethods = getAnalysisStore().getCandidateMethods().get(requirement);

        CtClass ctComponent = null;

        try {
            if (source != null) {
                ctComponent = createCopyClass(source, targetPackage);

                for(CtMethod ctMethod : ctComponent.getDeclaredMethods()) {
                    boolean flag = false;

                    for(Map.Entry<Functionality, Method> methodEntry : candidateMethods.entrySet()) {
                        Method method = methodEntry.getValue();

                        flag = (areEquals(ctMethod, method))? true : false;

                        if(flag)
                            break;
                    }

                    if(!flag)
                        ctComponent.removeMethod(ctMethod);
                }

                toPublicConstructor(ctComponent);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ctComponent;
    }

    /**
     * Creates a basic class, i.e. a class with a <code>main</code> method.
     *
     * @param targetPackage the target package for the new class.
     * @return the <code>CtClass</code> instance for the new class.
     */
    public CtClass createBasicClass(String targetPackage) {
        CtClass ctBasicClass = null;

        try {
            ctBasicClass = classPool.makeClass(targetPackage + requirement.getDescription().getName());

            String constructorBody = "System.out.println(this.getClass().getName() + \" constructor\");";
            CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctBasicClass);
            ctConstructor.setBody(constructorBody);

            ctBasicClass.addConstructor(ctConstructor);
        }
        catch (Exception e) {
        }

        return ctBasicClass;
    }

    /**
     * Makes an existing class thinner, i.e. deletes unnecessary elements from a specific
     * class.
     *
     * @param source the source class.
     */
    public void makeItThinner(Class source) {
        Map<Functionality, Method> candidateMethods = getAnalysisStore().getCandidateMethods().get(requirement);

        try {
            if(source != null) {
                for(Method method : source.getDeclaredMethods()) {
                    boolean flag = false;

                    for(Map.Entry<Functionality, Method> methodEntry : candidateMethods.entrySet()) {
                        Method candidateMethod = methodEntry.getValue();

                        flag = (areEquals(method, candidateMethod))? true : false;

                        if(flag)
                            break;
                    }

                    if(!flag) {
                        CtClass modifiedClass = classPool.get(source.getCanonicalName());
                        CtMethod ctMethod = modifiedClass.getDeclaredMethod(method.getName());
                        modifiedClass.removeMethod(ctMethod);
                        modifiedClass.writeFile();
                    }
                }
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Makes an existing class facade, i.e. adds wrapper methods to an existing class in
     * order to establish communication with other classes.
     *
     * @param source the source class to convert.
     */
    public void makeItFacade(Class source) {
        Map<Functionality, Method> methodMap = getAnalysisStore().getCandidateMethods().get(requirement);
        boolean flag = false;

        try {
            for(Map.Entry<Functionality, Method> methodEntry : methodMap.entrySet()) {
                Functionality functionality = methodEntry.getKey();
                Method candidateMethod = methodEntry.getValue();

                for(Method method : source.getDeclaredMethods()) {
                    if(candidateMethod.getName().equals(method.getName())) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    String inputParameters = ComponentExplorer.toInputParameters(functionality.getInput());
                    String outputParameters = ComponentExplorer.castInputParameters(ComponentExplorer.toOutputParameters(inputParameters), candidateMethod);
                    String wrapperBody = createWrapperMethod(candidateMethod, inputParameters, outputParameters);

                    CtClass modifiedClass = classPool.get(source.getCanonicalName());
                    CtMethod ctWrapperMethod = CtNewMethod.make(wrapperBody, modifiedClass);
                    modifiedClass.addMethod(ctWrapperMethod);
                }

                flag = false;
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Makes an existing <code>CtClass</code> facade, i.e. adds wrapper methods to an
     * existing class in order to establish communication with other classes.
     *
     * @param source the source <code>CtClass</code> to convert.
     */
    public void makeItFacade(CtClass source) {
        Map<Functionality, Method> methodMap = getAnalysisStore().getCandidateMethods().get(requirement);
        boolean flag = false;

        try {
            for (Map.Entry<Functionality, Method> methodEntry : methodMap.entrySet()) {
                Functionality functionality = methodEntry.getKey();
                Method candidateMethod = methodEntry.getValue();

                for(CtMethod ctMethod : source.getDeclaredMethods()) {
                    if(candidateMethod.getName().equals(ctMethod.getName())) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    String inputParameters = ComponentExplorer.toInputParameters(functionality.getInput());
                    String outputParameters = ComponentExplorer.castInputParameters(ComponentExplorer.toOutputParameters(inputParameters), candidateMethod);
                    String wrapperBody = createWrapperMethod(candidateMethod, inputParameters, outputParameters);

                    CtMethod ctWrapperMethod = CtNewMethod.make(wrapperBody, source);
                    source.addMethod(ctWrapperMethod);
                }

                flag = false;
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Creates a connector to communicate components one to one.
     *
     * @param targetPackage the target package of the connector.
     * @param className the connector name.
     * @param targetClass the target <code>CtClass</code> to communicate with.
     * @return the connector represented by a <code>CtClass</code> instance.
     */
    public CtClass createSimpleConnector(String targetPackage, String className, CtClass targetClass) {
        CtClass ctClass = null;

        try {
            ctClass = classPool.makeClass(targetPackage + className);

            String constructorBody = "System.out.println(this.getClass().getName() + \" connector constructor\");";

            CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctClass);
            ctConstructor.setBody(constructorBody);
            ctClass.addConstructor(ctConstructor);

            Vector<CtClass> target = new Vector<>();
            target.add(targetClass);

            String mainBody = "public static void main(String[] args){" +
                    "System.out.println(\"Main method executed\");" +
                    ctClass.getName() + " obj = new " + ctClass.getName() + "();\n" + createClassInvocations(target) +
                    "}";

            CtMethod ctMainMethod = CtMethod.make(mainBody, ctClass);
            ctClass.addMethod(ctMainMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ctClass;
    }

    /**
     * Creates a connector to communicate components one to many.
     *
     * @param classes the classes which will be communicated by the connector.
     * @param targetPackage the target package for the connector.
     * @return the connector represented by a <code>CtClass</code> instance.
     */
    public CtClass createFacadeConnector(Vector<Class> classes, String targetPackage) {
        CtClass ctFacade = null;

        try {
            ctFacade = classPool.makeClass(targetPackage + requirement.getDescription().getName());

            String constructorBody = "System.out.println(this.getClass().getName() + \" constructor\");";

            CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctFacade);
            ctConstructor.setBody(constructorBody);
            ctFacade.addConstructor(ctConstructor);

            boolean flag = false;

            for(Class cls : classes) {
                Method[] methods = cls.getDeclaredMethods();

                for(Method method : methods) {
                    for (Functionality functionality : requirement.getFunctionalities()) {
                        for(String keyword : functionality.getKeywords()) {
                            if (method.getName().equals(keyword)) {
                                String inputParameters = ComponentExplorer.toInputParameters(functionality.getInput());
                                String outputParameters = ComponentExplorer.castInputParameters(ComponentExplorer.toOutputParameters(inputParameters), method);
                                String wrapperBody = createWrapperMethod(method, inputParameters, outputParameters);

                                CtMethod ctWrapperMethod = CtNewMethod.make(wrapperBody, ctFacade);

                                ctFacade.addMethod(ctWrapperMethod);

                                flag = true;
                                break;
                            }
                        }

                        if(flag)
                            break;
                    }

                    flag = false;
                }
            }
        }
        catch (Exception e) {
        }

        return ctFacade;
    }

    /**
     * Creates a connector to communicate components one to many.
     *
     * @param targetPackage the target package for the connector.
     * @param ctClasses the set of <code>CtClass</code> which will be communicated by
     *                 the connector.
     * @return the connector represented by a <code>CtClass</code> instance.
     */
    public CtClass createFacadeConnector(String targetPackage, Vector<CtClass> ctClasses) {
        CtClass ctFacade = null;

        try {
            ctFacade = classPool.makeClass(targetPackage + requirement.getDescription().getName());

            String constructorBody = "System.out.println(this.getClass().getName() + \" constructor\");";

            CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctFacade);
            ctConstructor.setBody(constructorBody);
            ctFacade.addConstructor(ctConstructor);

            boolean flag = false;

            for(CtClass ctClass : ctClasses) {
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();

                for(CtMethod ctMethod : ctMethods) {
                    for (Functionality functionality : requirement.getFunctionalities()) {
                        for(String keyword : functionality.getKeywords()) {
                            if (ctMethod.getName().equals(keyword)) {
                                String inputParameters = ComponentExplorer.toInputParameters(functionality.getInput());
                                String outputParameters = ComponentExplorer.castInputParameters(ComponentExplorer.toOutputParameters(inputParameters), ctMethod);
                                String wrapperBody = createWrapperMethod(ctMethod, inputParameters, outputParameters);

                                CtMethod ctWrapperMethod = CtNewMethod.make(wrapperBody, ctFacade);

                                ctFacade.addMethod(ctWrapperMethod);

                                flag = true;
                                break;
                            }
                        }

                        if(flag)
                            break;
                    }

                    flag = false;
                }
            }
        }
        catch(Exception e) {
        }

        return ctFacade;
    }

    /**
     * Adds a <code>main</code> method to an existing class.
     *
     * @param source the source class.
     */
    public void addMainMethod(Class source) {
        try {
            CtClass ctClass = classPool.get(source.getCanonicalName());

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
     * Adds all methods contained in a source class to a target class.
     *
     * @param source the source class which contains the desired methods.
     * @param target the target <code>CtClass</code> which will contains the methods.
     */
    public void addAllMethods(Class source, CtClass target) {
        try {
            for(Method method : source.getDeclaredMethods()) {
                CtMethod ctMethod = classPool.getMethod(method.getDeclaringClass().getName(), method.getName());
                target.addMethod(CtNewMethod.copy(ctMethod, target,null));
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Adds specific methods to a target <code>CtClass</code>.
     *
     * @param target the target <code>CtClass</code>.
     */
    public void addCandidateMethods(CtClass target) {
        try {
            Map<Functionality, Method> methodMap = getAnalysisStore().getCandidateMethods().get(requirement);

            for(Map.Entry<Functionality, Method> methodEntry : methodMap.entrySet()) {
                Method method = methodEntry.getValue();

                CtMethod ctMethod = classPool.getMethod(method.getDeclaringClass().getName(), method.getName());
                target.addMethod(CtNewMethod.copy(ctMethod, target, null));
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Creates a <code>String</code> with the class invocation for each class contained
     * in the list provided.
     *
     * @param ctClasses the set of <code>CtClass</code> instances.
     * @return the <code>String</code> with the class invocations.
     */
    protected String createClassInvocations(Vector<CtClass> ctClasses) {
        String invocationsBody = "";

        for(CtClass c : ctClasses)
            invocationsBody += c.getName() + " " + c.getSimpleName().toLowerCase() + " = new " + c.getName() + "();\n";

        return invocationsBody;
    }

    /**
     * Creates a wrapper method <code>String</code> representation.
     *
     * @param ctMethod the <code>CtMethod</code> to invoke.
     * @param inputParameters the method input parameters.
     * @param outputParameters the output parameters.
     * @return the <code>String</code> representation of wrapper method.
     */
    protected String createWrapperMethod(CtMethod ctMethod, String inputParameters, String outputParameters) {
        String wrapperMethod = "";

        try {
            if(Modifier.isStatic(ctMethod.getModifiers())) {
                wrapperMethod = "public void " + ctMethod.getName() + "(" + inputParameters + "){" +
                        ctMethod.getDeclaringClass().getName() + "." + ctMethod.getName() + "(" + outputParameters + ");" +
                        "}";
            }
            else {
                wrapperMethod = "public void " + ctMethod.getName() + "(" + inputParameters + "){" +
                        ctMethod.getDeclaringClass().getName() + " obj = new " + ctMethod.getDeclaringClass().getName() + "();" +
                        "obj." + ctMethod.getName() + "(" + outputParameters + ");" +
                        "}";
            }
        }
        catch (Exception e) {
        }

        return wrapperMethod;
    }

    /**
     * Creates a wrapper method <code>String</code> representation.
     *
     * @param method the <code>Method</code> to invoke.
     * @param inputParameters the method input parameters.
     * @param outputParameters the output parameters.
     * @return the <code>String</code> representation of wrapper method.
     */
    protected String createWrapperMethod(Method method, String inputParameters, String outputParameters) {
        String wrapperMethod = "";

        try {
            if(Modifier.isStatic(method.getModifiers())) {
                wrapperMethod = "public void " + method.getName() + "(" + inputParameters + "){" +
                        method.getDeclaringClass().getName() + "." + method.getName() + "(" + outputParameters + ");" +
                        "}";
            }
            else {
                wrapperMethod = "public void " + method.getName() + "(" + inputParameters + "){" +
                        method.getDeclaringClass().getName() + " obj = new " + method.getDeclaringClass().getName() + "();" +
                        "obj." + method.getName() + "(" + outputParameters + ");" +
                        "}";
            }
        }
        catch (Exception e) {
        }

        return wrapperMethod;
    }

    /**
     * Compares two methods to know if they are the equals.
     *
     * @param source the source method.
     * @param target the target method.
     * @return <code>true</code> if the methods are equals; <code>false</code> otherwise.
     */
    protected boolean areEquals(Method source, Method target) {
        boolean flag = false;

        if(source.getDeclaringClass().getSimpleName().equals(target.getDeclaringClass().getSimpleName())) {
            if(source.getName().equals(target.getName()) || source.getName().contains("aroundBody") || source.getName().contains("ajc$"))
                flag = true;
            else
                flag = false;
        }

        return flag;
    }

    /**
     * Compares a <code>CtMethod</code> to another <code>Method</code> to know if they
     * are equals.
     *
     * @param source the source method.
     * @param target the target method.
     * @return <code>true</code> if the methods are equals; <code>false</code> otherwise.
     */
    protected boolean areEquals(CtMethod source, Method target) {
        boolean flag = false;

        if(source != null && target != null) {
            if (source.getDeclaringClass().getSimpleName().equals(target.getDeclaringClass().getSimpleName())) {
                if (source.getName().equals(target.getName()) || source.getName().contains("aroundBody") || source.getName().contains("ajc$"))
                    flag = true;
                else
                    flag = false;
            }
        }

        return flag;
    }

    /**
     * Gets the class file with a main method from a set of <code>CtClass</code> files.
     *
     * @param ctClasses the set of <code>CtClass</code> files.
     * @return the <code>CtClass</code> file that contains the main method.
     */
    public static CtClass getMainClass(Vector<CtClass> ctClasses) {
        CtClass ctMainClass = null;

        try {
            for(CtClass ctClass : ctClasses) {
                for(CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                    if(ctMethod.getName().equals("main")) {
                        ctMainClass = ctClass;
                        break;
                    }
                }

                if(ctMainClass != null)
                    break;
            }
        }
        catch (Exception e) {
        }

        return ctMainClass;
    }

    /**
     * Sets a private constructor to public.
     *
     * @param ctClass the <code>CtClass</code> file that contains the private constructor.
     */
    public void toPublicConstructor(CtClass ctClass) {
        try {
            for (CtConstructor ctConstructor : ctClass.getDeclaredConstructors())
                ctConstructor.setModifiers(1);
        }
        catch (Exception e) {
        }
    }

    /**
     * Generates class files from a set of <code>CtClasses</code>.
     *
     * @param ctClasses the set of <code>CtClass</code> files.
     */
    public static void writeClasses(Vector<CtClass> ctClasses) {
        try {
            for(CtClass ctClass : ctClasses) {
                ctClass.writeFile();
            }
        }
        catch (Exception e) {
        }
    }
}