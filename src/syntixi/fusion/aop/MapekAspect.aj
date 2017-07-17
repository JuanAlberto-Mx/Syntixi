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

    pointcut initMonitoring(): execution(public syntixi.fusion.MonitoringFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.monitoring());

    pointcut initAnalysis(): execution(public syntixi.fusion.AnalysisFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.analysis());

    pointcut initPlanning(): execution(public syntixi.fusion.PlanningFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.planning());

    pointcut initExecution(): execution(public syntixi.fusion.ExecutionFSM.new()) ||
            execution(void syntixi.fusion.MapekTemplate.execution());

    before(): initMonitoring() {
        System.out.println("Initializing Monitoring Stage");
    }

    before(): initAnalysis() {
        System.out.println("Initializing Analysis Stage");
    }

    before(): initPlanning() {
        System.out.println("Initializing Planning Stage");
    }

    before(): initExecution() {
        System.out.println("Initializing Execution Stage");
    }
}