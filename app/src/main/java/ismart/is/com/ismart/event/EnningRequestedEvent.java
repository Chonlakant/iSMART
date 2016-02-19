package ismart.is.com.ismart.event;

/**
 * Created by marcus on 4/20/2015
 */

public class EnningRequestedEvent {
        String vendor;

    public EnningRequestedEvent(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
