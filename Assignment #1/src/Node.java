
public class Node {
	
	// Class Parameters
	public Node next;
    private Data record;

    
	// Constructor
	public Node(Node next, Data record) {
		this.setNext(next);
		this.setRecord(record);
	}

	// Generic Mutator and Accessor Functions
	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}	
	
	public Data getRecord() {
		return record;
	}

	public void setRecord(Data record) {
		this.record = record;
	}


}
