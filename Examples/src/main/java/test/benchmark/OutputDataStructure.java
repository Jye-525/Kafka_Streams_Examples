package test.benchmark;

public class OutputDataStructure {
	private String sentence;
	private long   startTimeStamp;
	private long   endTimeStamp;
	private long   messageByteLength;
	
	public OutputDataStructure(String sentence, long startTimeStampLong, long msgLength) {
		this.sentence = sentence; 
		this.startTimeStamp = startTimeStampLong;
		this.endTimeStamp = System.currentTimeMillis();
		this.messageByteLength = msgLength;
	}
	
	public OutputDataStructure(String sentence, long startTimeStampLong, long endTimeStampLong, long msgLength) {
		this.sentence = sentence; 
		this.startTimeStamp = startTimeStampLong;
		this.endTimeStamp = endTimeStampLong;
		this.messageByteLength = msgLength;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public long getStartTimeStamp() {
		return startTimeStamp;
	}
	
	public long getEndTimeStamp() {
		return endTimeStamp;
	}
	
	public long getMsgBytesLength() {
		return messageByteLength;
	}
}
