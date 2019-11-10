package co.edureka.consumer;

import co.edureka.avro.Student;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Aride Chettali on 06-Jan-18.
 */
public class ConsumerWithAvroDeSerializer
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9102");
        props.put("group.id", "grp-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "7000");
        props.put("key.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", "http://192.168.56.101:8081");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        KafkaConsumer<String, Student> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("student-record"));
        while (true)
        {
            ConsumerRecords<String, Student> records = consumer.poll(1000);
            for (ConsumerRecord<String, Student> record : records)
            {
                Student s = record.value();
                System.out.println("Student record -> [Id: "+ s.getId() + ", Name: " + s.getName() + ", email: " + s.getEmailAddress()+ "]");

            }
        }
    }
}
