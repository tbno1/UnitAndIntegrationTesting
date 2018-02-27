package com.quicktutorialz.learntesting.DemoTest;

import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@Log
@SpringBootApplication
public class DemoTestApplication implements CommandLineRunner{

	@Autowired
	UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(DemoTestApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		//all stuff here will be executed at the beginning, before microservice starts listening
		log.info("let's fill H2 in-memory database");
		userDao.save(new User("RGNLSN87H13D761R", "Alessandro Argentieri", 30, new Date()));
		userDao.save(new User("DBRRSS87K27D761F", "Debora Rossini", 29, null));
		userDao.save(new User("LSNGLN96G23H143K", "Elisa Angelini", 21, null));
		userDao.save(new User("RBRCVN7708U7633F", "Roberta Covini", 41, null));
		log.info("in-memory database filled");
	}
}
