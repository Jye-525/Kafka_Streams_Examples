package test.benchmark.transform;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TransformDataStructureSerd implements Serde<TransformDataStructure> {
	@Override
	public Serializer<TransformDataStructure> serializer() {
		return new TransformDataStructureSerializer();
	}

	@Override
	public Deserializer<TransformDataStructure> deserializer() {
		return new TransformDataStructureDeserializer();
	}
}
