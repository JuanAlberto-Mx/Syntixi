package syntixi.util.xml;

/**
 * <code>XMLCommand</code> class allows to encapsulate a set of operations to
 * interact with <code>XML</code> files.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class XMLCommand implements Command {

    /**
     * Instance to the <code>XML</code> manager.
     */
    private XMLManager xmlManager;

    /**
     * Constructor to initialize the <code>XML</code> manager instance.
     *
     * @param xmlManager the <code>XML</code> manager to instantiate.
     * @see XMLManager
     */
    public XMLCommand(XMLManager xmlManager) {
        this.xmlManager = xmlManager;
    }

    @Override
    public void execute() {
        xmlManager.fillInstance();
    }
}