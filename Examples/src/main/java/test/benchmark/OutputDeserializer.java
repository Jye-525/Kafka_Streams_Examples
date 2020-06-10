package test.benchmark;

import java.util.regex.Pattern;

import org.apache.kafka.common.serialization.Deserializer;

public class OutputDeserializer implements Deserializer<OutputDataStructure>{

	@Override
    public OutputDataStructure deserialize(String topic, byte[] data) {
            if (data == null)
                return null;
            else
            {
            	String srcData = new String(data);
            	String[] fieldStrings = srcData.split(Pattern.quote("|"));
            	System.out.println(srcData + ", numbers: " + fieldStrings.length);
            	return new OutputDataStructure(fieldStrings[0], Long.parseLong(fieldStrings[1]), Long.parseLong(fieldStrings[2]), Long.parseLong(fieldStrings[3]));
            }
    }
}
