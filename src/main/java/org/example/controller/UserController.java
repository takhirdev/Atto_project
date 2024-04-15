package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.dto.ProfileDTO;
import org.example.service.CardService;
import org.example.service.TransactionService;

import java.util.Scanner;

public class UserController {
    private ProfileDTO profile = ComponentContainer.currentProfile;
    Scanner scanner = new Scanner(System.in);
    private CardService cardService;
    private TransactionService transactionService;

    public void start() {
        boolean a = true;
        while (a) {
            showMenu();
            int action = getAction();
            switch (action) {
                case 1:
                    addCard();
                    break;
                case 2:
                    cardList();
                    break;
                case 3:
                    changeCardStatus();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    refill();
                    break;
                case 6:
                    transaction();
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
                 **** User Menu ****
                  1. Add Card
                  2. Card List
                  3. Card Change Status
                  4. Delete Card
                  5. ReFill
                  6. Transaction
                  0. Exit
                """);
    }

    public int getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter action : ");
        return scanner.nextInt();
    }

    public void addCard() {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();

        cardService.addCard(cardNumber, profile);
    }

    public void cardList() {
        cardService.userCardList(profile.getId());
    }

    public void changeCardStatus() {
        System.out.print("Enter card number: ");
        String CardNumber = scanner.next();

        cardService.changeStatusByUser(CardNumber, profile);
    }

    public void delete() {
        System.out.print("Enter card number: ");
        String CardNumber = scanner.next();

        cardService.deleteUserCard(CardNumber, profile);
    }

    public void refill() {
        System.out.print("Enter card number:");
        String number = scanner.next();
        System.out.print("Amount:");
        Long amount = scanner.nextLong();

        transactionService.refill(profile, number, amount);
    }

    public void transaction() {
        transactionService.getUserTransaction(profile);
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
