package syntixi.instrumentation.dytecode.beans;

/**
 * <code>CompiledClass</code> class represents a <code>Java</code> compiled class which
 * consists of a name and a corresponding <code>bytecode</code>.
 * <p>
 * All <code>CompiledClass</code> instances are stored in a <code>CompiledClassList</code>
 * list.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class CompiledClass {

	/**
	 * The class name.
	 */
	private String name;

	/**
	 * The <code>Java</code> compiled class memory object.
	 */
	private JavaMemoryObject javaMemoryObject;

	/**
	 * Builds a <code>CompiledClass</code> instance given a name and a byte array
	 * containing the bytecode.
	 *
	 * @param name the class name.
	 * @param javaMemoryObject the object in memory.
	 */
	public CompiledClass(String name, JavaMemoryObject javaMemoryObject) {
		this.name = name;
		this.javaMemoryObject = javaMemoryObject;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the bytecode as a byte array.
	 *
	 * @return the byte array corresponding to a compiled class bytecode.
	 */
	public byte[] getBytecode() {
		byte[] byteCode = null;

		if(javaMemoryObject != null)
			byteCode = javaMemoryObject.getClassBytes();

		return byteCode;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;

		if(object == null || getClass() != object.getClass())
			return false;

		CompiledClass compiledClass = (CompiledClass) object;

		return Util.equal(name, compiledClass.name) && Util.equal(javaMemoryObject, compiledClass.javaMemoryObject);
	}

	@Override
	public int hashCode() {
		return Util.hashCode(name, javaMemoryObject);
	}
}