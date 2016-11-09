package ch.silviowangler.dox.settings;

import ch.silviowangler.dox.api.settings.SettingsService;
import ch.silviowangler.dox.domain.UserSetting;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.repository.UserSettingRepository;
import ch.silviowangler.dox.repository.security.DoxUserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsServiceImplTest {

    @InjectMocks
    private SettingsService service = new SettingsServiceImpl();
    @Mock
    private DoxUserRepository doxUserRepository;
    @Mock
    private UserSettingRepository userSettingRepository;

    private String username = "username";

    final DoxUser user = new DoxUser("email", "password", username);

    @Before
    public void setUp() throws Exception {
        Collection<SimpleGrantedAuthority> authorities = Sets.newHashSet();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new User(this.username, "password", authorities), "bla"));

        when(doxUserRepository.findByUsername(this.username)).thenReturn(user);
    }

    @Test
    public void testCreateOrUpdateSetting() throws Exception {

        when(userSettingRepository.findByKeyAndUser("hello", this.user)).thenReturn(null);

        service.createOrUpdateSetting("hello", "world");

        InOrder order = inOrder(doxUserRepository, userSettingRepository);

        order.verify(doxUserRepository).findByUsername("username");
        // has no ID yet. wont pass equals test
        order.verify(userSettingRepository).save(any(UserSetting.class));
        order.verifyNoMoreInteractions();
    }

    @Test
    public void testCreateOrUpdateSetting2() throws Exception {

        UserSetting userSetting = new UserSetting("hello", "mars", this.user);
        when(userSettingRepository.findByKeyAndUser("hello", this.user)).thenReturn(userSetting);

        service.createOrUpdateSetting("hello", "world");

        InOrder order = inOrder(doxUserRepository, userSettingRepository);

        order.verify(doxUserRepository).findByUsername("username");
        // has no ID yet. wont pass equals test
        order.verify(userSettingRepository).save(any(UserSetting.class));
        order.verifyNoMoreInteractions();

        assertThat(userSetting.getValue(), is("world"));
    }

    @Test
    public void testFindUserSettings() throws Exception {
        final Map<String, String> userSettings = service.findUserSettings();
        assertThat(userSettings.size(), is(0));
    }

    @Test
    public void testFindUserSettings2() throws Exception {

        when(userSettingRepository.findByUser(this.user)).thenReturn(Lists.newArrayList(new UserSetting("key", "value", this.user)));

        final Map<String, String> userSettings = service.findUserSettings();
        assertThat(userSettings.size(), is(1));

        assertThat(userSettings.containsKey("key"), is(true));
        assertThat(userSettings.get("key"), is("value"));
    }
}
