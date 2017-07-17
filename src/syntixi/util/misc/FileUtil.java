package syntixi.util.misc;

import syntixi.util.func.F1;
import syntixi.util.func.F2;
import syntixi.util.func.Functions;
import syntixi.util.func.P2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static syntixi.util.misc.IOUtil.readData;

/**
 * The <code>FileUtil</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class FileUtil {

    /**
     * Reads a file instance and returns a byte array representation.
     *
     * @param file the source file.
     * @return the file's byte array.
     * @throws IOException
     */
    public static byte[] readFileToBytes(File file) {
        try {
            return readData(new FileInputStream(file));
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Ges a file input stream from a specific file.
     *
     * @param file the source file.
     * @return the file input stream.
     */
    public static FileInputStream fileInputStream(File file) {
        try {
            return new FileInputStream(file);
        }
        catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a file input stream from a string file name.
     *
     * @param file the file name.
     * @return the file input stream.
     */
    public static FileInputStream fileInputStream(String file) {
        try {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The eachFile method.
     *
     * @param file the source file.
     * @param function the source function.
     */
    public static void eachFile(File file, P2<File, String> function) {
        eachFile(file, function, null);
    }

    /**
     * The eachFile method.
     *
     * @param file the source file.
     * @param function the source function.
     * @param exclude the exclude function.
     */
    public static void eachFile(File file, P2<File, String> function, F1<File, Boolean> exclude) {
        eachFile(file, Functions.f2(function, true), exclude);
    }

    /**
     * The eachFile method.
     *
     * @param file the source file.
     * @param function the source function.
     * @param exclude the exclude function.
     */
    public static void eachFile(File file, F2<File, String, Boolean> function, F1<File, Boolean> exclude) {
        ArrayList<String> relativePath = new ArrayList<>();

        if(file.isFile())
            function.execute(file, CollectionUtil.join(relativePath, File.separator));
        else {
            if(!eachFileInDir(file, function, relativePath, exclude))
                return;
        }
    }

    /**
     * The eachFileInDir method.
     *
     * @param file the source file.
     * @param function the source function.
     * @param relativePath the relative path.
     * @param exclude the exclude function.
     * @return the boolean result.
     */
    private static boolean eachFileInDir(File file, F2<File, String, Boolean> function, ArrayList<String> relativePath, F1<File, Boolean> exclude) {
        if(!file.exists() || !file.isDirectory())
            throw new RuntimeException("Invalid path: " + file);

        for(File child : file.listFiles()) {
            if(exclude != null && exclude.execute(child))
                continue;

            if(child.isFile()) {
                if(!function.execute(child, CollectionUtil.join(relativePath, File.separator)))
                    return false;
            }
            else {
                relativePath.add(child.getName());

                if(!eachFileInDir(child, function, relativePath, exclude))
                    return false;

                relativePath.remove(relativePath.size() - 1);
            }
        }

        return true;
    }
}