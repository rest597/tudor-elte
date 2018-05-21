package hu.elte.f40b2i.rest;

import hu.elte.f40b2i.data.User;
import hu.elte.f40b2i.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


public class AuthUserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger( AuthUserService.class );

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        logger.info("Authenticating " + username);
        User user = userRepository.findUserByUsername(username);
        logger.info("User data " + user.getPassword() + " " + user.getUserType());
        return new AuthUserPrincipal(user);
    }
}
