package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.dto.ProfileDTO;
import org.example.service.ProfileService;
import org.example.service.TransactionService;
import org.example.util.DatabaseUtill;
import org.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.Scanner;
@Controller
@Scope("singleton")
public class MainController {
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TransactionService transactionService;


    public void start(){
        DatabaseUtill.createTable();
        DatabaseUtill.initAdmin();
        boolean a = true;
        while (a) {
            showMenu();
            int action = getAction();
            switch (action) {
                case 1:
                    login();
                    break;
                case 2:
                    registration();
                    break;
                case 3:
                    payment();
                    break;
                case 0:
                    a = false;
                    break;
                default:
                    System.out.println("Wrong action! Mazgi.......");
            }
        }
    }

    public void showMenu() {
        System.out.println("""
                **** Menu ****
                1. Login
                2. Registration
                3. Make Payment
                0. Exit
                """);
    }

    public int getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter action : ");
        return scanner.nextInt();
    }

    public void registration() {
        System.out.print("Enter name : ");
        String name = scanner.nextLine();
        System.out.print("Enter surname : ");
        String surname = scanner.nextLine();
        System.out.print("Enter phone : ");
        String phone = scanner.nextLine();
        System.out.print("Enter password : ");
        String password = scanner.nextLine();

        ProfileDTO profileDTO = ComponentContainer.currentProfile;
        profileDTO.setName(name);
        profileDTO.setSurname(surname);
        profileDTO.setPhone(phone);
        profileDTO.setPassword(MD5Util.getMd5(password));

        profileService.registration(profileDTO);
    }

    public void login() {
        System.out.print("Enter phone : ");
        String phone = scanner.nextLine();
        System.out.print("Enter password : ");
        String password = scanner.nextLine();

        profileService.login(phone, password);
    }
    public void payment(){
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter terminal code: ");
        String terminalCode = scanner.nextLine();

        transactionService.payment(cardNumber,terminalCode);
    }
}
