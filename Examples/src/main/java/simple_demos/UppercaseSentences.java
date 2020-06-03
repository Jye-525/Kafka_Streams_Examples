package simple_demos;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class UppercaseSentences {

	public static void main(String[] args) {
		final Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-uppercase-sentences");
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "stream-uppercase-sentences-client");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		
		final StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, String> source = builder.stream("normal-sentence");
		
		KStream<String, String> upperCasedSentence = source.mapValues(v->v.toUpperCase());
		
		upperCasedSentence.to("uppercased-sentence");
		
		final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
		
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

	}

}
