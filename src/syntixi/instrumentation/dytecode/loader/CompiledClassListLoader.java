package syntixi.instrumentation.dytecode.loader;

import com.google.common.base.Function;
import syntixi.instrumentation.dytecode.beans.CompiledClass;
import syntixi.instrumentation.dytecode.beans.CompiledClassList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.uniqueIndex;

/**
 * Loads a source code into a list of <code>Class</code> instances.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class CompiledClassListLoader {

    /**
     * Returns the compiled classes.
     *
     * @param compiledClassList the <code>beans.CompiledClassList</code> instance corresponding
     *                         to a set of source code files.
     * @return the list of compiled classes.
     * @throws ClassNotFoundException
     */
    public List<Class<?>> loadAsList(CompiledClassList compiledClassList) throws ClassNotFoundException {
        BytecodeLoader bytecodeLoader = BytecodeLoader.getInstance();
        List<Class<?>> loadedClasses = new ArrayList<>();

        for(CompiledClass compiledClass : compiledClassList.getCompiledClasses()) {
            Class<?> cls = bytecodeLoader.load(compiledClass.getName(), compiledClass.getBytecode());
            loadedClasses.add(cls);
        }

        return loadedClasses;
    }

    /**
     * Loads a set of compiled classes.
     *
     * @param compiledClassList the <code>beans.CompiledClassList</code> containing a set
     *                             of classes.
     * @return the <code>Map</code> of compiled classes indexed by class name.
     * @throws ClassNotFoundException
     */
    public Map<String, Class<?>> loadAsMap(CompiledClassList compiledClassList) throws ClassNotFoundException {
        List<Class<?>> classes = loadAsList(compiledClassList);

        return uniqueIndex(classes, BY_CLASS_NAME);
    }

    /**
     * Returns a parametrized function.
     */
    private static final Function<Class, String> BY_CLASS_NAME = cls -> cls.getName();
}