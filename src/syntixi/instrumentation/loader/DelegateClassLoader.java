package syntixi.instrumentation.loader;

import syntixi.util.func.F1;

/**
 * The <code>DelegateClassLoader</code> class delegates the class load job to the parent
 * class loader.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see DynamicClassLoader
 */
public class DelegateClassLoader extends DynamicClassLoader {

	/**
	 * The <code>F1</code> instance.
	 */
	private F1<String, Boolean> except;

	/**
	 * Delegates the class loading to the <code>DynamicClassLoader</code> class.
	 *
	 * @param except the <code>F1</code> instance.
	 * @param paths the class paths.
	 */
	public DelegateClassLoader(F1<String, Boolean> except, String... paths) {
		super(paths);
		this.except = except;
	}

	@Override
	protected byte[] loadNewClass(String name) {
		if(except.execute(name))
			return null;

		return super.loadNewClass(name);
	}
}