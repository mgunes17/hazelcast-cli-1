package command.collection.memory;

public enum MemorySizeType {
	b("byte", 1),
	kb("kilobyte", 1024),
	mb("megabyte", 1024 * 1024),
	gb("gigabyte", 1024 * 1024 * 1024);
	
	private String typeName;
	private int size;
	
	private MemorySizeType (String typeName, int size) {
		this.typeName = typeName;
		this.size = size;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int getSize() {
		return size;
	}
	
}
