package hu.elte.f40b2i.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name="customer")
@PrimaryKeyJoinColumn(name = "userId")
public class Customer extends User implements Serializable {
        private static final long serialVersionUID = 1L;

        @Min(5)
        @Max(99)
        private int age;

        private String name;

        private String bio;

        public String getName(){ return this.name; }
        public void setName(String name){ this.name = name; }

        public int getAge(){ return this.age; }
        public void setAge(int age){ this.age = age; }

        public String getBio(){ return this.bio; }
        public void setBio(String bio){ this.bio = bio; }

        // Return object
        public ReturnObject createReturnObject(){
                return new ReturnObject(this.getUserid(),
                        this.getUsername(),
                        this.getUserType(),
                        this.getAge(),
                        this.getName(),
                        this.getBio());
        }

        public class ReturnObject extends User.ReturnObject{
            public int age;
            public String name;
            public String bio;

                ReturnObject(Integer userId,
                             String username,
                             UserType userType,
                             int age,
                             String name,
                             String bio){
                        super(userId, username, userType);
                        this.age = age;
                        this.name = name;
                        this.bio = bio;
                }
        }

    }
