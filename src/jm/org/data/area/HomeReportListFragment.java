package jm.org.data.area;

import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.DOC_TITLE;
import static jm.org.data.area.DBConstants.IDS_DOC_AUTH_STR;
import static jm.org.data.area.DBConstants.IDS_DOC_DWNLD_URL;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_TITLE;
import static jm.org.data.area.AreaConstants.*;

import java.util.Arrays;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class HomeReportListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final String TAG = getClass().getSimpleName();
	private ProgressDialog dialog;
	SimpleCursorAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		dialog = new ProgressDialog(getActivity());
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading. Please wait...", true);
		String[] from = { IDS_DOC_TITLE, IDS_DOC_AUTH_STR };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
		// tAdapter = new SimpleCursorAdapter(getActivity(),
		// R.layout.list_reports_item, null, from, to, 0);
		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_dual, null, from, to, 0);
		SimpleCursorAdapter.ViewBinder binder = new SimpleCursorAdapter.ViewBinder() {

			@Override
			public boolean setViewValue(View arg0, Cursor arg1, int arg2) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) arg0;
			    tv.setTextColor(Color.parseColor("#004B51"));
				return false;
			}
			
		};
		mAdapter.setViewBinder(binder);

		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);

		setEmptyText("No indicators found");
		setListShown(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListAdapter().getItem(position);

		String item = cursor.getString(cursor.getColumnIndex(IDS_DOC_ID));
		int item_id = cursor.getInt(cursor.getColumnIndex(DOCUMENT_ID));
		String itemTitle = cursor.getString(cursor
				.getColumnIndex(IDS_DOC_TITLE));
		String item_url = cursor.getString(cursor.getColumnIndex(IDS_DOC_DWNLD_URL));
		Log.d(TAG, "Report selected is: " + item + " Title is: " + itemTitle);

		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

		// MapBuilder.createEvent().build() returns a Map of event fields and values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder
		    .createEvent("ui_action",     // Event category (required)
		                 "Home_Reports_List_Selction",  // Event action (required)
		                 "Report selected is: " + item + " Title is: " + itemTitle,   // Event label
		                 null)            // Event value
		    .build()
		);
		// Launch Report View
		Intent intent = new Intent(getActivity().getApplicationContext(),
				ReportDetailViewActivity.class);
		intent.putExtra(DOCUMENT_ID, item_id);
		intent.putExtra(DOC_TITLE, itemTitle);
		intent.putExtra(IDS_DOC_ID, item);
		intent.putExtra(IDS_DOC_DWNLD_URL, item_url);
		// intent.putExtra(BING_URL, itemURL);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new HomeListAdapter(getActivity(), IDS_SEARCH);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null) {
			Log.e(TAG,
					String.format(
							"Report list Cursor size: %d. Cursor columns: %s. Cursor column count: %d",
							cursor.getCount(),
							Arrays.toString(cursor.getColumnNames()),
							cursor.getCount()));
			mAdapter.swapCursor(cursor);
			if (isResumed()) {
				setListShown(true);
			} else {
				setListShownNoAnimation(true);
			}
		}
		setEmptyText("No reports downloaded yet");
		setListShown(true);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		//mAdapter.swapCursor(null);

	}

	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}
	
	/*@Override
	public void onResume(){
		String[] from = { IDS_DOC_TITLE, IDS_DOC_AUTH_STR };
		int[] to = { R.id.list_item_title, R.id.list_item_desc };
		// tAdapter = new SimpleCursorAdapter(getActivity(),
		// R.layout.list_reports_item, null, from, to, 0);
		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_item_dual, null, from, to, 0);

		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
		super.onResume();
	}
	
	@Override
	public void onStop() {
	    try {
	      super.onStop();

	      if (this.mAdapter !=null){
	        this.mAdapter.getCursor().close();
	        this.mAdapter = null;
	      }
	      
	      //this.getLoaderManager().destroyLoader(0);
	      
	      if (this.mActivityListCursorObj != null) {
	        this.mActivityListCursorObj.close();
	      }

	      super.onStop();
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter" + error.getStackTrace());
	    	
	    }// end try/catch (Exception error)
	  }// end onStop
*/
}
