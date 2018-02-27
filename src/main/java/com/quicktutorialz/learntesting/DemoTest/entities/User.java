package com.quicktutorialz.learntesting.DemoTest.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* POJO which maps the table into the database */

@Entity
@Table(name="users")
@AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @Getter @Setter
    @NotNull @NotEmpty @NotBlank
    @Column(name="ID")
    private String id;

    @Column(name="NAME")
    @Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String name;

    @Column(name="AGE")
    @Getter @Setter
    //@NotNull @NotBlank @NotEmpty: with int it gives error!
    private int age;

    @Column(name="DATE_SUBSCRIPTION")
    @Getter @Setter
    private Date dateOfSubscription;        //filled with prePersist: it can be null in input; we don't use @NotNull

    @PrePersist
    void getTimeOperation() {
        this.dateOfSubscription = new Date();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof User))return false;
        User otherMyClass = (User) other;
        if(otherMyClass.getId().equals(this.getId())
                && otherMyClass.getId().equals(this.getId())
                && otherMyClass.getName().equals(this.getName())
                && otherMyClass.getAge()==this.getAge()
                )

            return true;
        return false;
    }


}
