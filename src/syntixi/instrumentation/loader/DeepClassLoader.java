package syntixi.instrumentation.loader;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>DeepClassLoader</code> loads all the classes as possible and leaves the
 * remaining classes to <code>ClassLoader</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see ClassLoader
 */
public abstract class DeepClassLoader extends ClassLoader {

	/**
	 * The <code>HashSet</code> to store loaded classes.
	 */
	Set<String> loadedClasses = new HashSet<>();

	/**
	 * The <code>HashSet</code> to store unavailable classes.
	 */
	Set<String> unavailableClasses = new HashSet<>();

	/**
	 * The <code>ClassLoader</code> instance to set the <code>DeepClassLoader</code>
	 * parent.
	 */
	private ClassLoader parentLoader = DeepClassLoader.class.getClassLoader();

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (loadedClasses.contains(name) || unavailableClasses.contains(name))
			return super.loadClass(name);

		byte[] newClassData = loadNewClass(name);

		if(newClassData != null) {
			loadedClasses.add(name);

			return loadClass(newClassData, name);
		}
		else {
			unavailableClasses.add(name);

			return parentLoader.loadClass(name);
		}
	}

	/**
	 * Load a class by specifying the name.
	 *
	 * @param name the class name.
	 * @return the class loaded.
	 */
	public Class<?> load(String name){
		try {
			return loadClass(name);
		}
		catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads a new class by specifying the name.
	 *
	 * @param name the class name.
	 * @return the <code>byte</code> array of the class.
	 */
	protected abstract byte[] loadNewClass(String name);

	/**
	 * Loads a class file.
	 *
	 * @param classData the <code>byte</code> array of the class to be loaded.
	 * @param name the class name.
	 * @return the class loaded.
	 */
	public Class<?> loadClass(byte[] classData, String name) {
		Class<?> cls = defineClass(name, classData, 0, classData.length);

		if(cls != null){
			if (cls.getPackage() == null)
				definePackage(name.replaceAll("\\.\\w+$", ""), null, null, null, null, null, null, null);

			resolveClass(cls);
		}

		return cls;
	}

	/**
	 * Gets a file path for a specific class name.
	 *
	 * @param name the class name.
	 * @return the file path.
	 */
	public static String toFilePath(String name) {
		return name.replaceAll("\\.", "/") + ".class";
	}
}