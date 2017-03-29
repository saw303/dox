/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox.web.util;


import static org.springframework.mobile.device.DevicePlatform.UNKNOWN;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;

public class DeviceMock implements Device {

    private boolean normal;
    private boolean mobile;
    private boolean tablet;

    public DeviceMock(boolean normal, boolean mobile, boolean tablet) {
        this.normal = normal;
        this.mobile = mobile;
        this.tablet = tablet;
    }

    public DeviceMock() {
        this(true, false, false);
    }

    @Override
    public boolean isNormal() {
        return normal;
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }

    @Override
    public boolean isTablet() {
        return tablet;
    }

    @Override
    public DevicePlatform getDevicePlatform() {
        return UNKNOWN;
    }
}
