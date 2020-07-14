package test.benchmark.transform;

import org.apache.kafka.common.serialization.Deserializer;

public class TransformDataStructureDeserializer implements Deserializer<TransformDataStructure> {
	
	@Override
    public TransformDataStructure deserialize(String topic, byte[] data) {
            if (data == null)
                return null;
            else
            {
            	return new TransformDataStructure(data);
            }
    }
}
