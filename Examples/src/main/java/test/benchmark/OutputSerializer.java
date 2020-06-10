package test.benchmark;

import org.apache.kafka.common.serialization.Serializer;

public class OutputSerializer implements Serializer<OutputDataStructure> {
	
	@Override
    public byte[] serialize(String topic, OutputDataStructure data) {
        if (data == null)
            return null;
        else
        {
        	String result = data.getSentence() + "|" + data.getStartTimeStamp() + "|" + data.getEndTimeStamp() + "|" + data.getMsgBytesLength();
        	System.out.println(result);
            return result.getBytes();
        }
    }
}
