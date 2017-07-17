package syntixi.fusion.aop;

import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>MonitoringAspect</code> prints the list of components and requirements existing
 * in the <code>Sýntixi</code> workspace.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public aspect MonitoringAspect {

    pointcut componentUpdate(): execution(void syntixi.fusion.core.monitoring.Observer.update()) &&
            within(syntixi.fusion.core.monitoring.ComponentObserver);

    pointcut requirementUpdate(): execution(void syntixi.fusion.core.monitoring.Observer.update()) &&
            within(syntixi.fusion.core.monitoring.RequirementObserver);

    after(): componentUpdate() {
        getMonitoringStore().printComponents();
    }

    after(): requirementUpdate() {
        getMonitoringStore().printRequirements();
    }
}