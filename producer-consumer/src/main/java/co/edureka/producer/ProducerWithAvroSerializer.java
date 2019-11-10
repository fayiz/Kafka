package co.edureka.producer;

import co.edureka.avro.Student;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Created by Aride on 19-03-2017.
 */
public class ProducerWithAvroSerializer {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9101");
        props.put("acks", "1");  //"0" -No ack, "1" only Leader ,"all" ALL
        props.put("retries", 0);  // "0" doesn't re try ; positive value will retry
        props.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("schema.registry.url", "http://192.168.56.101:8081");

        Producer<String, Student> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            Student s = new Student(i, "Student Name " + i,"student.name."+i+"@abcd.com");
            ProducerRecord<String, Student> record = new ProducerRecord<>("student-record", "my-key" + i, s);
            producer.send(record);
            Thread.sleep(500);
        }

        producer.close();
        System.out.println("message published");
    }
}