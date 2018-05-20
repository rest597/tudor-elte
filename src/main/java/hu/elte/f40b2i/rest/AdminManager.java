package hu.elte.f40b2i.rest;

import hu.elte.f40b2i.data.User;
import hu.elte.f40b2i.data.UserRepository;
import hu.elte.f40b2i.data.Tudor;
import hu.elte.f40b2i.data.TudorRepository;
import hu.elte.f40b2i.data.Customer;
import hu.elte.f40b2i.data.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("admin")
@Transactional
public class AdminManager {

    private static Logger log = LoggerFactory.getLogger(AdminManager.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private TudorRepository tudorDao;

    @Autowired
    private CustomerRepository customerDao;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/newUser")
    public ResponseEntity<IdMessageObject> newUserHandler(@RequestBody(required=true) User user) {
        String resMsg = " saved";
        Integer resId = 0;

        //Encrypt password
        user.setPassword(this.encryptPassword(user.getPassword()));

        //Set type and save
        if(user instanceof Tudor){
            resId = this.tudorDao.save((Tudor)user).getUserid();
            resMsg = "Tudor" + resMsg;
        }else if(user instanceof Customer){
            resId = this.customerDao.save((Customer)user).getUserid();
            resMsg = "Customer" + resMsg;
        }else{ // Admin
            resId = this.userDao.save(user).getUserid();
            resMsg = "Admin" + resMsg;
        }

        return new ResponseEntity<>(new IdMessageObject(resId, resMsg),HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{type}/{userid}")
    public ResponseEntity<User.ReturnObject> getUserHandler(@PathVariable("type") User.UserType userType,
                                                    @PathVariable("userid") Integer userid) {
        User.ReturnObject res = null;

        switch (userType){
            case Admin:
                res = this.userDao.getOne(userid).createReturnObject();
                break;
            case Tudor:
                res = this.tudorDao.getOne(userid).createReturnObject();
                break;
            case Customer:
                res = this.customerDao.getOne(userid).createReturnObject();
                break;
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }



    @DeleteMapping("/deleteUser/{type}/{userid}")
    public ResponseEntity<String> deleteUserHandler(@PathVariable("type") User.UserType userType,
                                                        @PathVariable("userid") Integer userid) {
        String resMsg = " deleted";

        switch (userType){
            case Admin:
                this.userDao.deleteById(userid);
                resMsg = "Admin" + resMsg;
                break;
            case Tudor:
                this.tudorDao.deleteById(userid);
                resMsg = "Tudor" + resMsg;
                break;
            case Customer:
                this.customerDao.deleteById(userid);
                resMsg = "Tudor" + resMsg;
                break;
        }
        return new ResponseEntity<>(resMsg,HttpStatus.NO_CONTENT);
    }

    private String encryptPassword(String password){
        return this.passwordEncoder.encode(password);
    }



}
