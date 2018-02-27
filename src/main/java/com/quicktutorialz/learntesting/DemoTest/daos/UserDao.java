package com.quicktutorialz.learntesting.DemoTest.daos;

import com.quicktutorialz.learntesting.DemoTest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/* Spring itself is going to implement the concrete class we don't see. */

public interface UserDao extends JpaRepository<User, String> {
    //use methods of etended interface: no need to define new methods
}
