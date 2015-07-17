/*
 * Copyright (C) 2015 Kiko Gonzalez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.kuassivi.android_ottosample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kuassivi.android_ottosample.event.ActivityEvent;
import com.example.kuassivi.android_ottosample.event.ServiceEvent;
import com.example.kuassivi.android_ottosample.receiver.DummyReceiver;
import com.example.kuassivi.android_ottosample.service.DummyService;
import com.example.kuassivi.android_ottosample.util.BusProvider;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

/**
 * This example shows how a Bus enhance communications between Activity and others Process objects
 * Now our Activity has less responsibility as it no longer needs to be concerned
 * with a list of listeners and is immediately decoupled from the interface
 * <p>
 * One of the approach is to interact with our {@link DummyService}
 * without the need to bound it with our {@link MainActivity},
 * see {@link #interactWithService()} Producer
 * <p>
 * Another approach is to deal with our {@link DummyReceiver}
 * once it has receive a broadcast communication from any place
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private DummyReceiver mReceiver = new DummyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // We start DummyService but we don't care about when it is connected
        Intent serviceIntent = new Intent(this, DummyService.class);
        startService(serviceIntent);

        // We register DummyReceiver to have account when it receives a broadcast message
        IntentFilter receiverIntentFilter = new IntentFilter(DummyReceiver.RECEIVER_ACTION);
        registerReceiver(mReceiver, receiverIntentFilter);
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent(this, DummyService.class);
        stopService(intent);

        unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Send a broadcast message to our DummyReceiver
        Intent broadcastIntent = new Intent(DummyReceiver.RECEIVER_ACTION);
        sendBroadcast(broadcastIntent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void logOrigin(@NonNull ActivityEvent event) {
        Log.i(TAG, "logOrigin is processed on MainActivity but it has been called "
                   + event.interaction);
    }

    /**
     * The @Produce annotation means that as soon as
     * an interested subscriber registers on a bus (anywhere!)
     * it will receive an event or the latest one,
     * depending on what you need
     * <p>
     * So we don't need to bind the Service to know when it is connected
     *
     * @return ServiceEvent
     *              an object which will be sent to our service
     *              when it is already connected and subscribed to a bus
     */
    @SuppressWarnings("unused")
    @Produce
    public ServiceEvent interactWithService() {
        return new ServiceEvent(ServiceEvent.Interact.FROM_ACTIVITY);
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }
}
