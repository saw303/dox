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
package ch.silviowangler.dox.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ch.silviowangler.dox.domain.security.DoxUser;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Entity
@Table(name = "DOX_USER_SETTINGS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "SET_KEY"})
})
public class UserSetting extends Setting {

    @ManyToOne(optional = false)
    private DoxUser user;

    public UserSetting() {
    }

    public UserSetting(String key, String value, DoxUser user) {
        super(key, value);
        this.user = user;
    }

    public DoxUser getUser() {
        return user;
    }

    public void setUser(DoxUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSetting)) return false;
        if (!super.equals(o)) return false;

        UserSetting that = (UserSetting) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
