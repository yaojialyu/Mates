package hk.edu.uic.mates.controller;

import android.content.Context;
import android.location.LocationManager;

public class LocationUtil {

	private LocationManager locationManager;
	
	public LocationUtil() {
		
	}
	
	public LocationUtil(Context context) {
		this.locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public boolean isGPSEnabled(Context context) {
		this.locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    	return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
	
	public LocationManager getLocationManager() {
		return this.locationManager;
	}
}
