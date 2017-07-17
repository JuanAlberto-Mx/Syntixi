package syntixi.fusion.core.knowledge.store;

/**
 * <code>StatusStore</code> class provides the means to update the status corresponding
 * to the result of the basic analysis performed by the analysis mechanism.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class StatusStore {

    /**
     * The flag to indicate if there are unsolved functionalities.
     */
    private boolean basicAnalysis;

    /**
     * The instance of the class.
     */
    private static StatusStore statusStore;

    /**
     * Private constructor to limit outer instantiations.
     */
    private StatusStore() {
    }

    /**
     * Initializes the status of the basic analysis status flag.
     *
     * @param status the status of the basic analysis result.
     *               <code>true</code> if the result is successful;
     *               <code>false</code> otherwise.
     */
    public void setBasicAnalysis(boolean status) {
        basicAnalysis = status;
    }

    /**
     * Returns the value of the basic analysis status.
     *
     * @return the value of the basic analysis status.
     *         <code>true</code> if the result is successful;
     *         <code>false</code> otherwise.
     */
    public boolean getBasicAnalysis() {
        return basicAnalysis;
    }

    /**
     * Returns a unique instance of the <code>StatusStore</code> class.
     *
     * @return the instance of the <code>StatusStore</code> class.
     */
    public static StatusStore getStatusStore() {
        if(statusStore == null)
            statusStore = new StatusStore();

        return statusStore;
    }
}