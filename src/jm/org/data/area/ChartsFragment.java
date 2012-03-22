package jm.org.data.area;

import org.achartengine.GraphicalView;
import org.achartengine.chartdemo.demo.chart.AreaChart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChartsFragment extends Fragment {
	public static final String TAG = ChartsFragment.class.getSimpleName();
	private IndicatorActivity parentActivity;
	private GraphicalView chart;
	private LinearLayout layout;
	private String Indicator;
	private String[] Countries;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		parentActivity = (IndicatorActivity) getActivity();
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		layout = (LinearLayout) parentActivity.findViewById(R.id.chart_view);
		Indicator = parentActivity.getIndicator();
		Indicator = "AG.LND.AGRI.ZS";
		Countries = parentActivity.getCountryList();
		Countries = new String[]{"Jamaica", "Barbados"};
		setHasOptionsMenu(true);
		
		
		chart = new AreaChart().execute(getActivity().getBaseContext(), Indicator, Countries);
		Log.e(TAG,"chart view " +chart.toString() + " - " + layout.getId() + "current indicator" + Indicator + " - "
				+ "First country: " + Countries[0] + " from " + Countries.length);
		
		
		layout.addView(chart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.charts, container, false);
		
		//getActivity().invalidateOptionsMenu();
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.chart, menu);
        
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		//getActivity().invalidateOptionsMenu();
		if (menu.size() <= 1) {
			Log.d(TAG, "Run onPrepareOptionsMenu");
			setHasOptionsMenu(true);
			MenuInflater menuInflater = getActivity().getMenuInflater();
			this.onCreateOptionsMenu(menu, menuInflater);
		}
		super.onPrepareOptionsMenu(menu);
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                
            case R.id.menu_reload:
                Toast.makeText(getActivity(), "Fake refreshing...", Toast.LENGTH_SHORT).show();
               //Get current country list 
               String[] countryList = (String[]) ((IndicatorActivity) getActivity()).getCountryList();
                
                /*parentActivity.getActionBarHelper().setRefreshActionItemState(true);
                getWindow().getDecorView().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                getActionBarHelper().setRefreshActionItemState(false);
                            }
                        }, 1000);*/
                break; 
            case R.id.menu_share:
                Toast.makeText(getActivity(), "Tapped share", Toast.LENGTH_SHORT).show();
                //Get image and initiative share intent
                break;
               
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
