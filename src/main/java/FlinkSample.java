import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.examples.wordcount.WordCount;

public class FlinkSample {
    public static void main(String[] args) throws Exception {
        WordCount.main(args);
//        DataStream<String> s;
//        s.keyBy().window()
    }
}
