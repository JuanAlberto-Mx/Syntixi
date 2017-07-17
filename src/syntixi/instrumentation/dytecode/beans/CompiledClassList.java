package syntixi.instrumentation.dytecode.beans;

import java.util.List;

/**
 * <code>CompiledClassList</code> class represents a set of compiled classes and allows
 * all of them to be loaded as a package if needed.
 * <p>
 * <code>CompiledClassList</code> class is useful to deal with inner classes and inner
 * interfaces.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class CompiledClassList {

	/**
	 * The list of compiled classes.
	 */
	private final List<CompiledClass> compiledClasses;

	/**
	 * Initializes a list of <code>CompiledClass</code> instances.
	 *
	 * @param compiledClasses the list of compiled classes.
	 */
	public CompiledClassList(List<CompiledClass> compiledClasses) {
		this.compiledClasses = compiledClasses;
	}

	/**
	 * Returns the list of compiled classes.
	 *
	 * @return the list of compiled classes.
	 */
	public List<CompiledClass> getCompiledClasses() {
		return compiledClasses;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;

		if(object == null || getClass() != object.getClass())
			return false;

		CompiledClassList compiledClassList = (CompiledClassList) object;

		return Util.equal(compiledClasses, compiledClassList.compiledClasses);
	}

	@Override
	public int hashCode() {
		return Util.hashCode(compiledClasses);
	}
}