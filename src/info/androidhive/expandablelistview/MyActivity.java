package info.androidhive.expandablelistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;



import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MyActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
//	private ImageView imgView;
	private TextView webviewNavi;
	private WebView webView;

    private SlidingMenu menu;

    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    private TextView textView;
    
    private PopupWindow popupWindow;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		TextView logout = (TextView) findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
		
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu_frame);
		
		
		
//		imgView = (ImageView) findViewById(R.id.top);
//		imgView.setImageResource(R.drawable.top);
		

		webviewNavi= (TextView)findViewById(R.id.textView1);
		
		webView = (WebView) findViewById(R.id.webView1);

		
        webView = (WebView) findViewById(R.id.webView1);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadUrl("file:///android_asset/welcome.html");
		
	


		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		expListView.setAdapter(listAdapter);
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				
//				webviewNavi.setText(listDataHeader.get(groupPosition)
//						+ " > "
//						+ listDataChild.get(
//								listDataHeader.get(groupPosition)).get(
//								childPosition));
//		
					switch(childPosition)
					{
					case 0:
						webView.loadUrl("file:///android_asset/p1.html");
						break;
					case 1:
						webView.loadUrl("file:///android_asset/p1.html");
						break;
					case 2: 
						webView.loadUrl("file:///android_asset/p1.html");
						break;
					}
		
				return false;
			}
		});
		
		
		
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("油泵车");
		listDataHeader.add("空调车");
		

		// Adding child data
		List<String> car1 = new ArrayList<String>();
		
		car1.add("DYC-5A");
		car1.add("DYC-6A");


		List<String> car2 = new ArrayList<String>();
		car2.add("DTCQ-4B");
	

	

		listDataChild.put(listDataHeader.get(0), car1); 
		listDataChild.put(listDataHeader.get(1), car2);
	
	}
	
}

