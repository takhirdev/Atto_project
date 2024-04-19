package org.example;

import org.example.config.SpringConfig;
import org.example.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
    public static void main(String[] args) {
       // ApplicationContext context = new ClassPathXmlApplicationContext("spring_config.xml");
        ApplicationContext context1 = new AnnotationConfigApplicationContext(SpringConfig.class);
        MainController mainController = (MainController) context1.getBean("mainController");
        mainController.start();
    }
}
