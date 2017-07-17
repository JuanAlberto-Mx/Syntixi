package syntixi.instrumentation.dytecode.beans;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;

/**
 * <code>JavaMemoryObject</code> class wraps bytecode into memory. This class extends
 * the <code>StandardFileManager</code> class functionality to read and write bytecode
 * into memory instead of class files.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see JavaFileObject
 */
public class JavaMemoryObject implements JavaFileObject {

	/**
	 * The byte array output stream.
	 */
	private ByteArrayOutputStream byteArrayOutputStream;

	/**
	 * The URI of the class.
	 */
	private URI uri;

	/**
	 * The kind of file.
	 */
	private Kind kind;

	/**
	 * Initializes the <code>Java</code> memory object.
	 *
	 * @param fileName the file name.
	 * @param fileKind the kind of file.
	 */
	public JavaMemoryObject(String fileName, Kind fileKind) {
		this.uri = URI.create("string:///" + fileName.replace('.', '/') + fileKind.extension);
		this.kind = fileKind;
		this.byteArrayOutputStream = new ByteArrayOutputStream();
	}

	/**
	 * Returns the bytes corresponding to a <code>Java</code> class.
	 *
	 * @return the class bytes.
	 */
	public byte[] getClassBytes() {
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public final URI toUri() {
		return uri;
	}

	@Override
	public final String getName() {
		return uri.getPath();
	}

	@Override
	public final InputStream openInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final OutputStream openOutputStream() throws IOException {
		return byteArrayOutputStream;
	}

	@Override
	public final Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Writer openWriter() throws IOException {
		return new OutputStreamWriter(openOutputStream());
	}

	@Override
	public final long getLastModified() {
		return 0L;
	}

	@Override
	public final boolean delete() {
		return false;
	}

	@Override
	public final Kind getKind() {
		return kind;
	}

	@Override
	public final boolean isNameCompatible(String simpleName, Kind fileKind) {
		String baseName = simpleName + kind.extension;

		return fileKind.equals(getKind()) && (baseName.equals(toUri().getPath()) || toUri().getPath().endsWith("/" + baseName));
	}

	@Override
	public final NestingKind getNestingKind() {
		return null;
	}

	@Override
	public final Modifier getAccessLevel() {
		return null;
	}

	@Override
	public final String toString() {
		return getClass().getName() + "[" + toUri() + "]";
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		JavaMemoryObject javaMemoryObject = (JavaMemoryObject) object;

		return Util.equal(uri, javaMemoryObject.uri) && Util.equal(kind, javaMemoryObject.kind);
	}

	@Override
	public int hashCode() {
		return Util.hashCode(uri, kind);
	}
}