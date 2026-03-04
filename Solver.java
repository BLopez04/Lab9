import java.util.Optional;

public class Solver implements Runnable{
    private StageQueue<Job> inPipe;
    private StageQueue<SolvedJob> outPipe;

    public Solver(StageQueue<Job> inPipe, StageQueue<SolvedJob> outPipe){
        this.inPipe = inPipe;
        this.outPipe = outPipe;
    }

    public int solveJob(Job job){
        int res = 0;
        switch (job.op){
            case ADD -> res = job.a + job.b;
            case SUB -> res = job.a - job.b;
            case MUL -> res = job.a * job.b;
            case DIV -> res = (job.b == 0) ? 0 : job.a / job.b;
            case MOD -> res =Math.floorMod(job.a, job.b);
        }
        return res;
    }
    @Override
    public void run() {
        while(true){
            try {
                // get a job, blocks if there is none
                Job curJob = inPipe.take();

                // check if the job is a STOP signal,
                // if it is, then terminate
                if(curJob.equals(Job.STOP)){
                    outPipe.put(SolvedJob.STOP);
                    return;
                }

                // start tracking time it takes to solve job
                long startTime = System.nanoTime();

                // solve job while getting all info for SolvedJob object
                Optional<Integer> res;
                boolean valid = true;
                String reason = "";
                if(curJob.op.equals(Op.DIV) && curJob.b == 0) {
                    res = null;
                    valid = false;
                    reason = "Divide by 0 error";
                }else {
                    res = Optional.of(solveJob(curJob));
                }

                // calculate time it took to solve job
                long endTime = System.nanoTime();
                long solvedAt = endTime - startTime;

                // create and send solvedJob to outPipe
                SolvedJob solvedJob = new SolvedJob(curJob, res, valid, reason, solvedAt);
                outPipe.put(solvedJob);
            } catch (InterruptedException ignored) {}

        }
    }
}
