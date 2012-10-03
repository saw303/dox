package ch.silviowangler.dox.security;

import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.security.DoxUserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DoxUserDetailServiceTest {

    @Mock
    private DoxUserRepository repository;

    @InjectMocks
    private DoxUserDetailService userDetailService = new DoxUserDetailService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() throws Exception {

        DoxUser user = new DoxUser("silvio.wangler@email.ch", "a password".getBytes(), "silvio");
        when(repository.findByUsername("silvio")).thenReturn(user);

        UserDetails userDetails = userDetailService.loadUserByUsername("silvio");

        verify(repository).findByUsername("silvio");

        assertThat(userDetails, notNullValue());
        assertThat(userDetails.getUsername(), equalTo("silvio"));
        assertThat(userDetails.getPassword(), equalTo("a password"));
        assertThat(userDetails.isAccountNonExpired(), is(not(true)));
        assertThat(userDetails.isAccountNonLocked(), is(not(true)));
        assertThat(userDetails.isCredentialsNonExpired(), is(true));
        assertThat(userDetails.isEnabled(), is(true));
        assertThat(userDetails.getAuthorities().size(), is(equalTo(0)));
    }

    @Test
    public void loadUserByNameThatDoesNotExists() throws Exception {

        when(repository.findByUsername("silvio")).thenReturn(null);

        this.thrown.expect(UsernameNotFoundException.class);
        this.thrown.expectMessage("No such user silvio");

        userDetailService.loadUserByUsername("silvio");
    }
}
