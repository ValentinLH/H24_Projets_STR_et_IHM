package fr.polytech.vgl.dao;

import org.springframework.boot.SpringApplication;

public class SpringAppThread  extends Thread {

    @Override
    public void run() {
        SpringApplication.run(SpringApp.class);
    }
}
