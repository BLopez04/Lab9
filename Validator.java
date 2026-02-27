public class Validator implements Runnable {
    private StageQueue<Job> ValidatorQueue;
    private StageQueue<SolvedJob> AggregatorQueue;
    private StageQueue<Job> SolverQueue;
    private int K_threads;

    public Validator(StageQueue<Job> ValidatorQueue,
                     StageQueue<Job> SolverQueue,
                     StageQueue<SolvedJob> AggregatorQueue,
                     int K_threads) {
        this.ValidatorQueue = ValidatorQueue;
        this.SolverQueue = SolverQueue;
        this.AggregatorQueue = AggregatorQueue;
        this.K_threads = K_threads;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Job j = ValidatorQueue.take();

                if (j == Job.STOP) {
                    for (int i = 0; i < K_threads; i++) {
                        SolverQueue.put(Job.STOP);
                    }
                    return;
                }

                boolean valid = true;
                String reason = null;

                if ((j.op == Op.DIV || j.op == Op.MOD) && j.b == 0) {
                    valid = false;
                    reason = "Division or modulo by zero";
                }

                if (!valid) {
                    SolvedJob invalid = new SolvedJob(j, 0, false, reason, System.nanoTime());
                    AggregatorQueue.put(invalid);
                    continue;
                }

                SolverQueue.put(j);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
