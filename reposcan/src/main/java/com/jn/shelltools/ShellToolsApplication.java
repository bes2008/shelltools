package com.jn.shelltools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShellToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShellToolsApplication.class, args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100000);
                }catch (InterruptedException ex){

                }
            }
        },"a").start();
    }
}
