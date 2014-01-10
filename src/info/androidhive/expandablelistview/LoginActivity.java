package info.androidhive.expandablelistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// TextView registerScreen = (TextView)
		// findViewById(R.id.link_to_register);

		Button button = (Button) findViewById(R.id.btnLogin);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				EditText text1 = (EditText) findViewById(R.id.user);
				EditText text2 = (EditText) findViewById(R.id.pwd);
				if (text1.getText().toString().equals("user")
						&& text2.getText().toString().equals("123456")) {
					Intent intent = new Intent(v.getContext(), MyActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "用户名密码错误，请重新输入",
							   Toast.LENGTH_LONG).show();
					
				
				}

			}
		});

		// public void Login(View v){
		// EditText text1 = (EditText)findViewById(R.id.username);
		// EditText text2 = (EditText)findViewById(R.id.password);
		// if(text1.getText().toString().equals("yaya") &&
		// text2.getText().toString().equals("123456")){
		// Intent intent = new Intent(this,MyActivity.class);
		// String user = text1.getText().toString();
		// intent.putExtra(MainActivity.USER_NAME, user+"欢迎你");
		// startActivity(intent);
		// }

		// }

	}
}