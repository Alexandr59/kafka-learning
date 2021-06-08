package task_2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ProducerRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String server = "localhost:9092";
        String topicName = "task-2";

        final Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        final KafkaProducer<Long, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 1000; ++i) {
            String message = i + 1 + "hello";
            RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord(topicName, message)).get();
            if (recordMetadata.hasOffset())
                System.out.println(message + ": sent successfully");
            TimeUnit.MILLISECONDS.sleep(100);
        }

        producer.close();
    }
}
