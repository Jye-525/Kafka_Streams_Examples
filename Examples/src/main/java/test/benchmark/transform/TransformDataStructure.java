package test.benchmark.transform;

public class TransformDataStructure {
	private String contentString;
	private long   startNanos;
	private long   startTimeStampMs;
	private long   recordLength;
	private long   endNanos;
	private long   endTimeStampMs;
	private long   latency;
	
	public TransformDataStructure(String content) {
		contentString = content;
		startNanos = System.nanoTime();
		startTimeStampMs = System.currentTimeMillis();
		recordLength = content.length();
		endNanos = System.nanoTime();
		endTimeStampMs = System.currentTimeMillis();
		latency = 0;
	}
	
	public TransformDataStructure(byte[] data) {
		contentString = new String(data);
		startNanos = System.nanoTime();
		startTimeStampMs = System.currentTimeMillis();
		recordLength = contentString.length();
		endNanos = System.nanoTime();
		endTimeStampMs = System.currentTimeMillis();
		latency = 0;
	}
	
	public String getContent() {
		return contentString;
	}
	
	public long getStartTime() {
		return startTimeStampMs;
	}
	
	public long getRecordLength() {
		return recordLength;
	}
	
	public void setEndTime() {
		endNanos = System.nanoTime();
		endTimeStampMs = System.currentTimeMillis();
		latency = endNanos - startNanos;
	}
	
	public long getEndTime() {
		return endTimeStampMs;
	}
	
	public void transformToUppercase() {
		contentString = contentString.toUpperCase();
	}
	
	public long getLatency() {
		return latency;
	}
}
