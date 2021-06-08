package task_1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ProducerRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String server = "localhost:9092";
        String topicName = "myTopic";

        final Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());

        final KafkaProducer<Long, Integer> producer = new KafkaProducer<>(props);

        System.out.println("enter the number of fibonacci numbers");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] arr = new int[n];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < arr.length; ++i) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        for (int j : arr) {
            RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord(topicName, j)).get();
            if (recordMetadata.hasOffset())
                System.out.println(j + ": sent successfully");
        }

        producer.close();
    }
}
