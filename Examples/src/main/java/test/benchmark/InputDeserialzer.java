package test.benchmark;

import org.apache.kafka.common.serialization.Deserializer;

public class InputDeserialzer implements Deserializer<InputDataStructure> {
	@Override
    public InputDataStructure deserialize(String topic, byte[] data) {
            if (data == null)
                return null;
            else
            {
            	String sentence = new String(data);
            	return new InputDataStructure(sentence);
            }
    }
}
