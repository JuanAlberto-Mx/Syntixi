package syntixi.fusion.aop;

import syntixi.util.bean.Checklist;
import syntixi.util.bean.Requirement;

/**
 * <code>AnalysisAspect</code> encapsulates all output messages corresponding to the
 * <code>Sýntixi</code>'s component analysis mechanism.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version 1.0, 31 Jan 2016
 */
public aspect AnalysisAspect {

    pointcut execBasicAnalysis(Requirement requirement, Checklist checklist): execution(public void syntixi.fusion.core.analysis.ComponentAnalyzer.basicAnalysis(Requirement, Checklist)) && args(requirement, checklist);

    pointcut execGetComponentAnnotation(Class cls): execution(* syntixi.util.reflect.ComponentExplorer.getComponentAnnotation(java.lang.Class)) && args(cls);

    pointcut callHasSimilarGoal(): call(* *.hasSimilarGoal(..));

    pointcut callContainsKeyword(): call(* *.containsKeyword(..));

    pointcut callHasCompatibleParameters(): call(* *.hasCompatibleParameters(..));

    pointcut execAnalyzeApi(): execution(* *.analyzeAPI(..));

    //pointcut execSearchCompatibleMethod(String keyword): execution(* agent.JVMExplorer.searchCompatibleMethod(String)) && args(keyword);

    before(Requirement requirement, Checklist checklist): execBasicAnalysis(requirement, checklist) {
        System.out.println("\tRequirement:\t" + requirement.getDescription().getName());
    }

    before(Class cls): execGetComponentAnnotation(cls) {
        System.out.println("\t\t\tFusionable Component:\t" + cls.getName());
    }

    before(): execAnalyzeApi() {
        System.out.println("\tAnalyzing JAVA API");
    }

    /*before(String keyword): execSearchCompatibleMethod(keyword) {
        System.out.println("\t\tFunctionality:\t" + keyword);
    }*/

    after() returning(Boolean answer):callHasSimilarGoal() {
        if(answer)
            System.out.println("\t\t\tSimilar goal:\tTrue");
        else
            System.out.println("\t\t\tSimilar goal:\tFalse");
    }

    after() returning(Boolean answer):callContainsKeyword() {
        if(answer)
            System.out.println("\t\t\tSimilar keywords:\tTrue");
        else
            System.out.println("\t\t\tSimilar keywords:\tFalse");
    }

    after() returning(Boolean answer):callHasCompatibleParameters() {
        if(answer)
            System.out.println("\t\t\tCompatible parameters:\tTrue");
        else
            System.out.println("\t\t\tCompatible parameters:\tFalse");
    }
}