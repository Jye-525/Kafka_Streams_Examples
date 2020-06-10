package test.benchmark;


import org.apache.kafka.common.serialization.Serializer;

public class InputSerializer implements Serializer<InputDataStructure> {

	@Override
    public byte[] serialize(String topic, InputDataStructure data) {
        if (data == null)
            return null;
        else
            return data.getSentence().getBytes();
    }
}
