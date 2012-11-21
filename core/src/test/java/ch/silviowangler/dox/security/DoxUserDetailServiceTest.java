package ch.silviowangler.dox.security;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.DoxUserRepository;
import ch.silviowangler.dox.domain.security.GrantedAuthority;
import ch.silviowangler.dox.domain.security.Role;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DoxUserDetailServiceTest {

    @Mock
    private DoxUserRepository repository;

    @InjectMocks
    private DoxUserDetailService userDetailService = new DoxUserDetailService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testLoadUserByUsername() throws Exception {

        DoxUser user = new DoxUser("silvio.wangler@email.ch", "a password", "silvio");
        when(repository.findByUsername("silvio")).thenReturn(user);

        UserDetails userDetails = userDetailService.loadUserByUsername("silvio");

        verify(repository).findByUsername("silvio");

        assertThat(userDetails, notNullValue());
        assertThat(userDetails.getUsername(), equalTo("silvio"));
        assertThat(userDetails.getPassword(), equalTo("a password"));
        assertThat(userDetails.isAccountNonExpired(), is(true));
        assertThat(userDetails.isAccountNonLocked(), is(true));
        assertThat(userDetails.isCredentialsNonExpired(), is(true));
        assertThat(userDetails.isEnabled(), is(true));
        assertThat(userDetails.getAuthorities().size(), is(equalTo(0)));
    }

    @Test
    public void testLoadUserByUsernameWithGrantedAuthorities() throws Exception {

        DoxUser user = new DoxUser("silvio.wangler@email.ch", "a password", "silvio");

        Role roleAdmin = new Role("ADMIN");
        roleAdmin.getGrantedAuthorities().add(new GrantedAuthority("delete"));
        roleAdmin.getGrantedAuthorities().add(new GrantedAuthority("create"));
        roleAdmin.getGrantedAuthorities().add(new GrantedAuthority("update"));

        Role roleUser = new Role("USER");
        roleUser.getGrantedAuthorities().add(new GrantedAuthority("read"));
        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        when(repository.findByUsername("silvio")).thenReturn(user);

        UserDetails userDetails = userDetailService.loadUserByUsername("silvio");

        verify(repository).findByUsername("silvio");

        assertThat(userDetails, notNullValue());
        assertThat(userDetails.getUsername(), equalTo("silvio"));
        assertThat(userDetails.getPassword(), equalTo("a password"));
        assertThat(userDetails.isAccountNonExpired(), is(true));
        assertThat(userDetails.isAccountNonLocked(), is(true));
        assertThat(userDetails.isCredentialsNonExpired(), is(true));
        assertThat(userDetails.isEnabled(), is(true));
        assertThat(userDetails.getAuthorities().size(), is(equalTo(6)));
        assertThat((Iterable<SimpleGrantedAuthority>) userDetails.getAuthorities(), hasItems(
                new SimpleGrantedAuthority("read"),
                new SimpleGrantedAuthority("create"),
                new SimpleGrantedAuthority("update"),
                new SimpleGrantedAuthority("delete"),
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    public void loadUserByNameThatDoesNotExists() throws Exception {

        when(repository.findByUsername("silvio")).thenReturn(null);

        this.thrown.expect(UsernameNotFoundException.class);
        this.thrown.expectMessage("No such user silvio");

        userDetailService.loadUserByUsername("silvio");
    }
}
