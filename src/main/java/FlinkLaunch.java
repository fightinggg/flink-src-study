import org.apache.flink.runtime.entrypoint.StandaloneSessionClusterEntrypoint;
import org.apache.flink.runtime.taskexecutor.TaskManagerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FlinkLaunch {
    protected static final Logger LOG = LoggerFactory.getLogger(FlinkLaunch.class);

    public static void main(String[] args) throws InterruptedException {
        LOG.info("app begin...");

        final Thread task = new Thread(FlinkLaunch::taskManager);
        final Thread job = new Thread(FlinkLaunch::jobManager);
        job.start();
        Thread.sleep(10000);
        task.start();

        task.join();
        job.join();
    }

    public static void jobManager() {
        StandaloneSessionClusterEntrypoint.main(jobManagerCmd.split(" "));
    }

    public static void taskManager() {
        try {
            TaskManagerRunner.main(taskManagerCmd.split(" "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String jobManagerCmd =
            "--configDir flink/flink-dist/src/main/resources "
                    + "--executionMode cluster "
                    + "-D jobmanager.memory.off-heap.size=134217728b "
                    + "-D jobmanager.memory.jvm-overhead.min=201326592b "
                    + "-D jobmanager.memory.jvm-metaspace.size=268435456b "
                    + "-D jobmanager.memory.heap.size=1073741824b "
                    + "-D jobmanager.memory.jvm-overhead.max=201326592b";

    static String taskManagerCmd =
            "--configDir flink/flink-dist/src/main/resources "
                    + "-D external-resources=none "

                    //框架堆上内存Framework Heap Memory
                    + "-D taskmanager.memory.framework.heap.size=134217728b "

                    //Task堆上内存Task Heap Memory
                    + "-D taskmanager.memory.task.heap.size=402653174b "

                    //框架堆外内存Framework Off-Heap Memory
                    + "-D taskmanager.memory.framework.off-heap.size=134217728b "

                    //Task堆外内存Task Off-Heap Memory
                    + "-D taskmanager.memory.task.off-heap.size=0b "

                    //网络缓冲内存Network Memory
                    + "-D taskmanager.memory.network.min=134217730b "
                    + "-D taskmanager.memory.network.max=134217730b "

                    + "-D taskmanager.memory.managed.size=536870920b "

                    //JVM元空间所使用的内存。
                    + "-D taskmanager.memory.jvm-metaspace.size=268435456b "

                    //JVM执行开销
                    + "-D taskmanager.memory.jvm-overhead.min=201326592b "
                    + "-D taskmanager.memory.jvm-overhead.max=201326592b"

                    + "-D taskmanager.numberOfTaskSlots=1 "
                    + "-D taskmanager.cpu.cores=1.0 ";
}
