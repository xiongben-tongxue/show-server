package one.show.common;


public abstract interface IpSearcher {
	public abstract void init();

	public abstract int reload();

	public abstract String search(String paramString);

	public abstract int searchNettype(String paramString);

	public abstract int searchCountry(String paramString);
}

