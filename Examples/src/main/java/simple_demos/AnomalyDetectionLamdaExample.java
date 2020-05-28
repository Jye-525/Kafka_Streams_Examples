package simple_demos;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

public class AnomalyDetectionLamdaExample{
	
	public static void main(final String[] args) {
		final Properties streamsConfiguration = new Properties();
		
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "anomaly-detection-lambda-example"); // must be unique
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "anomaly-detection-lambda-example-client");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		
		streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 500);
		
		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();
		
		final StreamsBuilder builder = new StreamsBuilder();
		final KStream<String, String> viewStream = builder.stream("UserClicks");
		
		final KTable<Windowed<String>, Long> anomalousUsers = viewStream
				.map((ignoredKey, username)-> new KeyValue<>(username, username))
				.groupByKey()
				.windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
				.count()
				.filter((windowedUserId, count) -> count >= 3);
		
		final KStream<String, Long> anomalousUsersForConsole = anomalousUsers.toStream()
				.filter((windowUserId, count) -> count != null)
				.map((windowUserId, count) -> new KeyValue<>(windowUserId.toString(), count));
		
		anomalousUsersForConsole.to("AnomalousUsers", Produced.with(stringSerde, longSerde));
		
		final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
		
		streams.cleanUp();
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}