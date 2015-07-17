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
package com.example.kuassivi.android_ottosample.event;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// With the final modifier we constrain this class to be extended
public final class ActivityEvent {

    public static class Interact {
        public static final String FROM_ACTIVITY    = "FROM ACTIVITY";
        public static final String FROM_SERVICE     = "FROM SERVICE";
        public static final String FROM_RECEIVER    = "FROM RECEIVER";
    }

    // {@see http://tools.android.com/tech-docs/support-annotations}
    @StringDef({Interact.FROM_ACTIVITY, Interact.FROM_SERVICE, Interact.FROM_RECEIVER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractionEvents {}

    public String interaction;

    public ActivityEvent(@InteractionEvents String interaction) {
        this.interaction = interaction;
    }
}
