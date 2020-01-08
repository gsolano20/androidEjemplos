package com.novacompcr.myapplication;

import android.os.Bundle;
import android.view.View;

import com.novacompcr.myapplication.base.MerlinActivity;
import com.novacompcr.myapplication.display.NetworkStatusDisplayer;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;


public class DemoActivity extends MerlinActivity implements Connectable, Disconnectable, Bindable {

    private NetworkStatusDisplayer networkStatusDisplayer;
    private MerlinsBeard merlinsBeard;
    private View viewToAttachDisplayerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewToAttachDisplayerTo = findViewById(R.id.displayerAttachableView);
        merlinsBeard = new MerlinsBeard.Builder()
                .build(this);
        networkStatusDisplayer = new NetworkStatusDisplayer(getResources(), merlinsBeard);


    }

    private final View.OnClickListener networkStatusOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (merlinsBeard.isConnected()) {
                networkStatusDisplayer.displayPositiveMessage(R.string.current_status_network_connected, viewToAttachDisplayerTo);
            } else {
                networkStatusDisplayer.displayNegativeMessage(R.string.current_status_network_disconnected, viewToAttachDisplayerTo);
            }
        }
    };

    private final View.OnClickListener hasInternetAccessClick = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            merlinsBeard.hasInternetAccess(new MerlinsBeard.InternetAccessCallback() {
                @Override
                public void onResult(boolean hasAccess) {
                    if (hasAccess) {
                        networkStatusDisplayer.displayPositiveMessage(R.string.has_internet_access_true, viewToAttachDisplayerTo);
                    } else {
                        networkStatusDisplayer.displayNegativeMessage(R.string.has_internet_access_false, viewToAttachDisplayerTo);
                    }
                }
            });
        }
    };

    @Override
    protected Merlin createMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        networkStatusDisplayer.displayPositiveMessage(R.string.connected, viewToAttachDisplayerTo);
    }

    @Override
    public void onDisconnect() {
        networkStatusDisplayer.displayNegativeMessage(R.string.disconnected, viewToAttachDisplayerTo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStatusDisplayer.reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStatusDisplayer = null;
    }
}
