package ch.silviowangler.dox.web.util;


import org.springframework.mobile.device.Device;

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
}
