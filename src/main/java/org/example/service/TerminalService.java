package org.example.service;

import org.example.dto.TerminalDTO;
import org.example.enums.TerminalStatus;
import org.example.repository.TerminalRepository;

import java.util.List;

public class TerminalService {
    private TerminalRepository terminalRepository;
    public void create(TerminalDTO terminal) {
        if (!terminalNumberIsValid(terminal.getCode())){
            System.out.println("Terminal code is wrong! Mazgi...");
            return;
        }
        TerminalDTO exists = terminalRepository.getByCode(terminal.getCode());
        if (exists != null){
            System.out.println("Terminal already exists!");
            return;
        }

        terminalRepository.create(terminal);
        System.out.println("Success");
    }

    public void terminalList() {
        List<TerminalDTO> list = terminalRepository.terminalList();
        if (list.isEmpty()){
            System.out.println("[]");
            return;
        }
        list.forEach(System.out::println);
    }

    public void update(TerminalDTO terminal) {
        TerminalDTO exists = terminalRepository.getByCode(terminal.getCode());
        if (exists==null){
            System.out.println("Terminal not found!");
            return;
        }
        terminalRepository.update(terminal);
        System.out.println("Success");
    }

    public void changeTerminalStatus(String code) {
        TerminalDTO terminal = terminalRepository.getByCode(code);
        if (terminal==null){
            System.out.println("Terminal not found!");
            return;
        }
        TerminalStatus status = terminal.getStatus();
        if (status.equals(TerminalStatus.ACTIVE)){
            status = TerminalStatus.BLOCK;
        } else {
            status = TerminalStatus.ACTIVE;
        }
        terminal.setStatus(status);
        terminalRepository.changeStatus(terminal);
        System.out.println("Success");
    }

    public void delete(String code) {
        TerminalDTO terminal = terminalRepository.getByCode(code);
        if (terminal==null){
            System.out.println("Terminal not found");
            return;
        }
        terminalRepository.delete(code);
        System.out.println("Success");
    }
    public boolean terminalNumberIsValid(String terminalCode) {
        for (int i = 0; i < terminalCode.length(); i++) {
            char num = terminalCode.charAt(i);
            if (!Character.isDigit(num)) {
                return false;
            }
        }
        return true;
    }

    public void setTerminalRepository(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }
}
