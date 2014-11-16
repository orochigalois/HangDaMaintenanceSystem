package info.androidhive.expandablelistview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePwdActivity extends Activity
{
	
	EditText edit_mypwd,edit_mypwd_again;

	String valid_pwd=null,valid_pwd_again=null,Toast_msg = null;
	
	_DatabaseHandler dbHandler = new _DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepwd);

		TextView logout = (TextView) findViewById(R.id.logout2);
		logout.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				finish();
			}
		});
		
		
		edit_mypwd = (EditText) findViewById(R.id.mypwd);
		edit_mypwd_again = (EditText) findViewById(R.id.mypwd_again);
		
		edit_mypwd.addTextChangedListener(new TextWatcher()
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
				Is_Valid_Pwd(edit_mypwd);
			}
		});
		edit_mypwd_again.addTextChangedListener(new TextWatcher()
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
				Is_Valid_Pwd_Again(edit_mypwd_again);
			}
		});
		
		Button btnMypwd_save = (Button) findViewById(R.id.mypwd_save_btn);

		btnMypwd_save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (valid_pwd != null && valid_pwd_again != null
						&& valid_pwd.length() != 0 && valid_pwd_again.length() != 0)
				{
					
					String user = getIntent().getStringExtra("user_key");

					dbHandler.UpdatePWD(user,valid_pwd);
					
			
					
					Intent view_user = new Intent(UpdatePwdActivity.this,
							MainActivity.class);
					view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(view_user);
					finish();
					
					Toast_msg = "成功更新密码";
					Show_Toast(Toast_msg);
				}
				else
				{
					Toast_msg = "密码不正确\n请重新填写";
					Show_Toast(Toast_msg);
				}
				
			}
		});
		
		Button btnMypwd_clear_all = (Button) findViewById(R.id.mypwd_clear_all);

		btnMypwd_clear_all.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				edit_mypwd.setText("");
				edit_mypwd_again.setText("");
			}
		});
		

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
	
	public void Is_Valid_Pwd_Again(EditText edt) throws NumberFormatException
	{
		if (edt.getText().toString().length() <= 0)
		{
			edt.setError("密码不能为空");
			valid_pwd_again = null;
		}
		else if(!edt.getText().toString().equals(edit_mypwd.getText().toString()))
		{
			edt.setError("两次输入密码不一致");
			valid_pwd_again = null;
		}
		else if (!edt
				.getText()
				.toString()
				.matches(
						"(?=(.*\\d){1})(?=.*[a-zA-Z])(?=.*[!@#$%&\\^\\*\\(\\)])[0-9a-zA-Z!@#$%&\\^\\*\\(\\)]{9,}"))
		{
			edt.setError("密码至少含9位数字+字母+符号混编,且符号只能为!@#$%^&*()");
			valid_pwd_again = null;
		}
		else
		{
			valid_pwd_again = edt.getText().toString();
		}

	}
	public void Show_Toast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

}
