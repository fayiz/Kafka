package co.edureka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Aride Chettali on 05-Jan-18.
 */
public class SpecificOffsetCommittingConsumer
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9102");
        props.put("group.id", "grp-1");
        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", "7000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        Map<TopicPartition, OffsetAndMetadata> currentOffset = new HashMap<>();

        consumer.subscribe(Arrays.asList("my-first-topic"));
        while (true)
        {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records)
            {
                System.out.println("=============== partition Id= " + record.partition() + "  offset = " + record.offset() + " value = " + record.value() + "=================");

                TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                long nextOffset = record.offset() + 1;
                currentOffset.put(topicPartition, new OffsetAndMetadata(nextOffset));
                consumer.commitAsync(currentOffset,null);
            }
        }
    }
}
