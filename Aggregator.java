import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Aggregator implements Runnable{
    private int numSolvers;
    private StageQueue<SolvedJob> inPipe;

    private int total_rejected;

    private int total_solved;
    private long total_job_runtime;
    private HashMap<Op, Integer> counts_per_operator;

    private int min_result;
    private int max_result;

    public Aggregator(StageQueue<SolvedJob> inPipe, int numSolvers){
        this.inPipe = inPipe;
        this.numSolvers = numSolvers;
        total_job_runtime = 0;
        total_rejected = 0;
        total_solved = 0;
        counts_per_operator = new HashMap<>();
        for (Op op : Op.values()){
            counts_per_operator.put(op, 0);
        }
        min_result = 0;
        max_result = 0;
    }

    @Override
    public void run() {
        while(true){
            try {
                SolvedJob solvedJob = inPipe.take();

                //if a STOP solvedJob is received, print final result
                // then terminate
                if(solvedJob.equals(SolvedJob.STOP)){
                    System.out.printf("total_generated:  %d\n", total_solved + total_rejected);
                    System.out.printf("total_solved:  %d\n", total_solved);
                    System.out.printf("total_rejected:  %d\n", total_rejected);
                    if(total_solved != 0){
                        System.out.printf("avg_solved_job_runtime:  %x\n", total_job_runtime / total_solved);
                    }else{
                        System.out.printf("avg_solved_job_runtime:  %x\n", total_solved);
                    }
                    for (Op op : Op.values()){
                        System.out.printf("%s_total:  %d\n", op, counts_per_operator.get(op));
                    }
                    System.out.printf("min_result:  %d\n", min_result);
                    System.out.printf("max_result:  %d\n", max_result);
                    return;
                }

                if(solvedJob.valid){
                    total_solved += 1;
                    total_job_runtime += solvedJob.solvedAt;
                    counts_per_operator.put(solvedJob.job.op, counts_per_operator.get(solvedJob.job.op) + 1);
                    int res = solvedJob.result.get();
                    if(res < min_result){
                        min_result = res;
                    }else if (res > max_result){
                        max_result = res;
                    }
                }else{
                    total_rejected += 1;
                }


            } catch (InterruptedException ignored) {}
        }
    }
}
