package hu.elte.f40b2i.rest;


import hu.elte.f40b2i.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("user")
@Transactional
public class UserManager {

    private static Logger log = LoggerFactory.getLogger(AdminManager.class);

    @Autowired
    private UserRepository userDao;


    @DeleteMapping("/deleteMyprofile")
    public ResponseEntity<String> deleteMyProfileHandler(Authentication a) {
        this.userDao.deleteById(((AuthUserPrincipal)a.getPrincipal()).getId());
        return new ResponseEntity<>("User deleted",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Void> dispatchUser() {
        //log.debug("Into URI: " + rr.getURI().toString() );
        SecurityContext cc = SecurityContextHolder.getContext();
        HttpHeaders headers = new HttpHeaders();
        if(cc.getAuthentication() != null) {
            Authentication a = cc.getAuthentication();
            try
            {
                String uriTarget = "customer_home.html";
                if(a.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))){
                    uriTarget = "admin_home.html";
                }else if(a.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_TUDOR"))) {
                    uriTarget = "tudor_home.html";
                }

                headers.setLocation(new URI("/" + uriTarget));
            }
            catch ( URISyntaxException e )	{ log.warn( "Dispatcher cannot redirect" ); }
        }

        return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
    }

}
