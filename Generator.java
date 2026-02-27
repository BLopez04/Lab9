public class Generator implements Runnable {

    private int N_jobs;
    private StageQueue<Job> ValidatorQueue;

    public Generator(int N_jobs, StageQueue<Job> ValidatorQueue) {
        this.N_jobs = N_jobs;
        this.ValidatorQueue = ValidatorQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < N_jobs; i++) {
                Job j = new Job(
                        i,
                        (int)(Math.random() * 100 - 50),
                        (int)(Math.random() * 100 - 50),
                        Op.values()[(int)(Math.random() * Op.values().length)],
                        System.nanoTime()
                );

                ValidatorQueue.put(j);
            }

            ValidatorQueue.put(Job.STOP);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
