
public class InexistentKeyException extends RuntimeException {
	public InexistentKeyException()
	{
		super ("Error: Key value does not exist in dictionary");
	}
}
