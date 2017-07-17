package syntixi.instrumentation.dytecode.loader;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * <code>BytecodeLoader</code> extends the standard class loader to transform an array
 * of bytes into a <code>Class</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see ClassLoader
 */
public class BytecodeLoader extends ClassLoader {

	/**
	 * The array to store the <code>bytecode</code>.
	 */
	private byte[] bytecode;

	/**
	 * Private constructor for avoiding new class instantiations.
	 */
	private BytecodeLoader() {
		super(BytecodeLoader.class.getClassLoader());
	}

	/**
	 * Sets the <code>bytecode</code> for a specific class.
	 *
	 * @param bytecode the byte array corresponding to the <code>bytecode</code>.
	 */
	public void setBytecode(byte[] bytecode) {
		this.bytecode = bytecode;
	}

	/**
	 * Gets the byte array corresponding to the <code>bytecode</code>.
	 *
	 * @return the byte array corresponding to the <code>bytecode</code>.
	 */
	public byte[] getBytecode() {
		return bytecode;
	}

	/**
	 * Returns a <code>Class</code> instance corresponding to a specific class.
	 *
	 * @param className the fully qualified class name.
	 * @param byteCode the <code>bytecode</code> array.
	 * @return the loaded class.
	 * @throws ClassNotFoundException
	 */
	public Class load(String className, byte[] byteCode) throws ClassNotFoundException {
		setBytecode(byteCode.clone());

		return loadClass(className);
	}

	/**
	 * Defines a new <code>Java</code> class.
	 *
	 * @param name the class name.
	 * @param bytecode the byte array corresponding to the <code>bytecode</code>.
	 * @return a new <code>Class</code> instance.
	 */
	public Class<?> defineClass(String name, byte[] bytecode) {
		return defineClass(name, bytecode, 0, bytecode.length);
	}

	/**
	 * Gets a new <code>BytecodeLoader</code> instance.
	 *
	 * @return the <code>BytecodeLoader</code> instance.
	 */
	public static BytecodeLoader getInstance() {
		return AccessController.doPrivileged(new BytecodeLoaderAction());
	}

	@Override
	public Class findClass(String name)	throws ClassNotFoundException {
		Class<?> cls = null;

		try {
			if(bytecode != null)
				cls = defineClass(name, bytecode);
		}
		catch (ClassFormatError ex) {
			throw new ClassNotFoundException("Class name: " + name, ex);
		}

		return cls;
	}

	private static class BytecodeLoaderAction implements PrivilegedAction<BytecodeLoader> {

		@Override
		public BytecodeLoader run() {
			return new BytecodeLoader();
		}
	}
}