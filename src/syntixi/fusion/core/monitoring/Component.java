package syntixi.fusion.core.monitoring;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import syntixi.fusion.core.knowledge.store.MonitoringStore;
import syntixi.util.settings.Exceptions;

import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;

/**
 * <code>Component</code> class is responsible for detecting components running in the
 * <code>Java Virtual Machine</code>.
 * <p>
 * <code>Component</code> class is supported by an observer mechanism that stores all
 * the components detected in the <code>JVM</code> and keeps updated a specific list
 * of the <code>MonitoringStore</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see ComponentObserver
 * @see MonitoringStore
 */
public class Component extends Environment {

    /**
     * Components and applications detected in the <code>Java Virtual Machine</code>.
     */
    private VirtualMachineDescriptor virtualMachineDescriptor;

    /**
     * Sets an instance of the component detected in the <code>Java Virtual Machine</code>
     * and launch a notification to all observers about the current detection.
     *
     * @param vmd the container class that describes the detected <code>Java Virtual Machine</code>.
     * @see VirtualMachineDescriptor
     */
    public void setVirtualMachineDescriptor(VirtualMachineDescriptor vmd) {
        virtualMachineDescriptor = vmd;
        notifyObservers();
    }

    /**
     * Returns the current container class detected by the observer mechanism.
     *
     * @return the current detected container class.
     */
    public VirtualMachineDescriptor getVirtualMachineDescriptor() {
        return virtualMachineDescriptor;
    }

    /**
     * Searches for components running in the <code>Java Virtual Machine</code> and sets
     * a <code>Virtual Machine Descriptor</code> in case of new components detected.
     */
    public void findComponents() {
        for(VirtualMachineDescriptor vmd: VirtualMachine.list()) {
            if(!Exceptions.exists(vmd.displayName()))
                if(!componentExists(vmd))
                    setVirtualMachineDescriptor(vmd);
        }
    }

    /**
     * Verifies if a component exists in the component list.
     *
     * @param vmd the virtual machine descriptor
     * @return <code>true</code> if the component exists; <code>false</code> otherwise.
     */
    private boolean componentExists(VirtualMachineDescriptor vmd) {
        boolean flag = false;

        for(VirtualMachineDescriptor v : getMonitoringStore().getComponents()){
            if(vmd.equals(v)){
                flag = true;
                break;
            }
        }

        return flag;
    }
}