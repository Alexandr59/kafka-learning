package task_1;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConsumerRunner {
    public static void main(String[] args) {
        String server = "localhost:9092";
        String topicName = "myTopic";
        String groupName = "test.group";

        final Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                groupName);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());

        final Consumer<Long, Integer> consumer = new KafkaConsumer<>(props);

        TopicPartition tp = new TopicPartition(topicName, 0);
        List<TopicPartition> tps = Collections.singletonList(tp);
        consumer.assign(tps);
        consumer.seekToBeginning(tps);

        ConsumerRecords<Long, Integer> consumerRecords = consumer.poll(30000);

        int sum = 0;

        for (ConsumerRecord<Long, Integer> consumerRecord : consumerRecords) {
            sum += consumerRecord.value();
        }

        System.out.println("sum of fibonacci numbers: " + sum);

        consumer.close();
    }
}
