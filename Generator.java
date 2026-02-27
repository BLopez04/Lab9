public class Generator implements Runnable {

    private int N_jobs;
    private StageQueue<Job> ValidatorQueue;

    public Generator(int N_jobs, StageQueue<Job> ValidatorQueue) {
        this.N_jobs = N_jobs;
        this.ValidatorQueue = ValidatorQueue;
    }

    @Override
    public void run() {

    }
}
