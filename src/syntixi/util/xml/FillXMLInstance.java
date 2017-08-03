package syntixi.util.xml;

/**
 * <code>FillXMLInstance</code> class allows to encapsulate a set of operations to
 * fill <code>XML</code> instances into <code>Java</code> beans with equivalent
 * structure to the current <code>XML</code> file.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class FillXMLInstance implements Command {

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
    public FillXMLInstance(XMLManager xmlManager) {
        this.xmlManager = xmlManager;
    }

    @Override
    public void execute() {
        xmlManager.fillInstance();
    }
}