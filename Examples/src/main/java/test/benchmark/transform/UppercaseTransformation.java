package test.benchmark.transform;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

public class UppercaseTransformation {
	private static String bootstrapServerString = "localhost:9092";
	private static String inputTopic = "normal-sentence";
	private static String outputTopic = "transform-result";
	private static int threadNum = 1;
	
	public static void main(String[] args) {
		
		if (args.length < 4) {
			System.out.println("arguments: bootstrap_servers input_topic output_topic threadNum");
			return;
		}
		else {
			bootstrapServerString = args[0];
			inputTopic = args[1];
			outputTopic = args[2];
			int num = Integer.parseInt(args[3]);
			if (num > 0) {
				threadNum = num;
			}
		}
		
		final Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "transform-test");
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "transform-test-client");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerString);
		streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, threadNum);
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		//streamsConfiguration.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");
		//streamsConfiguration.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 16348);
		//streamsConfiguration.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.RoundRobinPartitioner");
		//streamsConfiguration.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);
		//streamsConfiguration.put(ProducerConfig.LINGER_MS_CONFIG, 0);
		
		final StreamsBuilder builder = new StreamsBuilder();
		KStream<String, TransformDataStructure> source = builder.stream(inputTopic, Consumed.with(Serdes.String(), new TransformDataStructureSerd()));
		
		KStream<String, TransformDataStructure> upperCasedSentence = source.mapValues(input-> {input.transformToUppercase(); return input;});
		
		upperCasedSentence.to(outputTopic, Produced.with(Serdes.String(), new TransformDataStructureSerd()));
		
		Topology topology = builder.build();
		
		final KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
		
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}

}
