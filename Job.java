class Job {
    long id;
    int a;
    int b;
    Op op;          // ADD, SUB, MUL, DIV, MOD
    long createdAt; // System.nanoTime()

    public Job(long id, int a, int b, Op op, long createdAt) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.op = op;
        this.createdAt = createdAt;
    }
    public static final Job STOP = new Job(-1, 0, 0, Op.ADD, 0);
}

