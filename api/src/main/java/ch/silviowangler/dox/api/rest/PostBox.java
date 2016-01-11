package ch.silviowangler.dox.api.rest;

import ch.silviowangler.dox.api.AbstractTranslatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
public class PostBox extends AbstractTranslatable implements Serializable {

    private Long id;
    private String shortName;
    private String parent;
    private String username;
    private String translatedText;

    public PostBox(Long id, String shortName, String parent, String username) {
        this.id = id;
        this.shortName = shortName;
        this.parent = parent;
        this.username = username;
    }

    public PostBox() {
    }

    @Override
    public String retrieveKeyPostfix() {
        return getShortName();
    }

    @Override
    public String getTranslation() {
        return this.translatedText;
    }

    @Override
    public void setTranslation(String translation) {
        this.translatedText = translation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostBox)) return false;
        PostBox postBox = (PostBox) o;
        return Objects.equals(getId(), postBox.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
