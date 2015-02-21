package ch.silviowangler.dox.api.rest;

import java.io.Serializable;

/**
 * Created on 21.02.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
public class PartnerModel implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
