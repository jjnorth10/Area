package jm.org.data.area;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;

public class AreaPreferencesActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.preferences);
		getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);
        getListView().setBackgroundColor(Color.TRANSPARENT);
        //getListView().setCacheColorHint(Color.TRANSPARENT);
		addPreferencesFromResource(R.xml.preferences2);

		//Preference startupPreference = findPreference(getString(R.string.pref_startupKey));
		//PreferenceScreen preferenceScreen = getPreferenceScreen();
		//preferenceScreen.removePreference(startupPreference);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, AreaActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	  public void onStart() {
	    super.onStart();
	  
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	  
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }


}
