import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class StageQueue<T> {
    private final LinkedList<T> items = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notEmpty = lock.newCondition();

    public void put(T item) {
        lock.lock();
        try {
            items.addLast(item);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            return items.removeFirst();
        } finally {
            lock.unlock();
        }
    }
}
