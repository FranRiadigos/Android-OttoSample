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
package com.example.kuassivi.android_ottosample.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kuassivi.android_ottosample.event.ActivityEvent;
import com.example.kuassivi.android_ottosample.event.ServiceEvent;
import com.example.kuassivi.android_ottosample.util.BusProvider;
import com.squareup.otto.Subscribe;

public class DummyService extends Service {
    public static final String TAG = "DummyService";

    @Override
    public void onCreate() {
        BusProvider.getInstance().register(this);
    }

    /**
     * We will receive an event here when the class is subscribed to a Bus
     * Posting an answer to another Subscribers with the same Event Object type
     *
     * @param event ServiceEvent
     */
    @SuppressWarnings("unused")
    @Subscribe
    public void logOrigin(@NonNull ServiceEvent event) {
        Log.i(TAG, "logOrigin is processed on DummyService but it has been called "
                   + event.interaction);

        BusProvider.getInstance().post(new ActivityEvent(ActivityEvent.Interact.FROM_SERVICE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
