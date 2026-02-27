public class PipelineApp {
    public static void main(String[] args) throws InterruptedException {
        final int N = 10_000;   // number of jobs
        final int K = 4;        // number of solver threads

        System.out.println("Starting pipeline...");
        System.out.println("Jobs: " + N);
        System.out.println("Solver threads: " + K);

        // Create queues
        StageQueue<Job> q1 = new StageQueue<>();
        StageQueue<Job> q2 = new StageQueue<>();
        StageQueue<SolvedJob> q3 = new StageQueue<>();

        // Create stages
        Generator generator = new Generator(N, q1);
        Validator validator = new Validator(q1, q2, q3, K);
        Aggregator aggregator = new Aggregator(q3, K);

        // Threads
        Thread generatorThread = new Thread(generator, "Generator");
        Thread validatorThread = new Thread(validator, "Validator");
        Thread aggregatorThread = new Thread(aggregator, "Aggregator");

        Thread[] solverThreads = new Thread[K];
        for (int i = 0; i < K; i++) {
            solverThreads[i] = new Thread(
                    new Solver(q2, q3),
                    "Solver-" + i
            );

        }
        long startTime = System.nanoTime();

        // Start threads
        generatorThread.start();
        validatorThread.start();
        aggregatorThread.start();
        for (Thread t : solverThreads) {
            t.start();
        }

        // Wait for completion
        generatorThread.join();
        validatorThread.join();
        for (Thread t : solverThreads) {
            t.join();
        }
        aggregatorThread.join();

        long endTime = System.currentTimeMillis();
        System.out.println("\nPipeline completed.");
        System.out.println("Total time: " + (endTime - startTime) + " ms");
    }

}