package syntixi.fusion.core.execution;

import javassist.CtClass;
import syntixi.fusion.Actuator;
import syntixi.util.inst.ComponentBuilder;

import java.util.Vector;

/**
 * <code>OCComp</code> allows modifying components running in the <code>JVM</code>
 * and generate new connectors to communicate them.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class OCComp extends OComponent {

    @Override
    public void build(Vector<CtClass> ctClasses) {
        try{
            if(!ctClasses.isEmpty()) {
                ComponentBuilder.writeClasses(ctClasses);

                CtClass ctMainClass = getMainClass(ctClasses);

                Actuator.setCtClass(ctMainClass);
                Actuator.start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the main class from a list of classes.
     *
     * @param ctClasses the list of classes.
     * @return the <code>CtClass</code> corresponding to the main class.
     */
    private CtClass getMainClass(Vector<CtClass> ctClasses) {
        CtClass mainClass = null;

        for(CtClass cls : ctClasses) {
            if(cls.getSimpleName().equals("MainClass")) {
                mainClass = cls;
                break;
            }
        }

        return mainClass;
    }
}