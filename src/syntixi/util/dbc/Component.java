package syntixi.util.dbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>Component</code> annotation allows adding contractual information to improve
 * the properties specification of classes.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface Component {

    enum Measure {
        LOW, MEDIUM, HIGH, VERY_HIGH
    }

    String goal() default "";

    String[] keywords() default "";

    Measure importance() default Measure.MEDIUM;

    Measure susceptible() default Measure.MEDIUM;

    int requiredInterfaces() default 0;

    int providedInterfaces() default 0;
}