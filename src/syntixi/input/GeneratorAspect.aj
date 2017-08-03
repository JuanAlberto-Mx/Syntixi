package syntixi.input;

import agent.InstrumentationAgent;

/**
 * <code>GeneratorAspect</code> improves the modularity of <code>Generator</code> class
 * by separating arguments checking operations for generator's execution.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public aspect GeneratorAspect {

    pointcut execMain(String[] args):execution(public static void Generator.main(*)) && args(args);

    pointcut execProceed(String args): call(public static void Generator.proceed(String)) && args(args);

    before(String[] args): execMain(args) {
        InstrumentationAgent.init();
    }

    before(String args): execProceed(args) {
        if(args.equals("-reactive") || args.equals("-r") || args.isEmpty()) {
            System.out.println("[Sýntixi]\tSensor mode activated");
        }
        else if(args.equals("-active") || args.equals("-a")) {
            System.out.println("[Sýntixi]\tPushButton mode activated");
        }
        else {
            System.err.println("Invalid command line argument!\n");
            System.out.println("Sýntixi's command line arguments accepted:\n");
            System.out.println("\t[-reactive] or [-r] to start the Sensor mode\n" +
                    "\t[-active] or [-a] to start the PushButton mode\n");
        }
    }

    after(String[] args): execMain(args) {
        if(args.length == 0) {
            System.out.println("Sýntixi's command line arguments:");
            System.out.println("\t[-reactive] or [-r] to start the Sensor mode\n" +
                    "\t[-active] or [-a] to start the PushButton mode\n");
        }
        else {
            Generator.proceed(args[0].toLowerCase().trim());
        }
    }
}