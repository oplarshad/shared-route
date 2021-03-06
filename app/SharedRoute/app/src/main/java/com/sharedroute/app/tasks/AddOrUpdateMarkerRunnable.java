package com.sharedroute.app.tasks;

import com.google.android.gms.maps.model.*;
import com.sharedroute.app.MainMapActivity;
import com.sharedroute.app.MarkerWrapper;
import com.sharedroute.app.R;

/**
* Created by cohid01 on 28/02/2015.
*/
public class AddOrUpdateMarkerRunnable implements Runnable {
    private final String sessionId;
    private final LatLng newLatLng;
    private final MainMapActivity mainMapActivity;

    public AddOrUpdateMarkerRunnable(MainMapActivity mainMapActivity, String sessionId, LatLng newLatLng) {
        this.sessionId = sessionId;
        this.newLatLng = newLatLng;
        this.mainMapActivity = mainMapActivity;
    }

    @Override
    public void run() {
        final MarkerWrapper marker = mainMapActivity.getSessionIdToMarkers().get(sessionId);
        if (marker != null){
            marker.getMarker().setPosition(newLatLng);
            marker.setLastTouch(System.currentTimeMillis());
        } else {
            final MarkerOptions markerOptions = new MarkerOptions()
                    .position(newLatLng)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
            Marker newMarker = mainMapActivity.getMainMap().addMarker(markerOptions);
            MarkerWrapper markerWrapper = new MarkerWrapper(newMarker, System.currentTimeMillis());
            mainMapActivity.getSessionIdToMarkers().put(sessionId,markerWrapper);
        }
    }
}
