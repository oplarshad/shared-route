package com.sharedroute.app;

import android.os.Build;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by cohid01 on 13/02/2015.
 *
 */
public class SharedLocationService {

    public static WebSocketClient requestMapUpdates(String serverURI, GoogleMap mMap) {

        URI uri;
        try {
            uri = new URI(serverURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        final WebSocketClient mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                this.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(final String s) {
                // should use runOnUiThread to interact with the UI
                Log.i("Websocket","message is "+ s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
        return mWebSocketClient;
    }
}
