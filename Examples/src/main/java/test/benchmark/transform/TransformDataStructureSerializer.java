package test.benchmark.transform;

import org.apache.kafka.common.serialization.Serializer;

public class TransformDataStructureSerializer implements Serializer<TransformDataStructure> {
	
	private final String sepapatorString = "#";
	@Override
    public byte[] serialize(String topic, TransformDataStructure data) {
        if (data == null)
            return null;
        else {
        	StringBuilder stringBuilder = new StringBuilder();
        	stringBuilder.append(data.getContent());
        	stringBuilder.append(sepapatorString);
        	stringBuilder.append(data.getStartTime());
        	stringBuilder.append(sepapatorString);
        	data.setEndTime();
        	stringBuilder.append(data.getEndTime());
        	stringBuilder.append(sepapatorString);
        	stringBuilder.append(data.getLatency());
        	stringBuilder.append(sepapatorString);
        	stringBuilder.append(data.getRecordLength());
        	return stringBuilder.toString().getBytes();
        }
    }
}
