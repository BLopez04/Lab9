import java.util.Optional;

class SolvedJob {
    Job job;
    Optional<Integer> result;   // null if rejected
    boolean valid;
    String reason;    // required if invalid
    long solvedAt;    // System.nanoTime()

    public SolvedJob(Job job, Optional<Integer> result, boolean valid, String reason, long solvedAt) {
        this.job = job;
        this.result = result;
        this.valid = valid;
        this.reason = reason;
        this.solvedAt = solvedAt;
    }

    public static final SolvedJob STOP = new SolvedJob(Job.STOP, Optional.of(0), false, "", 0);
}