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
package com.example.kuassivi.android_ottosample.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class MainThreadBus extends Bus {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadBus() {
    }

    public MainThreadBus(String identifier) {
        super(identifier);
    }

    public MainThreadBus(ThreadEnforcer enforcer) {
        super(enforcer);
    }

    public MainThreadBus(ThreadEnforcer enforcer, String identifier) {
        super(enforcer, identifier);
    }

    // {@see http://tools.android.com/tech-docs/support-annotations}
    @MainThread
    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
