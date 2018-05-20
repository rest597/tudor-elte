package hu.elte.f40b2i.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = Tudor.class, name = "Tudor"),
        @JsonSubTypes.Type(value = User.class, name = "Admin"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    public enum UserType { Admin, Tudor, Customer };

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    public UserType type;

    public int getUserid() { return this.userId; }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    public UserType getUserType() { return this.type; }
    public void setUserType(UserType type) { this.type = type; }

    // Return object
    public ReturnObject createReturnObject(){
        return new ReturnObject(this.getUserid(), this.getUsername());
    }

    public class ReturnObject{
        public Integer userId;
        public String username;

        ReturnObject(Integer userId, String username){
            this.userId = userId;
            this.username = username;
        }
    }


}

