package syntixi.instrumentation.loader;

import syntixi.util.func.F1;
import syntixi.util.misc.FileUtil;
import syntixi.util.misc.IOUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * <code>DynamicClassLoader</code> class loads classes dynamically in the
 * <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see DeepClassLoader
 */
public class DynamicClassLoader extends DeepClassLoader {

	/**
	 * The list of loaders.
	 */
	LinkedList<F1<String, byte[]>> loaders = new LinkedList<>();

	/**
	 * Initializes the paths for each class to be loaded.
	 *
	 * @param paths the class paths.
	 */
	public DynamicClassLoader(String... paths) {
		for(String path : paths) {
			File file = new File(path);
			F1<String, byte[]> loader = loader(file);

			if(loader == null)
				throw new RuntimeException("Path not exists " + path);

			loaders.add(loader);
		}
	}

	/**
	 * Initializes a collection of file paths corresponding to the classes
	 * to be loaded.
	 *
	 * @param paths the collection of paths.
	 */
	public DynamicClassLoader(Collection<File> paths) {
		for(File file : paths) {
			F1<String, byte[]> loader = loader(file);

			if(loader == null)
				throw new RuntimeException("Path not exists " + file.getPath());

			loaders.add(loader);
		}
	}

	/**
	 * Loads a specific file.
	 *
	 * @param file the file to be loaded.
	 * @return a function with the file path and the class's <code>byte</code> array.
	 */
	public static F1<String, byte[]> loader(File file) {
		if(!file.exists())
			return null;
		else if(file.isDirectory())
			return dirLoader(file);
		else {
			try {
				final JarFile jarFile = new JarFile(file);

				return jarLoader(jarFile);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Finds a specific file.
	 *
	 * @param filePath the file path.
	 * @param classPath the class path.
	 * @return the file found.
	 */
	private static File findFile(String filePath, File classPath) {
		File file = new File(classPath, filePath);

		return (file.exists() ? file : null);
	}

	/**
	 * Loads a specific directory.
	 *
	 * @param classPath the class path.
	 * @return the <code>byte</code> array of the class.
	 */
	public static F1<String, byte[]> dirLoader(final File classPath) {
		return filePath -> {
			File file = findFile(filePath, classPath);

			if(file == null)
				return null;

			return FileUtil.readFileToBytes(file);
		};
	}

	/**
	 * Loads a <code>JAR</code> file.
	 *
	 * @param jarFile the <code>JAR</code> file instance.
	 * @return a <code>F1</code> function.
	 */
	private static F1<String, byte[]> jarLoader(final JarFile jarFile) {
		return new F1<String, byte[]>() {
			public byte[] execute(String filePath) {

				ZipEntry zipEntry = jarFile.getJarEntry(filePath);

				if (zipEntry == null)
					return null;

				try {
					return IOUtil.readData(jarFile.getInputStream(zipEntry));
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			protected void finalize() throws Throwable {
				IOUtil.close(jarFile);
				super.finalize();
			}
		};
	}

	@Override
	protected byte[] loadNewClass(String name) {
		for(F1<String, byte[]> loader : loaders) {
			byte[] data = loader.execute(DeepClassLoader.toFilePath(name));

			if (data!= null)
				return data;
		}

		return null;
	}
}