public class Validator implements Runnable {
    private StageQueue<Job> ValidatorQueue;
    private StageQueue<SolvedJob> AggregatorQueue;
    private StageQueue<Job> SolverQueue;
    private int K_threads;

    public Validator(StageQueue<Job> ValidatorQueue, StageQueue<Job> AggregatorQueue,
                     StageQueue<SolvedJob> SolverQueue, int N_jobs) {
        this.ValidatorQueue = ValidatorQueue;
        this.AggregatorQueue = AggregatorQueue;
        this.SolverQueue = SolverQueue;
        this.K_threads = K_threads;
    }
    @Override
    public void run() {

    }
}
