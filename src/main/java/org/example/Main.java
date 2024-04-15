package org.example;

import org.example.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_config.xml");
        MainController mainController = (MainController) context.getBean("mainController");
        mainController.start();
    }
}