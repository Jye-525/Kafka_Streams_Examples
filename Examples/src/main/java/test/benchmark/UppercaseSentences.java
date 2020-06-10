package test.benchmark;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;


public class UppercaseSentences {

	public static void main(String[] args) {
		final Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-uppercase-sentences");
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "stream-uppercase-sentences-client");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		
		final StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, InputDataStructure> source = builder.stream("normal-sentence-test", Consumed.with(Serdes.String(), new InputSerd()));
		
		KStream<String, OutputDataStructure> upperCasedSentence = source.mapValues(new ValueMapper<InputDataStructure, OutputDataStructure>() {
			@Override
			public OutputDataStructure apply(InputDataStructure input) {
				String upperCaseSentence = input.getSentence().toUpperCase();
				
				System.out.println("===========upper: " + upperCaseSentence + ", timeStap: " + input.getStartTimeStamp());
				return new OutputDataStructure(upperCaseSentence, input.getStartTimeStamp(), input.getSentence().length());
			}
		});
		
		upperCasedSentence.to("uppercased-sentence-test", Produced.with(Serdes.String(), new OutputSerd()));
		
		final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
		
		streams.start();
		
		System.out.println(streams.toString());
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

	}

}

