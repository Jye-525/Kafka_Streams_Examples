package test.benchmark;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class OutputSerd implements Serde<OutputDataStructure> {

	@Override
	public Serializer<OutputDataStructure> serializer() {
		return new OutputSerializer();
	}

	@Override
	public Deserializer<OutputDataStructure> deserializer() {
		return new OutputDeserializer();
	}
}
