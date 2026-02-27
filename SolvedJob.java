class SolvedJob {

    Job job;

    Integer result;   // null if rejected

    boolean valid;

    String reason;    // required if invalid

    long solvedAt;    // System.nanoTime()

}