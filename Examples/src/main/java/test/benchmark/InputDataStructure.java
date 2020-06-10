package test.benchmark;

public class InputDataStructure {
	private String sentence;
	private long   startTimeStamp;
	
	public InputDataStructure(String sentence) {
		this.sentence = sentence;
		startTimeStamp = System.currentTimeMillis();
	}
	
	
	public String getSentence() {
		return sentence;
	}
	
	public long getStartTimeStamp() {
		return startTimeStamp;
	}
}
