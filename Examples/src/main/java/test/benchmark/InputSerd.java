package test.benchmark;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class InputSerd implements Serde<InputDataStructure> {
	
	@Override
	public Serializer<InputDataStructure> serializer(){
		return new InputSerializer();
	}

	@Override
    public Deserializer<InputDataStructure> deserializer(){
    	return new InputDeserialzer();
    }
}
