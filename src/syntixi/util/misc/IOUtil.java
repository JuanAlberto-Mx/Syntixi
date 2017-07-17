package syntixi.util.misc;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The <code>IOUtil</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class IOUtil {

	/**
	 * Reads the stream into byte array.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @return the byte array of the input stream.
	 * @throws IOException
	 */
	public static byte[] readData(InputStream inputStream) {
		try {
			return readDataNice(inputStream);
		}
		finally {
			close(inputStream);
		}
	}

	/**
	 * Reads an <code>InputStream</code> into byte array.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @return the byte array of the input stream.
	 */
	private static byte[] readDataNice(InputStream inputStream) {
		ByteArrayOutputStream temporal = null;

		byte[] buffer = null;

		try {
			int read;
			buffer = new byte[8192];
			temporal = new ByteArrayOutputStream();

			while((read = inputStream.read(buffer, 0, 8192)) > -1)
				temporal.write(buffer, 0, read);

			return temporal.toByteArray();
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes streams <code>in</code> or <code>out</code>.
	 *
	 * @param stream the <code>Closeable</code> instance.
	 */
	public static void close(Closeable stream) {
		if(stream != null) {
			try {
				if(stream instanceof Flushable)
					((Flushable)stream).flush();

				stream.close();
			}
			catch(IOException e) {
				e.getMessage();
			}
		}
	}

	/**
	 * Closes an active <code>Connection</code> instance.
	 *
	 * @param connection the <code>Connection</code> instance.
	 */
	public static void close(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			}
			catch(SQLException e) {
			}
		}
	}

	/**
	 * Closes a specific <code>ResultSet</code>.
	 *
	 * @param resultSet the <code>ResultSet</code> instance.
	 */
	public static void close(ResultSet resultSet) {
		if(resultSet != null) {
			try {
				resultSet.close();
			}
			catch(SQLException e) {
			}
		}
	}

	/**
	 * Closes a <code>PreparedStatement</code> instance.
	 *
	 * @param preparedStatement the <code>PreparedStatement</code> instance.
	 */
	public static void close(PreparedStatement preparedStatement) {
		if(preparedStatement != null) {
			try {
				preparedStatement.close();
			}
			catch(SQLException e) {
			}
		}
	}

	/**
	 * Closes a stream.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @param charSet a charSet.
	 * @return the <code>String</code> result.
	 */
	public static String toString(InputStream inputStream, String charSet) {
		return inputStreamToString_force(inputStream, charSet);
	}

	/**
	 * Closes a stream.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @param charSet a charSet.
	 * @return the resulting <code>String</code>.
	 */
	public static String inputStreamToString_force(InputStream inputStream, String charSet) {
		try {
			return inputStreamToString(inputStream, charSet);
		}
		catch(IOException e) {
			return null;
		}
	}

	/**
	 * Reads in whole input stream and returns as a <code>String</code>.
	 *
	 * @param inputStream the input stream to read in. It will be closed at the finish.
	 * @param charSet the charset to convert the input bytes into <code>String</code>.
	 * @return the resulting <code>String</code>.
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream inputStream, String charSet) throws IOException {
		InputStreamReader inputStreamReader = null;

		try {
			inputStreamReader = (charSet == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, charSet));

			return toString(inputStreamReader);
		}
		catch(UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}
		finally {
			close(inputStream);
		}
	}

	/**
	 * Reads in whole input stream and returns as a <code>String</code>.
	 *
	 * @param reader the input reader to read in. It will be closed at the finish.
	 * @return the resulting <code>String</code>.
	 * @throws IOException
	 */
	public static String toString(Reader reader) {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			char[] buffer = new char[4096];

			for(int read; (read = reader.read(buffer)) > -1;) {
				stringBuilder.append(buffer, 0, read);
			}

			return stringBuilder.toString();
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			close(reader);
		}
	}

	/**
	 * Read the input stream and write to output stream.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @param outputStream the <code>OutputStream</code> instance.
	 * @return the <code>long</code> representation.
	 * @throws IOException
	 */
	public static long connect(InputStream inputStream, OutputStream outputStream) throws IOException {
		try {
			return dump(inputStream, outputStream);
		}
		finally {
			close(inputStream);
		}
	}

	/**
	 * The dump method.
	 *
	 * @param inputStream the <code>InputStream</code> instance.
	 * @param outputStream the <code>OutputStream</code> instance.
	 * @return the <code>long</code> representation.
	 * @throws IOException
	 */
	private static long dump(InputStream inputStream, OutputStream outputStream) throws IOException {
		long total = 0;
		int read;
		int size = 8192;
		byte[] buffer = new byte[size];

		while((read = inputStream.read(buffer, 0, size)) > -1) {
			outputStream.write(buffer, 0, read);
			total += read;
		}

		outputStream.flush();

		return total;
	}
}