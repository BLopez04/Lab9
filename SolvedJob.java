class SolvedJob {
    Job job;
    Integer result;   // null if rejected
    boolean valid;
    String reason;    // required if invalid
    long solvedAt;    // System.nanoTime()

    public SolvedJob(Job job, int result, boolean valid, String reason, long solvedAt) {
        this.job = job;
        this.result = result;
        this.valid = valid;
        this.reason = reason;
        this.solvedAt = solvedAt;
    }

    public static final SolvedJob STOP = new SolvedJob(Job.STOP, 0, false, "", 0);
}