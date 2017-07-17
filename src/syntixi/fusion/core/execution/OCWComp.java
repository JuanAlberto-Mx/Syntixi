package syntixi.fusion.core.execution;

import javassist.CtClass;

import java.util.Vector;

/**
 * <code>OCWComp</code> allows modifying components running in the <code>JVM</code>
 * and communicate them without using connectors.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class OCWComp extends OComponent {

    @Override
    public void build(Vector<CtClass> ctClasses) {

        try{
            //Ver la forma de recuperar los class para realizar las operaciones ya que la lista de readyclasses esta vacia
            /*CtClass ctMainClass = ComponentBuilder.getMainClass(ctClasses);

            if(!ctClasses.isEmpty()) {

                ComponentBuilder.writeClasses(ctClasses);

                Class loadedClass = new Proxy().load(null, ctMainClass);

                Method method = loadedClass.getDeclaredMethod("main", String[].class);

                ReflectUtil.invoke(method, loadedClass, (Object)new String[]{null});
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}