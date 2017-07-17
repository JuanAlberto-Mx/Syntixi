package syntixi.fusion;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <code>Sensor</code> class encapsulates the functionality of a physical sensor device
 * which can be started and stopped.
 * <p>
 * <code>Sensor</code> class implements a reactive Finite-State Machine based on the
 * <code>MAPE-K</code> loop to iterate indefinitely through the <code>MAPE-K</code> loop
 * stages keeping alive the generator's operation to attend unforeseen user requirements
 * in a generator's local directory and detecting <code>Java</code> components running in
 * the <code>Java Virtual Machine</code>.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class Sensor {

    /**
     * A static field for establishing the state where the generator execution must stay.
     * <p>
     * Allowed states are: <code>0 = back, 1 = working</code>, and <code>2 = next</code>.
     */
    private static int STATE = 1;

    /**
     * An array to initialize the <code>MAPE-K</code> loop stages.
     */
    private State[] stages = {new MonitoringFSM(), new AnalysisFSM(), new PlanningFSM(), new ExecutionFSM()};

    /**
     * A bidimensional array for establishing the allowed transitions between the
     * <code>MAPE-K</code> loop stages.
     */
    private int[][] transitions = {{3,0,1}, {0,1,2}, {1,2,3}, {2,3,0}};

    /**
     * Indicates the current <code>MAPE-K</code> loop stage where the generator execution is.
     */
    private int currentStage = 0;

    /**
     * Sets the state where the generator must be operating.
     *
     * @param state the state where the generator must be operating.
     *              <p>
     *              Allowed states are: <code>0 = back, 1 = working</code>, and
     *              <code>2 = next</code>
     */
    public static void setSTATE(int state) {
        STATE = state;
    }

    /**
     * Sets the next stage of the <code>MAPE-K</code> loop where the generator execution
     * must operate.
     *
     * @param index the <code>MAPE-K</code> loop stage.
     */
    private void next(int index) {
        currentStage = transitions[currentStage][index];
    }

    /**
     * Goes back one stage in the <code>MAPE-K</code> loop according to the current stage.
     */
    private void back() {
        stages[currentStage].back();
        next(0);
    }

    /**
     * Keeps working the generator in the current <code>MAPE-K</code> loop stage.
     */
    private void working() {
        stages[currentStage].working();
        next(1);
    }

    /**
     * Goes forward one stage in the <code>MAPE-K</code> loop according to the current stage.
     */
    private void next() {
        stages[currentStage].next();
        next(2);
    }

    /**
     * Starts the execution of the finite-state machine based on the <code>MAPE-K</code>
     * control loop.
     */
    public void run() {
        Runnable task = () -> {
            if (STATE == 0)
                back();
            else if (STATE == 1)
                working();
            else if (STATE == 2)
                next();
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);
    }
}