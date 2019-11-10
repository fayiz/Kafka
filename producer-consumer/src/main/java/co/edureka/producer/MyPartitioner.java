package co.edureka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Aride Chettali on 06-Jan-18.
 */
public class MyPartitioner implements Partitioner
{

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster)
    {
        int partition;
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numOfpartitions = partitions.size();
        String productName = (String) key;
        if(productName.equalsIgnoreCase("calacs"))
            partition = numOfpartitions - 1;
        else
            partition = (key.hashCode() % (numOfpartitions-1));

        System.out.println("custom partitioner returned : "+ partition);
        return partition;
    }

    @Override
    public void close()
    {

    }

    @Override
    public void configure(Map<String, ?> map)
    {

    }
}