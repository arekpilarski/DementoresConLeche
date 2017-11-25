package com.hackmonDB.db;

import com.hackmonDB.db.controller.EventController;
import com.hackmonDB.db.controller.UserController;
import com.hackmonDB.db.entity.User;
import com.hackmonDB.db.repository.EventRepository;
import static java.lang.System.exit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HackmonDbApplication implements CommandLineRunner{

    @Autowired
    private UserController userController;
	public static void main(String[] args) {
		  SpringApplication app = new SpringApplication(HackmonDbApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
              
	}

    @Override
    public void run(String... args) throws Exception {
        AddUser(new User("WW"));
  
        exit(0);
    }
    
    public void AddUser(User user)
    {
        userController.insert(user);
    }
}
