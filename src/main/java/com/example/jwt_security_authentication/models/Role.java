package com.example.jwt_security_authentication.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

//We Implement Serializable because it makes easier to save the state of the role.
//It is useful when we need to save objects or send them through different applications
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) //set any fild of the class private
public class Role implements Serializable {
    /**
     * This class creates a table named Role with two roles, USER and ADMIN
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    RoleName roleName;

    public Role(RoleName roleName){
        this.roleName = roleName;
    }
    public String getRoleName(){
        return roleName.toString();
    }
}
