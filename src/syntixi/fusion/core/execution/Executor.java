package syntixi.fusion.core.execution;

import javassist.CtClass;
import syntixi.fusion.Mapek;

import java.util.Vector;

import static syntixi.fusion.core.knowledge.store.PlanningStore.getPlanningStore;

/**
 * <code>Executor</code> class encapsulates the functionality of a requirement dispatcher.
 * This class allows determining the suitable factory to fuse the components dynamically.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Executor implements Mapek {

    /**
     * The component factory instance.
     */
    AbstractFactory componentFactory = new ComponentFactory();

    @Override
    public void init() {
        dispatcher();
    }

    /**
     * Dispatches all user requirements detected.
     */
    public void dispatcher() {
        getPlanningStore().getReadyComponents().forEach((requirement, ctClasses) -> {
            String alternative = requirement.getAlternative().getAlternative().trim().toUpperCase();

            determineFactory(alternative, ctClasses);
        });
    }

    /**
     * Determines the suitable factory to fuse the components.
     *
     * @param alternative the alternative selected.
     * @param ctClasses the classes to fuse.
     */
    public void determineFactory(String alternative, Vector<CtClass> ctClasses) {
        if(alternative.equals("OCC") || alternative.equals("OCWC")) {
            OComponent oComponent = componentFactory.buildOComponent(alternative);
            oComponent.build(ctClasses);
        }
        else if(alternative.equals("GCC") || alternative.equals("GCWC1") || alternative.equals("GCWC2")) {
            GComponent gComponent = componentFactory.buildGComponent(alternative);
            gComponent.build(ctClasses);
        }
    }
}