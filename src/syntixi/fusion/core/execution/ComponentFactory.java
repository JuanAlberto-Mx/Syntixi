package syntixi.fusion.core.execution;

/**
 * <code>ComponentFactory</code> implements the <code>AbstractFactory</code>
 * to proceed with the fusion of software components according to the
 * alternative specified in the user requirement.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ComponentFactory extends AbstractFactory {

    @Override
    public OComponent buildOComponent(String alternative) {
        OComponent oComponent = null;

        if(alternative.equals("OCC"))
            oComponent = new OCComp();
        else if(alternative.equals("OCWC"))
            oComponent = new OCWComp();

        return oComponent;
    }

    @Override
    public GComponent buildGComponent(String alternative) {
        GComponent gComponent = null;

        if(alternative.equals("GCC"))
            gComponent = new GCComp();
        else if(alternative.equals("GCWC1"))
            gComponent = new GCWComp1();
        else if(alternative.equals("GCWC2"))
            gComponent = new GCWComp2();

        return gComponent;
    }
}