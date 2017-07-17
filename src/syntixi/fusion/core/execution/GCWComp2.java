package syntixi.fusion.core.execution;

import javassist.CtClass;
import syntixi.fusion.Actuator;
import syntixi.util.inst.ComponentBuilder;

import java.util.Vector;

/**
 * <code>GCWComp2</code> allows generating new components without connectors.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class GCWComp2 extends GComponent {

    @Override
    public void build(Vector<CtClass> ctClasses) {
        try{
            if(!ctClasses.isEmpty()) {
                CtClass ctMainClass = ComponentBuilder.getMainClass(ctClasses);

                ComponentBuilder.writeClasses(ctClasses);

                Actuator.setCtClass(ctMainClass);
                Actuator.start();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}