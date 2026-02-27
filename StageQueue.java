import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class StageQueue {
    private final LinkedList<String> items = new LinkedList<String>();

    private final ReentrantLock lock = new ReentrantLock(true);

}
