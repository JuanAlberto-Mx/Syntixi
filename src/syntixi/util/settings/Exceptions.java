package syntixi.util.settings;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Exceptions</code> class contains a list of components that will be
 * ignored by <code>Sýntixi</code> during the fusion process.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Exceptions {

    /**
     * The list of ignored components.
     */
    private static List<String> exceptionList = initExceptionList();

    /**
     * Initializes the list with the names of components or key phrases that
     * will be ignored.
     *
     * @return the list of ignored components.
     */
    public static List<String> initExceptionList() {
        List<String> exceptions = new ArrayList<>();

        exceptions.add("com.intellij.idea.Main");
        exceptions.add("maven");
        exceptions.add("org.jetbrains.");
        exceptions.add("Generator");
        exceptions.add("syntixi");
        exceptions.add("/");

        return exceptions;
    }

    /**
     * Verifies if a component exists in the list of ignored components.
     *
     * @param component the name of the component to compare.
     * @return <code>true</code> if the component exists in the list;
     *          <code>false</code> otherwise.
     */
    public static boolean exists(String component) {
        boolean flag = false;

        for(String key : exceptionList) {
            if(component.contains(key)) {
                flag = true;
                break;
            }
        }

        return flag;
    }
}