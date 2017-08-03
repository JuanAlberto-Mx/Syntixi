package syntixi.input;

import syntixi.fusion.PushButton;
import syntixi.fusion.Sensor;
import syntixi.util.settings.Repository;
import syntixi.util.settings.Workspace;

/**
 * <code>Generator</code> class is the starting point to perform the fusion of software
 * components at run-time.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Generator {

    /**
     * Receives an execution command to specify the generator's execution mode
     * which can be <code>active</code> or <code>reactive</code>.
     * <p>
     * The <code>active</code> execution mode indicates that the generator will pass
     * through the <code>MAPE-K</code> loop stages and will finish its execution
     * after the last <code>MAPE-K</code> loop stage. To use this execution mode,the
     * user must host the <code>XML</code> requirements in the generator's requirements
     * directory before executing the generator.
     * <p>
     * The <code>reactive</code> execution mode indicates that the generator will
     * iterate through the <code>MAPE-K</code> loop stages indefinitely until a
     * <code>Ctrl + C</code> keystroke is detected. To use this execution mode, the
     * user requirements existence it is not necessary before the generator's
     * execution because it will behave like a sensor ready to detect unforeseen
     * user requirements.
     *
     * @param command the execution command.
     *                <p>
     *                <b>Options</b>: <code>-reactive</code> or <code>-r</code> to establish
     *                a reactive execution mode, <code>-active</code> or <code>-a</code> to
     *                establish an active execution mode.
     */
    public static void proceed(String command) {
        if(command.equals("-reactive") || command.equals("-r") || command.isEmpty()) {
            new Sensor().run();
        }
        else if(command.equals("-active") || command.equals("-a")) {
            new PushButton().run();
        }
    }

    /**
     * Executes the <code>Sýntixi</code> generator.
     *
     * @param args the generator's initial parameters.
     */
    public static void main(String... args) {
        new Workspace("requirements");
        new Repository("repository");
    }
}