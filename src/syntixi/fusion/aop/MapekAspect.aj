package syntixi.fusion.aop;

/**
 * <code>MapekAspect</code> encapsulates the calls to class constructors for each class of
 * the <code>MAPE-K</code> Finite State Machine as well as <code>MapekTemplate</code> class
 * method invocations.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version 1.0, 31 Jan 2016
 */
public aspect MapekAspect {

    private static String[] signs = {"|", "/", "─", "\\"};
    private static int pivot = 0;

    pointcut initMonitoring(): execution(public syntixi.fusion.MonitoringFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.monitoring());

    pointcut initAnalysis(): execution(public syntixi.fusion.AnalysisFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.analysis());

    pointcut initPlanning(): execution(public syntixi.fusion.PlanningFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.planning());

    pointcut initExecution(): execution(public syntixi.fusion.ExecutionFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.execution());

    pointcut workingMonitoring(): execution(void syntixi.fusion.State.working()) &&
            within(syntixi.fusion.MonitoringFSM);

    pointcut workingAnalysis(): execution(void syntixi.fusion.State.working()) &&
            within(syntixi.fusion.AnalysisFSM);

    pointcut workingPlanning(): execution(void syntixi.fusion.State.working()) &&
            within(syntixi.fusion.PlanningFSM);

    pointcut workingExecution(): execution(void syntixi.fusion.State.working()) &&
            within(syntixi.fusion.ExecutionFSM);

    before(): initMonitoring() {
        System.out.println("[MAPE-K]\tMonitoring stage initialized");
    }

    before(): initAnalysis() {
        System.out.println("[MAPE-K]\tAnalysis stage initialized");
    }

    before(): initPlanning() {
        System.out.println("[MAPE-K]\tPlanning stage initialized");
    }

    before(): initExecution() {
        System.out.println("[MAPE-K]\tExecution stage initialized");
    }

    before(): workingMonitoring() {
        System.out.print("[Monitoring]\t" + getSign());
    }

    before(): workingAnalysis() {
        System.out.println("[Analysis]\t Started");
    }

    before(): workingPlanning() {
        System.out.println("[Planning]\t Started");
    }

    before(): workingExecution() {
        System.out.println("[Execution]\t Started");
    }

    private String getSign() {
        String sign = signs[pivot] + "\r";

        pivot++;

        if(pivot == 4)
            pivot = 0;

        return sign;
    }
}