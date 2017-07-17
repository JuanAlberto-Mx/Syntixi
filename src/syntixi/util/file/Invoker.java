package syntixi.util.file;

/**
 * <code>Invoker</code> class allows to decrement the complexity for the execution
 * of commands.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 * @see Command
 */
public class Invoker {

    /**
     * Starts the execution of a command.
     *
     * @param command the command to execute.
     */
    public void start(Command command) {
        command.execute();
    }
}