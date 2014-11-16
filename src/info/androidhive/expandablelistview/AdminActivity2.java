package info.androidhive.expandablelistview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity2 extends Activity
{
	EditText add_user, add_pwd;
	Button add_save_btn, add_view_all, update_btn, update_view_all;
	LinearLayout add_view, update_view;
	String valid_user = null,valid_pwd = null, Toast_msg = null;
	int USER_ID;
	_DatabaseHandler dbHandler = new _DatabaseHandler(this);
	
	ArrayList<_Contact> contact_data = new ArrayList<_Contact>();
	_DatabaseHandler db;
	String ownUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin2);

		TextView logout = (TextView) findViewById(R.id.logout2);
		logout.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				finish();
			}
		});
		
		ownUser="";
		Get_Data_From_DB();

		// set screen
		Set_Add_Update_Screen();

		// set visibility of view as per calling activity
		String called_from = getIntent().getStringExtra("called");

		if (called_from.equalsIgnoreCase("add"))
		{
			add_view.setVisibility(View.VISIBLE);
			update_view.setVisibility(View.GONE);
		}
		else
		{

			update_view.setVisibility(View.VISIBLE);
			add_view.setVisibility(View.GONE);
			USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

			_Contact c = dbHandler.Get_Contact(USER_ID);

			add_user.setText(c.getUser());
			add_pwd.setText(c.getPwd());
			ownUser=c.getUser();
			
			valid_user = add_user.getText().toString();
			valid_pwd= add_pwd.getText().toString();

		}
		
		
		
		add_user.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				Is_Valid_User(add_user);
			}
		});
		add_pwd.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				// min lenth 10 and max lenth 12 (2 extra for - as per phone
				// matcher format)
				Is_Valid_Pwd(add_pwd);
			}
		});
		

		

		add_save_btn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// check the value state is null or not
				if (valid_pwd != null && valid_user != null
						&& valid_pwd.length() != 0 && valid_user.length() != 0)
				{

					dbHandler.Add_Contact(new _Contact(valid_user,valid_pwd,
							"0"));
					
					//Reset_Text();
					
					Intent view_user = new Intent(AdminActivity2.this,
							AdminActivity1.class);
					view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(view_user);
					finish();
					
					Toast_msg = "成功添加用户";
					Show_Toast(Toast_msg);

				}
				else
				{
					Toast_msg = "用户信息不正确\n请重新填写";
					Show_Toast(Toast_msg);
				}

			}
		});

		update_btn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub



				// check the value state is null or not
				if (valid_pwd != null && valid_user != null
						&& valid_pwd.length() != 0 && valid_user.length() != 0)
				{

					dbHandler.Update_Contact(new _Contact(USER_ID, valid_user,valid_pwd,
							 "0"));
					dbHandler.close();
			
					
					Intent view_user = new Intent(AdminActivity2.this,
							AdminActivity1.class);
					view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(view_user);
					finish();
					
					Toast_msg = "成功更新用户";
					Show_Toast(Toast_msg);
				}
				else
				{
					Toast_msg = "用户信息不正确\n请重新填写";
					Show_Toast(Toast_msg);
				}

			}
		});
		update_view_all.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent view_user = new Intent(AdminActivity2.this,
						AdminActivity1.class);
				view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(view_user);
				finish();
			}
		});

		add_view_all.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent view_user = new Intent(AdminActivity2.this,
						AdminActivity1.class);
				view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(view_user);
				finish();
			}
		});

	}

	public void Set_Add_Update_Screen()
	{

		add_user = (EditText) findViewById(R.id.add_name);
		add_pwd = (EditText) findViewById(R.id.add_mobile);

		add_save_btn = (Button) findViewById(R.id.add_save_btn);
		update_btn = (Button) findViewById(R.id.update_btn);
		add_view_all = (Button) findViewById(R.id.add_view_all);
		update_view_all = (Button) findViewById(R.id.update_view_all);

		add_view = (LinearLayout) findViewById(R.id.add_view);
		update_view = (LinearLayout) findViewById(R.id.update_view);

		add_view.setVisibility(View.GONE);
		update_view.setVisibility(View.GONE);

	}


	
	public void Is_Valid_User(EditText edt) throws NumberFormatException
	{
		
		if (edt.getText().toString().length() <= 0)
		{
			edt.setError("用户名不能为空");
			valid_user = null;
		}
		else if(edt.getText().toString().equals("admin"))
		{
			edt.setError("用户名不能为admin");
			valid_user = null;
		}
		else if(isUserDuplicate(edt.getText().toString())&&!ownUser.equals(edt.getText().toString()))
		{
			edt.setError("该用户名已存在");
			valid_user = null;
		}
		else if (!edt.getText().toString().matches("[a-zA-Z ]+"))
		{
			edt.setError("用户名只能为字母");
			valid_user = null;
		}
		else
		{
			valid_user = edt.getText().toString();
		}

	}

	public void Is_Valid_Pwd(EditText edt) throws NumberFormatException
	{
		if (edt.getText().toString().length() <= 0)
		{
			edt.setError("密码不能为空");
			valid_pwd = null;
		}
		else if (!edt
				.getText()
				.toString()
				.matches(
						"(?=(.*\\d){1})(?=.*[a-zA-Z])(?=.*[!@#$%&\\^\\*\\(\\)])[0-9a-zA-Z!@#$%&\\^\\*\\(\\)]{9,}"))
		{
			edt.setError("密码至少含9位数字+字母+符号混编,且符号只能为!@#$%^&*()");
			valid_pwd = null;
		}
		else
		{
			valid_pwd = edt.getText().toString();
		}

	}

	

	public void Show_Toast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}


	
	public boolean isUserDuplicate(String user)
	{
		boolean found = false;
		for (int i = 0; i < contact_data.size(); i++)
		{
			if (contact_data.get(i).getUser().equals(user))
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
