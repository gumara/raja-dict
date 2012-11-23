package com.gadgetplus.rajadict;

import com.gadgetplus.rajadict.R;
import android.os.Bundle;
import android.app.Activity;
 
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
 
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        final DatabaseHandler myDb = new DatabaseHandler(this);
       
        PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(),0);
		 
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String version = pInfo.versionName;
        
        String totalWord = String.valueOf(myDb.getTotalRow());
        TextView textTotalWord = (TextView)findViewById(R.id.textTotalWord);
        textTotalWord.setText("จำนวนคำศัพท์ " + totalWord + " คำ");
    
        TextView textVersion = (TextView)findViewById(R.id.textVersion); 
        textVersion.setText("รุ่น " + version);
   
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
		
	}
    
    
}
