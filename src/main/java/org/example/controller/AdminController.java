package org.example.controller;

import org.example.dto.CardDTO;
import org.example.dto.TerminalDTO;
import org.example.enums.TerminalStatus;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;

import java.time.LocalDate;
import java.util.Scanner;

public class AdminController {

    private Scanner scanner = new Scanner(System.in);
    private CardService cardService;
    private TerminalService terminalService;
    private ProfileService profileService ;
    private TransactionService transactionService;

    public void start() {
        boolean a = true;
        while (a) {
            showMenu();
            int action = getAction();
            switch (action) {
                case 1:
                    createCard();
                    break;
                case 2:
                    cardList();
                    break;
                case 3:
                    updateCard();
                    break;
                case 4:
                    changeCardStatus();
                    break;
                case 5:
                    deleteCard();
                    break;
                case 6:
                    createTerminal();
                    break;
                case 7:
                    terminalList();
                    break;
                case 8:
                    updateTerminal();
                    break;
                case 9:
                    changeTerminalStatus();
                    break;
                case 10:
                    deleteTerminal();
                    break;
                case 11:
                    profileList();
                    break;
                case 12:
                    changeProfileStatus();
                    break;
                case 13:
                    transactionList();
                    break;
                case 14:
                    companyCardBalance();
                    break;
                case 15:
                    todayTransactions();
                    break;
                case 16:
                    dailyTransactions();
                    break;
                case 17:
                    intervalTransactions();
                    break;
                case 18:
                    transactionByTerminal();
                    break;
                case 19:
                    transactionByCard();
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
                 **** Admin Menu ****
                  1. Create Card
                  2. Card List
                  3. Update Card
                  4. Change Card status
                  5. Delete Card
                  6. Create Terminal
                  7. Terminal List
                  8. Update Terminal
                  9. Change Terminal Status
                  10. Delete Terminal
                  11. Profile List
                  12. Change Profile Status (by phone)
                  13. Transaction List
                  14. Company Card Balance
                  15. Bugungi to'lovlar
                  16. Kunlik to'lovlar
                  17. Oraliq to'lovlar
                  18. Transaction by Terminal:
                  19. Transaction By Card:
                  0. Exit
                """);
    }

    public int getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter action : ");
        return scanner.nextInt();
    }

    public void createCard() {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();
        System.out.print("Enter card expDate(yyyy-MM-dd): ");
        LocalDate expDate = LocalDate.parse(scanner.next());

        CardDTO card = new CardDTO();
        card.setNumber(cardNumber);
        card.setExpDate(expDate);

        cardService.createCard(card);
    }

    public void cardList() {
        cardService.cardList();
    }

    public void updateCard() {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();
        System.out.print("Enter new expDate(yyyy-MM-dd): ");
        LocalDate expDate = LocalDate.parse(scanner.next());

        CardDTO card = new CardDTO();
        card.setNumber(cardNumber);
        card.setExpDate(expDate);
        cardService.updateCard(card);
    }

    public void changeCardStatus() {
        System.out.print("Enter card number: ");
        String CardNumber = scanner.next();

        cardService.changeStatusByAdmin(CardNumber);
    }

    public void deleteCard() {
        System.out.print("Enter card number: ");
        String CardNumber = scanner.next();

        cardService.delete(CardNumber);
    }

    public void createTerminal() {
        System.out.print("Enter code: ");
        String code = scanner.next();
        System.out.print("Enter address: ");
        String address = scanner.next();

        TerminalDTO terminal = new TerminalDTO();
        terminal.setCode(code);
        terminal.setAddress(address);
        terminal.setStatus(TerminalStatus.ACTIVE);
        terminalService.create(terminal);
    }

    public void terminalList() {
        terminalService.terminalList();
    }

    public void updateTerminal() {
        System.out.print("Enter code: ");
        String code = scanner.next();
        System.out.print("Enter new address: ");
        String address = scanner.next();

        TerminalDTO terminal = new TerminalDTO();
        terminal.setCode(code);
        terminal.setAddress(address);
        terminalService.update(terminal);
    }

    public void changeTerminalStatus() {
        System.out.print("Enter terminal code: ");
        String code = scanner.next();

        terminalService.changeTerminalStatus(code);
    }

    public void deleteTerminal() {
        System.out.print("Enter Terminal code: ");
        String code = scanner.next();

        terminalService.delete(code);
    }

    public void profileList() {
        profileService.profileList();
    }

    public void changeProfileStatus() {
        System.out.print("Enter phone: ");
        String phone = scanner.next();
        profileService.changeStatus(phone);
    }

    public void transactionList() {
        transactionService.transactionList();
    }

    public void todayTransactions() {
        transactionService.todayTransactions();
    }

    public void dailyTransactions() {
        System.out.print("Enter Date:(yyyy-MM-dd) : ");
        LocalDate date = LocalDate.parse(scanner.next());
        transactionService.dailyTransactions(date);
    }

    public void intervalTransactions() {
        System.out.print("Enter FromDate:(yyyy-MM-dd) : ");
        LocalDate fromDate = LocalDate.parse(scanner.next());
        System.out.print("Enter ToDate:(yyyy-MM-dd) : ");
        LocalDate toDate = LocalDate.parse(scanner.next());

        transactionService.intervalTransactions(fromDate, toDate);
    }

    public void transactionByTerminal() {
        System.out.print("Enter terminal code: ");
        String terminalCode = scanner.next();
        transactionService.transactionByTerminal(terminalCode);
    }

    public void transactionByCard() {
        System.out.print("Enter Card number: ");
        String cardNumber = scanner.next();
        transactionService.transactionByCard(cardNumber);
    }

    public void companyCardBalance() {
        cardService.getCompanyBalance();
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    public void setTerminalService(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
