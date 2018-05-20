package hu.elte.f40b2i.rest;


import hu.elte.f40b2i.data.Customer;
import hu.elte.f40b2i.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Transactional
public class UserManager {

    private static Logger log = LoggerFactory.getLogger(AdminManager.class);

    @PostMapping("/updateMyData")
    public ResponseEntity<String> updateMyDataHandler(@RequestBody(required=false) Customer customer) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

    }

    @PostMapping("/deleteMyprofile")
    public ResponseEntity<String> deleteMyDataHandler(@RequestBody(required=false) Customer customer) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

    }
}
