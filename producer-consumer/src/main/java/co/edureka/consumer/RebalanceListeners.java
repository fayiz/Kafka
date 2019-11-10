package co.edureka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by Aride Chettali on 05-Jan-18.
 */
public class RebalanceListeners
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9102");
        props.put("group.id", "grp-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "7000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("my-first-topic"),new HandleRebalance());
        while (true)
        {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records)
            {
                System.out.println("=============== partition Id= " + record.partition() + "  offset = " + record.offset() + " value = " + record.value() + "=================");
            }
        }

    }

    private static class HandleRebalance implements ConsumerRebalanceListener
    {

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> collection)
        {
            for(TopicPartition partition : collection)
                System.out.println("partition "+ partition.partition() + " revoked from me");
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> collection)
        {
            for(TopicPartition partition : collection)
                System.out.println("partition " + partition.partition() + " assigned to me");
        }
    }

}
