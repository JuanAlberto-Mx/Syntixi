package syntixi.util.xml;

/**
 * <code>DeleteXMLInstance</code> class encapsulates a set of operations to
 * delete all <code>Java</code> bean instances corresponding to a specific
 * <code>XML</code> file.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class DeleteXMLInstance implements Command {

    /**
     * Instance to the <code>XML</code> manager.
     */
    private XMLManager xmlManager;

    /**
     * Constructor to initialize the <code>XML</code> manager instance.
     *
     * @param xmlManager the <code>XML</code> manager to instantiate.
     */
    public DeleteXMLInstance(XMLManager xmlManager) {
        this.xmlManager = xmlManager;
    }

    @Override
    public void execute() {
        xmlManager.deleteInstance();
    }
}