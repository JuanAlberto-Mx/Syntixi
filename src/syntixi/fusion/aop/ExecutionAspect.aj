package syntixi.fusion.aop;

/**
 * <code>ExecutionAspect</code> encapsulates all output messages of the execution stage.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version 1.0, 31 Jan 2016
 */
public aspect ExecutionAspect {

    pointcut execGCC(): execution(* syntixi.fusion.core.execution.GComponent.build(*)) &&
            within(syntixi.fusion.core.execution.GCComp);

    pointcut execGCWC1(): execution(* syntixi.fusion.core.execution.GComponent.build(*)) &&
            within(syntixi.fusion.core.execution.GCWComp1);

    pointcut execGCWC2(): execution(* syntixi.fusion.core.execution.GComponent.build(*)) &&
            within(syntixi.fusion.core.execution.GCWComp2);

    pointcut execOCC(): execution(* syntixi.fusion.core.execution.OComponent.build(*)) &&
            within(syntixi.fusion.core.execution.OCComp);

    pointcut execOCWC(): execution(* syntixi.fusion.core.execution.OComponent.build(*)) &&
            within(syntixi.fusion.core.execution.OCWComp);

    before():execGCC() {
        System.out.println("\tCreating GCC component");
    }

    before():execGCWC1() {
        System.out.println("\tCreating GCWC1 component");
    }

    before():execGCWC2() {
        System.out.println("\tCreating GCWC2 component");
    }

    before():execOCC() {
        System.out.println("\tCreating OCC component");
    }

    before():execOCWC() {
        System.out.println("\tCreating OCWC component");
    }
}