package ismart.is.com.ismart.event;

/**
 * Created by marcus on 4/20/2015
 */

public class TipRequestedEvent {
        String vendor;

    public TipRequestedEvent(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
