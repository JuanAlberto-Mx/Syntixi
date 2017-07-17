package syntixi.fusion.core.monitoring;

import syntixi.fusion.core.knowledge.store.MonitoringStore;
import syntixi.util.settings.Workspace;

import java.io.File;

import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>Requirement</code> class detects new user requirements in the local repository
 * of the generator.
 * <p>
 * <code>Requirement</code> class is supported by an observer mechanism that stores each
 * detected user requirement in the local repository and keeps updated a list of user
 * requirements of the <code>MonitoringStore</code> class. Each user requirements is
 * specified via <code>XML</code> files.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see RequirementObserver
 * @see MonitoringStore
 */
public class Requirement extends Environment {

    /**
     * Indicates the existence of a new requirement.
     */
    private boolean detected;

    /**
     * <code>XML</code> requirement detected in the local repository.
     */
    private File requirement;

    /**
     * Sets the value of the requirement flag to indicate if a new user requirement
     * is detected in the local repository.
     *
     * @param detected the value to assign.
     *                 <code>true</code> if a new requirement is detected;
     *                 <code>false</code> otherwise.
     */
    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    /**
     * Sets the value of the <code>XML</code> file detected in the local repository.
     *
     * @param file the <code>XML</code> file detected in the local repository.
     * @see File
     */
    public void setRequirement(File file) {
        requirement = file;
        notifyObservers();
    }

    /**
     * Returns the current value of the requirement flag.
     *
     * @return <code>true</code> if new requirements are detected; <code>false</code> otherwise.
     */
    public boolean getDetected() {
        return detected;
    }

    /**
     * Returns the current <code>XML</code> file detected.
     *
     * @return the file corresponding to the <code>XML</code> user requirement.
     */
    public File getRequirement() {
        return requirement;
    }

    /**
     * Searches for new <code>XML</code> user requirements in the local repository of the
     * generator and sets both a flag and a file in case of new requirements detected.
     */
    public void findRequirements() {
        File folder = new File(Workspace.getWorkspace());

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith("xml"))
                if (!fileExist(file))
                    setRequirement(file);
        }
    }

    /**
     * Verifies if an <code>XML</code> file exists in the requirement list.
     *
     * @param file the <code>XML</code> file.
     * @return <code>true</code> if the <code>XML</code> file exists; <code>false</code> otherwise.
     */
    private boolean fileExist(File file){
        boolean flag = false;

        for(File f : getMonitoringStore().getRequirements()){
            if(file.equals(f)){
                flag = true;
                break;
            }
        }

        return flag;
    }
}