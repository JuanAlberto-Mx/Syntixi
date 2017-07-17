package syntixi.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import syntixi.util.bean.Alternative;
import syntixi.util.bean.Description;
import syntixi.util.bean.Functionality;
import syntixi.util.bean.Requirement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static syntixi.fusion.core.knowledge.store.RequirementsStore.getRequirementsStore;

/**
 * <code>XMLManager</code> class provides a set of methods to create <code>Requirement</code>
 * instances based on the information obtained by reading an <code>XML</code> file.
 * <p>
 * Each <code>Requirement</code> instance has a well-defined structure and is stored in a list
 * of the <code>RequirementStore</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Requirement
 */
public class XMLManager {

    /**
     * <code>XML</code> file path.
     */
    private String path;

    /**
     * Instances to create a <code>Java</code> structure based on the current <code>XML</code>
     * file.
     */
    private Requirement requirement;
    private Description description;
    private Functionality functionality;
    private Alternative alternative;

    /**
     * Constructor to initialize the <code>XML</code> file path as well as the
     * <code>Requirement</code> instances.
     *
     * @param path the <code>XML</code> file path.
     */
    public XMLManager(String path) {
        this.path = path;

        requirement = new Requirement();
        description = new Description();
        alternative = new Alternative();
    }

    /**
     * Creates and loads a new Document instance to interact with an <code>XML</code>
     * file and access to its content as an <code>Abstract Syntax Tree</code>.
     *
     * @return the attribute that allows direct access to the child node of the document.
     */
    public Element load() {
        Element element = null;

        try {
            File file = new File(path);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            element = document.getDocumentElement();
        }
        catch(SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        finally {
            element.normalize();
        }

        return element;
    }

    /**
     * Reads and obtains the child nodes of the current <code>XML</code> file to access
     * and manipulate its information.
     *
     * @return the child nodes of the <code>XML</code> file.
     */
    public NodeList read() {
        return load().getElementsByTagName("component").item(0).getChildNodes();
    }

    /**
     * Iterates each branch of the <code>XML</code> file and creates a <code>Requirement</code>
     * instance with the information of each node.
     * <p>
     * All created instances have a <code>Java</code> structure that is equivalent to the
     * original <code>XML</code> structure, and are stored in a list of the
     * <code>RequirementsStore</code> class to make them available for future operations.
     */
    public void fillInstance() {
        NodeList childNodes = read();

        for(int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if(child.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) child;
                String tagName = element.getTagName();

                fillInstance(element, tagName);
            }
        }

        getRequirementsStore().setRequirements(requirement);
    }

    /**
     * Fills the <code>Requirement</code> instances by using the information of each
     * node of an <code>XML</code> file.
     *
     * @param element the current child nodes of the <code>XML</code> file.
     * @param tagName the name of the node.
     * @see Element
     */
    public void fillInstance(Element element, String tagName) {
        switch (tagName) {
            case "description":
                description.setName(element.getElementsByTagName("name").item(0).getTextContent());
                description.setGoal(element.getElementsByTagName("goal").item(0).getTextContent());
                requirement.setDescription(description);
                break;
            case "functionality":
                functionality = new Functionality();
                functionality.setKeywords(element.getElementsByTagName("keywords").item(0).getTextContent().split(","));
                functionality.setInput(element.getElementsByTagName("input").item(0).getTextContent().split(","));
                functionality.setOutput(element.getElementsByTagName("output").item(0).getTextContent());
                requirement.setFunctionalities(functionality);
                break;
            case "alternative":
                alternative.setAlternative(element.getTextContent());
                requirement.setAlternative(alternative);
                break;
            default:
                break;
        }
    }
}