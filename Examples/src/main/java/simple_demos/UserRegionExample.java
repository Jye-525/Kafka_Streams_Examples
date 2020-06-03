package simple_demos;

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

// compute the user count per country from a KTable that contains <UserId, Country>

public class UserRegionExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Properties streamConfiguration = new Properties();
		streamConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-region-example");
		streamConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "user-region-example-client");
		streamConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		streamConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
		
		
		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();
		
		final StreamsBuilder builder = new StreamsBuilder();
		
		final KTable<String, String> userRegions = builder.table("UserRegion");
		final KTable<String, Long> regionCounts = userRegions.groupBy((userId, region) -> KeyValue.pair(region, region))
				.count()
				.filter((regionName, count) -> count >= 2);
		
		System.out.println(regionCounts.toStream());
		
		final KStream<String, Long> regionCountsForConsole = regionCounts.toStream()
				.filter((regionName, count) -> count != null);
		
		regionCountsForConsole.to("LargeRegions", Produced.with(stringSerde, longSerde));
		
		final KafkaStreams streams = new KafkaStreams(builder.build(), streamConfiguration);
		
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}

}
