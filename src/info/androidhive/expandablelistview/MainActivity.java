package info.androidhive.expandablelistview;

import info.androidhive.expandablelistview.AdminActivity1.Contact_Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
{

	ArrayList<_Contact> contact_data = new ArrayList<_Contact>();
	_DatabaseHandler db;

	String lastUser = "";
	int tolerantCounter = 0;

	
	_DatabaseHandler dbHandler = new _DatabaseHandler(this);
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Get_Data_From_DB();
		
		Button btnUpdatePWD = (Button) findViewById(R.id.btnUpdatePWD);

		btnUpdatePWD.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{

				Get_Data_From_DB();

				EditText txtUser = (EditText) findViewById(R.id.user);
				EditText txtPwd = (EditText) findViewById(R.id.pwd);

				if (isAdmin(txtUser.getText().toString(), txtPwd.getText()
						.toString()))
				{
					Intent intent = new Intent(v.getContext(),
							AdminActivity1.class);
					startActivity(intent);
				}
				else
				{
					if (isUserCorrect(txtUser.getText().toString()))
					{
						
						if(isLock(txtUser.getText().toString()))
						{
							Toast.makeText(getApplicationContext(),
									"您的账号已被锁定，请联系管理员解锁",
									Toast.LENGTH_LONG).show();
						}
						else
						{

							if (bothUserPwdCorrect(txtUser.getText().toString(),
									txtPwd.getText().toString()))
							{
								tolerantCounter = 0;
								lastUser = "";
								Intent intent = new Intent(v.getContext(),
										UpdatePwdActivity.class);
								intent.putExtra("user_key", txtUser.getText().toString());
								
								startActivity(intent);
							}
							else
							{
								if (lastUser.equals(txtUser.getText().toString()))
									tolerantCounter++;
								else
									tolerantCounter = 0;
								
								if(tolerantCounter==2)
								{
									doLock(txtUser.getText().toString());
									Toast.makeText(getApplicationContext(),
											"3次输入密码错误，您已被锁定，请联系管理员解锁",
											Toast.LENGTH_LONG).show();
								}
								else
									Toast.makeText(getApplicationContext(),
										"密码有误，您还有" + (2 - tolerantCounter) + "次机会",
										Toast.LENGTH_LONG).show();
								
								
								lastUser = txtUser.getText().toString();
							}
						}
						
					}
					else if(txtUser.getText().toString().equals("admin"))
					{
						Toast.makeText(getApplicationContext(),
								"管理员密码有误", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(getApplicationContext(),
								"系统无该用户名，请重新输入", Toast.LENGTH_LONG).show();
					}

					

				}

			
				
				
				
			}
		});

		Button btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Get_Data_From_DB();

				EditText txtUser = (EditText) findViewById(R.id.user);
				EditText txtPwd = (EditText) findViewById(R.id.pwd);

				if (isAdmin(txtUser.getText().toString(), txtPwd.getText()
						.toString()))
				{
					Intent intent = new Intent(v.getContext(),
							AdminActivity1.class);
					startActivity(intent);
				}
				else
				{
					if (isUserCorrect(txtUser.getText().toString()))
					{
						
						if(isLock(txtUser.getText().toString()))
						{
							Toast.makeText(getApplicationContext(),
									"您的账号已被锁定，请联系管理员解锁",
									Toast.LENGTH_LONG).show();
						}
						else
						{

							if (bothUserPwdCorrect(txtUser.getText().toString(),
									txtPwd.getText().toString()))
							{
								tolerantCounter = 0;
								lastUser = "";
								Intent intent = new Intent(v.getContext(),
										ContentActivity.class);
								startActivity(intent);
							}
							else
							{
								if (lastUser.equals(txtUser.getText().toString()))
									tolerantCounter++;
								else
									tolerantCounter = 0;
								
								if(tolerantCounter==2)
								{
									doLock(txtUser.getText().toString());
									Toast.makeText(getApplicationContext(),
											"3次输入密码错误，您已被锁定，请联系管理员解锁",
											Toast.LENGTH_LONG).show();
								}
								else
									Toast.makeText(getApplicationContext(),
										"密码有误，您还有" + (2 - tolerantCounter) + "次机会",
										Toast.LENGTH_LONG).show();
								
								
								lastUser = txtUser.getText().toString();
							}
						}
						
					}
					else if(txtUser.getText().toString().equals("admin"))
					{
						Toast.makeText(getApplicationContext(),
								"管理员密码有误", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(getApplicationContext(),
								"系统无该用户名，请重新输入", Toast.LENGTH_LONG).show();
					}

					

				}

			}
		});

	}
	
	public boolean isLock(String _user)
	{
		boolean lock=false;
		for (int i = 0; i < contact_data.size(); i++)
		{
			if(contact_data.get(i).getUser().equals(_user))
			{
				if(contact_data.get(i).getLock().equals("1"))
					lock=true;
				else if(contact_data.get(i).getLock().equals("0"))
					lock=false;
				else
				{}
			}
		}
		return lock;
	}
	public void doLock(String _user)
	{
		_Contact cnt = new _Contact();
		for (int i = 0; i < contact_data.size(); i++)
		{
			if(contact_data.get(i).getUser().equals(_user))
			{

				int tidno = contact_data.get(i).getID();
				String user = contact_data.get(i).getUser();
				String pwd = contact_data.get(i).getPwd();
			
				
				cnt.setID(tidno);
				cnt.setUser(user);
				cnt.setPwd(pwd);
				cnt.setLock("1");
	
			}
		}
		
		dbHandler.Update_Contact_For_Lock(cnt);
		
		Get_Data_From_DB();
	}

	public boolean isAdmin(String user, String pwd)
	{
		return user.equals("admin") && pwd.equals("12345");
	}

	public boolean isUserCorrect(String user)
	{
		boolean found = false;
		for (int i = 0; i < contact_data.size(); i++)
		{
			if (contact_data.get(i).getUser().equals(user))
				found = true;
		}
		return found;
	}

	public boolean bothUserPwdCorrect(String user, String pwd)
	{
		boolean found = false;
		for (int i = 0; i < contact_data.size(); i++)
		{
			if (contact_data.get(i).getUser().equals(user)
					&& contact_data.get(i).getPwd().equals(pwd))
				found = true;
		}
		return found;
	}

	public void Get_Data_From_DB()
	{
		contact_data.clear();
		db = new _DatabaseHandler(this);
		ArrayList<_Contact> contact_array_from_db = db.Get_Contacts();

		for (int i = 0; i < contact_array_from_db.size(); i++)
		{

			int tidno = contact_array_from_db.get(i).getID();
			String user = contact_array_from_db.get(i).getUser();
			String pwd = contact_array_from_db.get(i).getPwd();
			String lock = contact_array_from_db.get(i).getLock();
			_Contact cnt = new _Contact();
			cnt.setID(tidno);
			cnt.setUser(user);
			cnt.setPwd(pwd);
			cnt.setLock(lock);

			contact_data.add(cnt);
		}
		db.close();

	}

}