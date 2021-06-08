package task_2;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConsumerGroup {

    private static void runConsumer(String nameConsumer, String topic, String group, String fileName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        KafkaConsumer<Long, String> consumer = new KafkaConsumer<Long, String>(props);

        consumer.subscribe(Arrays.asList(topic));

        try (FileWriter writer = new FileWriter(fileName, false)) {
            while (true) {
                ConsumerRecords<Long, String> records = consumer.poll(100);
                for (ConsumerRecord<Long, String> record : records) {
                    writer.write("consumer name = " + nameConsumer + ", offset = " + record.offset() + ", key = " + record.key() +
                            ", value = " + record.value() + ", partition number = " + record.partition() + "\n");
                    writer.flush();
                }
            }
        } catch (IOException ex) {
            consumer.close();
        }
    }

    static class Consumer extends Thread {
        private final String name;
        private final String topic;
        private final String group;
        private final String fileName;

        public Consumer(String name, String topic, String group, String fileName) {
            this.name = name;
            this.topic = topic;
            this.group = group;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            runConsumer(name, topic, group, fileName);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String firstName = "First Consumer";
        String secondName = "Second Consumer";
        String topic = "task-2";
        String group = "task-2.group";
        String firstFileName = "src/main/resources/task_2/first_consumer.txt";
        String SecondFileName = "src/main/resources/task_2/second_consumer.txt";

        Consumer first = new Consumer(firstName, topic, group, firstFileName);
        first.start();

        TimeUnit.MILLISECONDS.sleep(20000);

        Consumer second = new Consumer(secondName, topic, group, SecondFileName);
        second.start();
    }
}
