package syntixi.input;

/**
 * <code>GeneratorAspect</code> improves the modularity of <code>Generator</code> class
 * by separating arguments checking operations for generator's execution.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public aspect GeneratorAspect {

    pointcut execMain(String[] args):execution(public static void Generator.main(*)) && args(args);

    after(String[] args):execMain(args) {
        if(args.length == 0)
            Generator.proceed(null);
        else
            Generator.proceed(args[0].toLowerCase().trim());
    }
}