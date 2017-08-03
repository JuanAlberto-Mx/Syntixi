package syntixi.fusion.aop;

import java.io.File;

/**
 * <code>StoreAspect</code> encapsulates all operations and output messages related
 * to <code>store</code> package classes.
 */
public aspect StoreAspect {

    pointcut deleteRequirement(File file): execution(* syntixi.fusion.core.knowledge.store.MonitoringStore.deleteRequirement(File)) && args(file);

    after(File file): deleteRequirement(file) {
        if(file.delete()){
            System.out.println("[Monitoring Store]\t" + file.getName() + " is deleted!");
        }
        else{
            System.out.println("[Monitoring Store]\tDelete operation is failed");
        }
    }
}