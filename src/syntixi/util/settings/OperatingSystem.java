package syntixi.util.settings;

/**
 * <code>OperatingSystem</code> class provides a set of methods to determine the platform
 * in which <code>Sýntixi</code> is executed.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public final class OperatingSystem {

    /**
     * The field to set the operating system used.
     */
    private String OS;

    /**
     * The kind of slash used in the operating system detected.
     */
    private String slash;

    /**
     * The <code>OperatingSystem</code> instance.
     */
    private static OperatingSystem operatingSystem;

    /**
     * Private constructor to restrict class instantiations.
     */
    private OperatingSystem() {
    }

    /**
     * Gets the name of the operating system used.
     *
     * @return the name of the operating system used.
     */
    public String getOS() {
        this.OS = System.getProperty("os.name");

        return OS;
    }

    /**
     * Gets the kind of slash used in the current operating system.
     *
     * @param option the operating system index.
     *               <p>
     *               <b>Options</b>: <code>1 = Linux, 2 = Windows</code>.
     * @return the the kind of slash used in the current operating system.
     */
    public String getSlash(int option) {
        if(getOS().equals("Linux") || getOS().equals("linux")) {
            if(option == 1)
                this.slash = "/";
            else
                this.slash = "//";
        }
        else if(getOS().equals("Windows") || getOS().equals("windows"))
            this.slash = "\\";

        return this.slash;
    }

    /**
     * Gets an <code>OperatingSystem</code> instance.
     *
     * @return the <code>OperatingSystem</code> instance.
     */
    public static OperatingSystem getOperatingSystem() {
        if(operatingSystem == null)
            operatingSystem = new OperatingSystem();

        return operatingSystem;
    }
}