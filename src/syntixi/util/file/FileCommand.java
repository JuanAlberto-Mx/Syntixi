package syntixi.util.file;

/**
 * <code>FileCommand</code> class allows to encapsulate a set of operations to
 * interact with files.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class FileCommand implements Command {

    /**
     * Instance to the <code>File</code> manager.
     */
    private FileManager fileManager;

    /**
     * Constructor to initialize the <code>File</code> manager instance.
     *
     * @param fileManager the <code>File</code> manager to instantiate.
     */
    public FileCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        fileManager.copyFiles(fileManager.getSource(), fileManager.getTarget());
        fileManager.setIndex(0);
        fileManager.remove(fileManager.getSource());
    }
}