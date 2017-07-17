package syntixi.fusion;

import syntixi.fusion.core.analysis.Analyzer;
import syntixi.fusion.core.execution.Executor;
import syntixi.fusion.core.monitoring.Monitor;
import syntixi.fusion.core.planning.Planner;

/**
 * <code>PushButton</code> class encapsulates the functionality of a classic button that
 * executes a specific work every time it is pressed.
 * <p>
 * The main goal of <code>PushButton</code> class is to monitor user requirements and
 * <code>Java</code> components running in the <code>Java Virtual Machine</code> every time the
 * <code>push</code> method is executed.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class PushButton extends MapekTemplate {

    @Override
    protected void monitoring() {
        mapek = new Monitor();
        mapek.init();
        ((Monitor)mapek).searchRequirements();
        ((Monitor)mapek).searchComponents();
    }

    @Override
    protected void analysis() {
        mapek = new Analyzer();
        mapek.init();
    }

    @Override
    protected void planning() {
        mapek = new Planner();
        mapek.init();
    }

    @Override
    protected void execution() {
        mapek = new Executor();
        mapek.init();
    }
}