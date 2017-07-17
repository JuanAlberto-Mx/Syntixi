package syntixi.instrumentation.dytecode.compiler;

import com.google.common.collect.ImmutableMap;
import syntixi.instrumentation.dytecode.beans.CompiledClassList;
import syntixi.instrumentation.dytecode.beans.JavaFileObjectFromString;
import syntixi.instrumentation.dytecode.beans.MemoryBytecodeManager;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <code>DytecodeCompiler</code> uses the <code>JavaCompiler</code> with custom implementations
 * of <code>JavaFileManager</code> and <code>JavaFileObject</code> classes to compile a source
 * code from a <code>String</code> representation to <code>bytecode</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see JavaFileManager
 * @see JavaFileObject
 */
public class DytecodeCompiler {

	/**
	 * Compiles a class from a <code>String</code> representation.
	 *
	 * @param className the class name.
	 * @param code the source code.
	 * @return the set of classes encapsulated in a <code>CompiledClassList</code> instance.
	 * @throws Exception
	 */
	public CompiledClassList compile(String className, String code) throws Exception {
		return compile(ImmutableMap.<String, String>builder().put(className, code).build());
	}

	/**
	 * Compiles a set of classes stored in a <code>Map</code> instance.
	 *
	 * @param classes the <code>Map</code> formed by <code>(className, classSource)</code> pairs.
	 * @return the set of classes stored in a <code>CompiledClassList</code> class instance.
	 * @throws Exception
	 */
	public CompiledClassList compile(Map<String, String> classes) throws Exception {
		JavaCompiler javaCompiler = getSystemJavaCompiler();

		DiagnosticCollector<JavaFileObject> diagnosticCollector = getDiagnosticCollector();

		MemoryBytecodeManager bytecodeManager = getClassManager(javaCompiler);

		String classpath = getClasspath();

		List<String> classpathOptions = Arrays.asList("-classpath", classpath);

		List<JavaFileObjectFromString> stringSources = new ArrayList<>();

		for(String className : classes.keySet()) {
			String classCode = classes.get(className);
			stringSources.add(new JavaFileObjectFromString(className, classCode));
		}

		CompilationTask compilationTask = javaCompiler.getTask(null, bytecodeManager, diagnosticCollector, classpathOptions, null, stringSources);

		boolean compilationStatus = compilationTask.call();

		if(compilationStatus)
			return new CompiledClassList(bytecodeManager.getAllClasses());
		else
			throw new Exception("");
	}

	/**
	 * Gets a <code>DiagnosticCollector</code> instance.
	 *
	 * @return the <code>DiagnosticCollector</code> instance.
	 */
	public DiagnosticCollector<JavaFileObject> getDiagnosticCollector() {
		return new DiagnosticCollector<>();
	}

	/**
	 * Gets a <code>MemoryBytecodeManager</code> instance.
	 *
	 * @param javaCompiler the <code>JavaCompiler</code> instance.
	 * @return the <code>MemoryBytecodeManager</code> instance.
	 */
	public MemoryBytecodeManager getClassManager(JavaCompiler javaCompiler) {
		return new MemoryBytecodeManager(javaCompiler.getStandardFileManager(null, null, null));
	}

	/**
	 * Gets a <code>JavaCompiler</code> instance.
	 *
	 * @return the <code>JavaCompiler</code> instance.
	 */
	public JavaCompiler getSystemJavaCompiler() {
		return ToolProvider.getSystemJavaCompiler();
	}

	/**
	 * Returns the full classpath.
	 *
	 * @return the full classpath.
	 */
	public String getClasspath() {
		StringBuilder stringBuilder = new StringBuilder();
		URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();

		for(URL url : urlClassLoader.getURLs())
			stringBuilder.append(url.getFile()).append(System.getProperty("path.separator"));

		return stringBuilder.toString();
	}
}