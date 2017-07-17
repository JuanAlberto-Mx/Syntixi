package syntixi.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <code>FileManager</code> class provides a set of methods to perform operations
 * with files, such as copy files from specific sources to target directories and
 * remove files from a particular directory.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class FileManager {

    /**
     * Index to control the iterations over the files.
     */
    private int index = 0;

    /**
     * The backup directory.
     */
    private File directory;

    /**
     * The source directory.
     */
    private File source;

    /**
     * The target directory.
     */
    private File target;

    /**
     * Constructor to initialize the source and target directories.
     *
     * @param source the source directory.
     * @param target the target directory.
     */
    public FileManager(File source, File target) {
        this.directory = source;
        this.source = source;
        this.target = target;
    }

    /**
     * Sets the index to control the iterations over the files.
     *
     * @param index the index to control the iterations over the files.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the source directory which contains the files to move.
     *
     * @return the source directory.
     */
    public File getSource() {
        return source;
    }

    /**
     * Returns the target directory where the files will be located.
     *
     * @return the target directory.
     */
    public File getTarget() {
        return target;
    }

    /**
     * Copies files from source to target directory recursively.
     *
     * @param source the source directory.
     * @param target the target directory.
     */
    public void copyFiles(File source, File target) {
        if(!source.isDirectory())
            copy(source,target);
        else{
            index++;

            if(index == 1) {
                target = new File(target.getAbsolutePath() + File.separator + source.getName());

                if(!target.exists())
                    target.mkdir();
            }

            String paths[] = source.list();

            for(int i = 0; i < paths.length; i++) {
                File newSource = new File(source.getAbsolutePath() + File.separator + paths[i]);
                File newTarget = new File(target.getAbsolutePath() + File.separator + paths[i]);

                if(newSource.isDirectory() && !newTarget.exists())
                    newTarget.mkdir();

                copyFiles(newSource, newTarget);
            }
        }
    }

    /**
     * Copies files from source to target directory.
     *
     * @param source the source directory.
     * @param target the target directory.
     */
    private void copy(File source, File target) {
        try {
            if(source.exists()) {
                FileInputStream fileInputStream = new FileInputStream(source);
                OutputStream outputStream = new FileOutputStream(target);

                byte[] buffer = new byte[1024];
                int length;

                while((length = fileInputStream.read(buffer)) > 0)
                    outputStream.write(buffer, 0, length);

                outputStream.close();
                fileInputStream.close();
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Removes an entire directory.
     *
     * @param directory the directory to delete.
     */
    public void remove(File directory) {
        File[] fileList = directory.listFiles();

        for(int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory())
                remove(fileList[i]);

            fileList[i].delete();
        }

        directory.delete();
    }
}