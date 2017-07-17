package syntixi.instrumentation.dytecode.beans;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>MemoryBytecodeManager</code> class encapsulates a list of compiled classes
 * into {@link #memoryClasses}.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see JavaMemoryObject
 * @see ForwardingJavaFileManager
 */
public class MemoryBytecodeManager extends ForwardingJavaFileManager<JavaFileManager> {

	/**
	 * The list of compiled classes.
	 */
	private List<CompiledClass> memoryClasses = new ArrayList<>();

	/**
	 * Initializes a <code>Java</code> file manager.
	 *
	 * @param javaFileManager the <code>JavaFileManager</code> class instance.
	 */
	public MemoryBytecodeManager(JavaFileManager javaFileManager) {
		super(javaFileManager);
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName)  throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String name, Kind kind, FileObject sibling) throws IOException {
		JavaMemoryObject javaMemoryObject = new JavaMemoryObject(name, kind);

		CompiledClass compiledClass = new CompiledClass(name, javaMemoryObject);
		memoryClasses.add(compiledClass);

		return javaMemoryObject;
	}

	@Override
	public boolean isSameFile(FileObject a, FileObject b) {
		return false;
	}

	/**
	 *
	 * Gets a list of compiled classes.
	 *
	 * @return the list of compiled classes.
	 */
	public List<CompiledClass> getAllClasses() {
		return memoryClasses;
	}
}