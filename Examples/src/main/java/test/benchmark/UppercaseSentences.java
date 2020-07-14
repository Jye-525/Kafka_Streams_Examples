package test.benchmark;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;

import test.clients.OutputDataStructure;
import test.clients.OutputSerd;


public class UppercaseSentences {

	public static void main(String[] args) {
		final Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-uppercase-sentences");
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "stream-uppercase-sentences-client");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		
		final StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, DataStructure> source = builder.stream("normal-sentence-test", Consumed.with(Serdes.String(), new DataStructureSerd()));
		
		KStream<String, DataStructure> upperCasedSentence = source.mapValues(new ValueMapper<DataStructure, DataStructure>() {
			@Override
			public DataStructure apply(DataStructure input) {
				
				input.toUpperCase();
				System.out.println("During the first map===========upper: " + input.getSentence() + ", timeStap: " + input.getStartTimeStamp());
				return input;
			}
		});
				
		KStream<String, DataStructure> upperCasedSentence1 = upperCasedSentence.mapValues(new ValueMapper<DataStructure, DataStructure>() {
			@Override
			public DataStructure apply(DataStructure input) {
				
				input.addCharacterToSentence("######");
				System.out.println("During the second map===========upper: " + input.getSentence() + ", timeStap: " + input.getStartTimeStamp());
				return input;
			}
		});
		
		upperCasedSentence1.to("uppercased-sentence-test", Produced.with(Serdes.String(), new DataStructureSerd()));
		
		Topology topology = builder.build();
		
		final KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
		
		streams.start();
		
		System.out.println(topology.describe());
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

	}

}

