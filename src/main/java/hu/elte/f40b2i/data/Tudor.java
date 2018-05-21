package hu.elte.f40b2i.data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name="tudor")
@PrimaryKeyJoinColumn(name = "userId")

public class Tudor extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(5)
    @Max(99)
    private int age;

    private String name;

    private String specialization;

    private String experience;

    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }

    public int getAge(){ return this.age; }
    public void setAge(int age){ this.age = age; }

    public String getSpecialization(){ return this.specialization; }
    public void setSpecialization(String specialization){ this.specialization = specialization; }

    public String getExperiance(){ return this.experience; }
    public void setExperience(String experience){ this.experience = experience; }


    // Return object
    public ReturnObject createReturnObject(){
        return new ReturnObject(this.getUserid(),
                this.getUsername(),
                this.getUserType(),
                this.getAge(),
                this.getName(),
                this.getSpecialization(),
                this.getExperiance());
    }

    public class ReturnObject extends User.ReturnObject{
        public int age;
        public String name;
        public String specialization;
        public String experience;

        ReturnObject(Integer userId,
                     String username,
                     UserType userType,
                     int age,
                     String name,
                     String specialization,
                     String experience){
            super(userId, username, userType);
            this.age = age;
            this.name = name;
            this.specialization = specialization;
            this.experience = experience;
        }
    }
}
