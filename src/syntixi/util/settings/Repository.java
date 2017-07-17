package syntixi.util.settings;

import java.io.File;

/**
 * <code>Repository</code> class allows users setting the requirements repository full path
 * which will be used by <code>Sýntixi</code> to fuse the necessary components.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Repository {

    /**
     * The full path to the selected repository.
     */
    private static String repository;

    /**
     * Constructor to initialize the <code>Sýntixi</code> repository.
     *
     * @param folder the name of the directory.
     */
    public Repository(String folder) {
        repository = Workspace.getCurrentDirectory() + File.separator + folder + File.separator;
    }

    /**
     * Gets the requirements repository full path.
     *
     * @return the requirements repository full path.
     */
    public static String getRepository() {
        return repository;
    }
}