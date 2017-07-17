package syntixi.util.settings;

import java.io.File;

/**
 * <code>Workspace</code> class allows setting the local directory to store user
 * requirements.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Workspace {

    /**
     * The field to set the workspace path.
     */
    private static String workspace;

    /**
     * Sets the workspace path.
     * @param folder the full path to the local directory desired.
     */
    public Workspace(String folder) {
        workspace = getCurrentDirectory() + File.separator + folder + File.separator;
    }

    /**
     * Gets the full path to the workspace directory.
     * @return the workspace full path.
     */
    public static String getWorkspace() {
        return workspace;
    }

    /**
     * Gets the <code>Sýntixi</code>'s current directory path.
     * @return the Sýntixi's current directory path.
     */
    public static String getCurrentDirectory() {
        String directory = System.getProperty("user.dir");

        return directory;
    }
}