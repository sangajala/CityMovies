/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.citymovies.utils;

import com.bananalabs.citymovies.R;

import java.util.Random;

public class MovieDetails {

    private static final Random RANDOM = new Random();

    public static String getRandomCheeseDrawable(int i) {
        switch (i) {
            default:
            case 0:
                return "http://img.youtube.com/vi/Ys2B2MahwFc/0.jpg";
            case 1:
                return "http://img.youtube.com/vi/YDpRur0T9iQ/0.jpg";
            case 2:
                return "http://img.youtube.com/vi/i916BRRHoTg/0.jpg";
            case 3:
                return "http://img.youtube.com/vi/LRARHtMzZQE/0.jpg";
            case 4:
                return "http://img.youtube.com/vi/fvQZkpnb764/0.jpg";
            case 5:
                return "http://img.youtube.com/vi/vb5xCMbMfZ0/0.jpg";
            case 6:
                return "http://img.youtube.com/vi/PRjWvVQz-NI/0.jpg";
            case 7:
                return "http://img.youtube.com/vi/lUaxF2gGumU/0.jpg";
            case 8:
                return "http://img.youtube.com/vi/0L7iH3foZU0/0.jpg";

        }
    }

    public static final String[] sCheeseStrings = {
            "Speedunnodu", "Express Raja", "Nenu Sailaja", "BANG BANG", "SANAM RE",
            "AIR LIFT", "BATMAN V SUPERMAN", "WALKING DEAD", "CIVIL WAR",
    };

}
