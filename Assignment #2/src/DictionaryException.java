
public class DictionaryException extends RuntimeException {

	public DictionaryException()
	{
		super ("Error: Unable to insert pre-existing key or remove non-existing key from dictionary");
	}
}
