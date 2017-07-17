package syntixi.fusion.aop;

/**
 * <code>PlanningAspect</code> encapsulates all output messages corresponding to the
 * <code>Sýntixi</code>'s fusion strategies.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version 1.0, 31 Jan 2016
 */
public aspect PlanningAspect {

    pointcut execGCC(): execution(public syntixi.fusion.core.planning.GCC.new());

    pointcut execGCWC1(): execution(public syntixi.fusion.core.planning.GCWC1.new());

    pointcut execGCWC2(): execution(public syntixi.fusion.core.planning.GCWC2.new());

    pointcut execOCC(): execution(public syntixi.fusion.core.planning.OCC.new());

    pointcut execOCWC(): execution(public syntixi.fusion.core.planning.OCWC.new());

    before(): execGCC() {
        System.out.println("\tGCC strategy:\tTrue");
    }

    before(): execGCWC1() {
        System.out.println("\tGCWC1 strategy:\tTrue");
    }

    before(): execGCWC2() {
        System.out.println("\tGCWC2 strategy:\tTrue");
    }

    before(): execOCC() {
        System.out.println("\tOCC strategy:\tTrue");
    }

    before(): execOCWC() {
        System.out.println("\tOCWC strategy:\tTrue");
    }
}