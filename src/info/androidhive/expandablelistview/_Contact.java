package info.androidhive.expandablelistview;

public class _Contact
{

	// private variables
	public int _id;
	public String _user;
	public String _pwd;
	public String _lock;

	public _Contact()
	{
	}

	// constructor
	public _Contact(int id, String user, String pwd, String lock)
	{
		this._id = id;
		this._user = user;
		this._pwd = pwd;
		this._lock = lock;
	}

	// constructor
	public _Contact(String user, String pwd, String lock)
	{
		this._user = user;
		this._pwd = pwd;
		this._lock = lock;
	}

	public int getID()
	{
		return this._id;
	}

	public void setID(int id)
	{
		this._id = id;
	}

	public String getUser()
	{
		return this._user;
	}

	public void setUser(String user)
	{
		this._user = user;
	}

	public String getPwd()
	{
		return this._pwd;
	}

	public void setPwd(String pwd)
	{
		this._pwd = pwd;
	}

	public String getLock()
	{
		return this._lock;
	}

	public void setLock(String lock)
	{
		this._lock = lock;
	}

}