package syntixi.fusion;

import syntixi.fusion.core.analysis.Analyzer;

/**
 * <code>AnalysisFSM</code> class represents the second stage in the <code>MAPE-K</code>
 * loop-based Finite-State Machine.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class AnalysisFSM implements State {

    /**
     * Private instance for accessing to the <code>Analyzer</code> class functionalities.
     */
    private Analyzer analyzer;

    /**
     * Constructor for initializing the <code>Analyzer</code> class features.
     */
    public AnalysisFSM() {
        analyzer = new Analyzer();
    }

    @Override
    public void next() {
        Sensor.setSTATE(1);
    }

    @Override
    public void back() {
        Sensor.setSTATE(0);
    }

    @Override
    public void working() {
        analyzer.init();

        Sensor.setSTATE(2);
    }
}