package info.androidhive.expandablelistview;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity1 extends Activity
{
	Button add_btn;
	ListView Contact_listview;
	ArrayList<_Contact> contact_data = new ArrayList<_Contact>();
	Contact_Adapter cAdapter;
	_DatabaseHandler db;
	String Toast_msg;

	
	_DatabaseHandler _dbHandler = new _DatabaseHandler(this);
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin1);

		TextView logout = (TextView) findViewById(R.id.logout1);
		logout.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				finish();
			}
		});

		try
		{
			Contact_listview = (ListView) findViewById(R.id.list);
			Contact_listview.setItemsCanFocus(false);
			add_btn = (Button) findViewById(R.id.add_btn);

			Set_Referash_Data();

		}
		catch (Exception e)
		{
			// TODO: handle exception
			Log.e("some error", "" + e);
		}
		add_btn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent add_user = new Intent(AdminActivity1.this,
						AdminActivity2.class);
				add_user.putExtra("called", "add");
				add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(add_user);
				finish();
			}
		});

	}

	public void Set_Referash_Data()
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
		cAdapter = new Contact_Adapter(AdminActivity1.this,
				R.layout._listview_row, contact_data);
		Contact_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
	}

	public void Show_Toast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		Set_Referash_Data();

	}

	public class Contact_Adapter extends ArrayAdapter<_Contact>
	{
		Activity activity;
		int layoutResourceId;
		_Contact user;
		ArrayList<_Contact> data = new ArrayList<_Contact>();

		public Contact_Adapter(Activity act, int layoutResourceId,
				ArrayList<_Contact> data)
		{
			super(act, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.activity = act;
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = convertView;
			UserHolder holder = null;

			if (row == null)
			{
				LayoutInflater inflater = LayoutInflater.from(activity);

				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new UserHolder();
				holder.user = (TextView) row.findViewById(R.id.user_name_txt);
				holder.pwd = (TextView) row.findViewById(R.id.user_pwd_txt);
				
				holder.lock = (Button) row.findViewById(R.id.btn_lock);
				holder.unlock = (Button) row.findViewById(R.id.btn_unlock);
				
				
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				row.setTag(holder);
			}
			else
			{
				holder = (UserHolder) row.getTag();
			}
			user = data.get(position);
			
			if(user.getLock().equals("1"))
			{
				holder.lock.setVisibility(View.VISIBLE);
				holder.unlock.setVisibility(View.GONE);
			}
			else if(user.getLock().equals("0"))
			{
				holder.unlock.setVisibility(View.VISIBLE);
				holder.lock.setVisibility(View.GONE);
			}
			else
			{}
			holder.edit.setTag(user.getID());
			holder.delete.setTag(user.getID());
			holder.lock.setTag(user.getID());
			holder.unlock.setTag(user.getID());
		
			
			holder.user.setText(user.getUser());

			holder.pwd.setText(user.getPwd());
			
			final UserHolder _holder=holder;

		
			holder.lock.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					
					
					_holder.unlock.setVisibility(View.VISIBLE);
					_holder.lock.setVisibility(View.GONE);
					_dbHandler.UnlockOrLockUser(v.getTag().toString(),"0");
				

				}
			});
			
			holder.unlock.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					
					_holder.lock.setVisibility(View.VISIBLE);
					_holder.unlock.setVisibility(View.GONE);
					
					_dbHandler.UnlockOrLockUser(v.getTag().toString(),"1");
				
	


				}
			});
			
			
			
			holder.edit.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					Log.i("Edit Button Clicked", "**********");

					Intent update_user = new Intent(activity,
							AdminActivity2.class);
					update_user.putExtra("called", "update");
					update_user.putExtra("USER_ID", v.getTag().toString());
					activity.startActivity(update_user);

				}
			});
			holder.delete.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(final View v)
				{
					// TODO Auto-generated method stub

					// show a message while loader is loading

					AlertDialog.Builder adb = new AlertDialog.Builder(activity);
					adb.setTitle("删除");
					adb.setMessage("确定要删除吗?");
					final int user_id = Integer.parseInt(v.getTag().toString());
					adb.setNegativeButton("取消", null);
					adb.setPositiveButton("确定",
							new AlertDialog.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// MyDataObject.remove(positionToRemove);
									_DatabaseHandler dBHandler = new _DatabaseHandler(
											activity.getApplicationContext());
									dBHandler.Delete_Contact(user_id);
									AdminActivity1.this.onResume();

								}
							});
					adb.show();
				}

			});
			return row;

		}

		class UserHolder
		{
			TextView user;
			TextView pwd;
			Button lock;
			Button unlock;
			Button edit;
			Button delete;
		}

	}

}
