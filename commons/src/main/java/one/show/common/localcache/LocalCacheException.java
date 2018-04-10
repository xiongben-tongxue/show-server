package one.show.common.localcache;

/**
 * 2009-12-10
 * @version $Revision: 1.0 $
 */
public class LocalCacheException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 229084464194752283L;
	//
	public LocalCacheException() {
		super();
	}
	//
	/**
	 * Constructor for MemcachedException.
	 * @param msg String
	 */
	public LocalCacheException(String msg){
		super(msg);
	}
	//
	/**
	 * Constructor for MemcachedException.
	 * @param msg String
	 * @param e Throwable
	 */
	public LocalCacheException(String msg,Throwable e){
		super(msg, e);
	}
	//
	/**
	 * Constructor for MemcachedException.
	 * @param e Throwable
	 */
	public LocalCacheException(Throwable e){
		super(e);
	}
}
