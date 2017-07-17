package syntixi.instrumentation.dytecode.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <code>Util</code> class contains a set of methods to compare objects and retrieve
 * <code>Java</code> files from a specific directory.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class Util {

    /**
     * Private constructor for avoiding new class instances.
     */
    private Util(){
    }

    /**
     * Compares two objects.
     *
     * @param obj1 the first object.
     * @param obj2 the second object.
     * @return <code>True</code> if the objects are equals; <code>false</code> otherwise.
     */
    public static boolean equal(Object obj1, Object obj2){
        return obj1 == obj2 || obj1 != null && obj1.equals(obj2);
    }

    /**
     * Gets the hash code.
     *
     * @param objects the set of objects.
     * @return the hash code.
     */
    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }

    /**
     * Gets a set of <code>Java</code> files contained in a specific directory.
     *
     * @param directory the directory which contains <code>Java</code> files.
     * @return a list of <code>Java</code> files.
     * @throws IOException
     */
    public static List<File> getJavaFiles(String directory) throws IOException {
        return getJavaFiles(directory, new ArrayList<>());
    }

    /**
     * Gets a set of <code>Java</code> files contained in a specific directory.
     *
     * @param directory the directory which contains <code>Java</code> files.
     * @param listFiles the empty <code>List</code> which will contain the resulting
     *                 <code>Java</code> files.
     * @return a list of <code>Java</code> files.
     * @throws IOException
     */
    private static List<File> getJavaFiles(String directory, List<File> listFiles) throws IOException {
        Files.newDirectoryStream(Paths.get(directory)).forEach(file -> {
            try {
                if(file.toFile().isDirectory())
                    getJavaFiles(file.toString(), listFiles);
                else if (file.toFile().getName().endsWith(".java"))
                    listFiles.add(file.toFile());
            }
            catch (IOException e){
            }
        });

        return listFiles;
    }
}