package syntixi.util.dbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>Interface</code> annotation allows adding contractual information to improve
 * the properties specification of methods.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Interface {

    enum InterfaceType {
        REQUIRED, PROVIDED, OPERATION
    }

    enum Measure {
        LOW, MEDIUM, HIGH, VERY_HIGH
    }

    String goal() default "";

    String[] keywords();

    int parameters() default 0;

    String returnType() default "";

    InterfaceType type() default InterfaceType.PROVIDED;

    Measure importance() default Measure.MEDIUM;

    Measure susceptible() default Measure.MEDIUM;
}