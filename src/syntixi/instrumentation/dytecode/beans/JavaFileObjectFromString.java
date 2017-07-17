package syntixi.instrumentation.dytecode.beans;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * <code>JavaFileObjectFromString</code> class is responsible for creating a
 * <code>Java</code> file object from a <code>String</code> data type.
 * <p>
 * A <code>JavaFileObjectFromString</code> instance encapsulates the source code of a
 * <code>Java</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see SimpleJavaFileObject
 */
public class JavaFileObjectFromString extends SimpleJavaFileObject {

	/**
	 * The source code representation as a <code>String</code> data type.
	 */
	private final String sourceCode;

	/**
	 * The class name.
	 */
	private final String className;

	/**
	 * Initializes a <code>Java</code> file object.
	 *
	 * @param className the class name.
	 */
	public JavaFileObjectFromString(String className) {
		super(URI.create("mem:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
		this.className = className;
		this.sourceCode = null;
	}

	/**
	 * Creates a <code>Java</code> file object from a <code>String</code> data type.
	 *
	 * @param className the class name.
	 * @param sourceCode the <code>Java</code> source code.
	 */
	public JavaFileObjectFromString(String className, String sourceCode) {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.className = className;
		this.sourceCode = sourceCode;
	}

	@Override
	public final CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return sourceCode;
	}

	@Override
	public final String getName() {
		return className;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		JavaFileObjectFromString objectFromString = (JavaFileObjectFromString) object;

		return Util.equal(sourceCode, objectFromString.sourceCode) && Util.equal(className, objectFromString.className);
	}

	@Override
	public int hashCode() {
		return Util.hashCode(sourceCode, className);
	}
}