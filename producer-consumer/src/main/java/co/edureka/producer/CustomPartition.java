package co.edureka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Aride Chettali on 06-Jan-18.
 */
public class CustomPartition {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9101");
        props.put("acks", "1");  //"0" -No ack, "1" only Leader ,"all" ALL
        props.put("retries", 0);  // "0" doesn't re try ; positive value will retry
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", MyPartitioner.class.getCanonicalName());

        Producer<String, String> producer = new KafkaProducer<>(props);
        try
        {
            ProducerRecord<String, String> record = new ProducerRecord<>("my-first-topic", "calacs", "my message from java");
            producer.send(record);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Thread.sleep(500);

        producer.close();
        System.out.println("message published");
    }
}