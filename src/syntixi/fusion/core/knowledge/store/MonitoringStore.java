package syntixi.fusion.core.knowledge.store;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.util.Vector;

/**
 * <code>MonitoringStore</code> class stores all the information related both <code>XML</code>
 * requirements located in a local repository and <code>Java</code> components executed in
 * the <code>Java Virtual Machine</code>.
 * <p>
 * In the case of <code>XML</code> requirements, the information stored corresponds to the
 * address of the file.
 * <p>
 * In the case of <code>Java</code> components, the information stored corresponds to object
 * instances of the <code>VirtualMachineDescriptor</code> class, which is provided by the
 * <code>Java</code> language.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see com.sun.tools.attach.VirtualMachineDescriptor
 */
public final class MonitoringStore {

    /**
     * Contains a set of addresses corresponding to the <code>XML</code> files detected by
     * the monitoring mechanism.
     */
    private Vector<File> requirements = new Vector<>();

    /**
     * Contains a set of classes corresponding to the running components detected by
     * the monitoring mechanism.
     */
    private Vector<VirtualMachineDescriptor> components = new Vector<>();

    /**
     * Instance to access to the <code>MonitoringStore</code> class.
     */
    private static MonitoringStore monitoringStore;

    /**
     * Private constructor to limit outer instantiations.
     */
    private MonitoringStore(){
    }

    /**
     * Stores a specific <code>XML</code> file in the list of requirements.
     *
     * @param file the file to be stored in the list of <code>XML</code> requirements.
     * @see File
     */
    public void setRequirements(File file) {
        requirements.add(file);
    }

    /**
     * Stores a specific container class in the list of running components.
     *
     * @param vmd the container class to be stored in the list of <code>Java</code> components.
     * @see VirtualMachineDescriptor
     */
    public void setComponents(VirtualMachineDescriptor vmd) {
        if(components.isEmpty())
            components.add(vmd);
        else if(!components.contains(vmd))
            components.add(vmd);
    }

    /**
     * Returns a list of <code>XML</code> user requirements.
     *
     * @return the list of <code>XML</code> user requirements.
     */
    public Vector<File> getRequirements() {
        return requirements;
    }

    /**
     * Returns a list of container classes that describe a <code>Java Virtual Machine</code>.
     *
     * @return the list of container classes.
     */
    public Vector<VirtualMachineDescriptor> getComponents() {
        return components;
    }

    /**
     * Returns a unique <code>MonitoringStore</code> class instance.
     *
     * @return the MonitoringStore class instance.
     */
    public static MonitoringStore getMonitoringStore() {
        if(monitoringStore == null)
            monitoringStore = new MonitoringStore();

        return monitoringStore;
    }

    /**
     * Prints the content of the <code>XML</code> user requirements list.
     */
    public void printRequirements() {
        requirements.forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    /**
     * Prints the content of the running components list.
     */
    public void printComponents() {
        components.forEach(vmd -> System.out.println(vmd.displayName()));
    }
}