package syntixi.fusion.core.analysis;

import syntixi.fusion.Mapek;
import syntixi.util.bean.Checklist;
import syntixi.util.bean.Requirement;
import syntixi.util.xml.FillXMLInstance;
import syntixi.util.xml.Invoker;
import syntixi.util.xml.XMLManager;

import java.io.File;

import static syntixi.fusion.core.knowledge.store.AnalysisStore.getAnalysisStore;
import static syntixi.fusion.core.knowledge.store.MonitoringStore.getMonitoringStore;
import static syntixi.fusion.core.knowledge.store.RequirementsStore.getRequirementsStore;

/**
 * <code>Analyzer</code> class represents the second stage of the <code>MAPE-K</code> loop
 * and provides the necessary mechanisms to analyse different components at run-time
 * in order to find the most suitable elements that satisfy the user requirements.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Analyzer implements Mapek {

    /**
     * Instance to access to the <code>ComponentExplorer</code>'s class functionalities.
     */
    private ComponentAnalyzer componentAnalyzer = new ComponentAnalyzer();

    @Override
    public void init() {
        analyzeXML();
        initializeChecklist();
        separateComponents();
        analyzeComponents();
    }

    /**
     * Searches for new requirements in a local repository.
     * <p>
     * Each requirement is specified via a <code>XML</code> file and its information is used
     * to create object instances that allow the analysis mechanism to find suitable
     * components based on the user needs.
     */
    private void analyzeXML() {
        XMLManager xmlManager;
        FillXMLInstance fillXMLInstance;

        for(File file : getMonitoringStore().getRequirements()) {
            xmlManager = new XMLManager(file.getAbsolutePath());
            fillXMLInstance = new FillXMLInstance(xmlManager);

            new Invoker().start(fillXMLInstance);
        }
    }

    /**
     * Creates and initializes a checklist for each user requirement in order to have
     * a control mechanism to know which functionalities are already solved or not.
     */
    private void initializeChecklist() {
        for(Requirement requirement : getRequirementsStore().getRequirements())
            getAnalysisStore().setChecklists(requirement, new Checklist(requirement));
    }

    /**
     * Filtrates components in order to get a set both fusionable components and
     * traditional components. Each component is stored in a list of traditional or
     * fusionable components and is make it available for future analysis and/or
     * planning operations.
     */
    private void separateComponents() {
        componentAnalyzer.separateComponents();
    }

    /**
     * Initiates the analysis process. A basic analysis of running components for each
     * user requirement is performed.
     */
    private void analyzeComponents() {
        getAnalysisStore().getChecklists().forEach((requirement,checklist) -> {
            componentAnalyzer.basicAnalysis(requirement, checklist);
        });
    }
}