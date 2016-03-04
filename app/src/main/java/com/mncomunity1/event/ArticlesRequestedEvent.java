package com.mncomunity1.event;

/**
 * Created by marcus on 4/20/2015
 */

public class ArticlesRequestedEvent {
        String vendor;

    public ArticlesRequestedEvent(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
