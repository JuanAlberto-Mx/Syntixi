package syntixi.fusion.core.monitoring;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Environment</code> class provides a set of methods to manipulate observer elements.
 * <p>
 * <code>Environment</code> class allows the user to add and delete observer instances, as
 * well as launch notifications to each one.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public abstract class Environment {

    /**
     * The observers list for a specific class.
     */
    private List<Observer> observers = new ArrayList<Observer>();

    /**
     * Adds a new observer to the observers list.
     *
     * @param observer the observer class that will be responsible for monitoring changes.
     * @see Observer
     */
    protected void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Deletes a specific observer from the observers list.
     *
     * @param observer the observer class that will be deleted from the observers list.
     * @see Observer
     */
    protected void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies to all observers existing in the observers list when changes occur in a
     * specific class property related to the monitored class.
     */
    protected void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }
}