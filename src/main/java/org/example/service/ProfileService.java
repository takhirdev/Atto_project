package org.example.service;

import org.example.container.ComponentContainer;
import org.example.controller.AdminController;
import org.example.controller.UserController;
import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileCardRepository;
import org.example.repository.ProfileRepository;
import org.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCardRepository profileCardRepository;
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserController userController;
    public void registration(ProfileDTO profile) {

        ProfileDTO exists = profileRepository.getByPhone(profile.getPhone());
        if (exists != null) {
            System.out.println("Profile already exists. Mazgi!");
            return;
        }
        if (!numberValidation(profile.getPhone())) {
            System.out.println("Wrong number!");
            return;
        }
        profile.setRole(ProfileRole.USER);
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.registration(profile);
        System.out.println("Success");

    }

    public void login(String phone, String password) {

        ProfileDTO profile = profileRepository.getByPhone(phone);
        if (profile == null) {
            System.out.println("Profile not found!");
            return;
        }
        if (!profile.getPassword().equals(MD5Util.getMd5(password))) {
            System.out.println("Password is wrong!");
            return;
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCK)) {
            System.out.println("Profile status not active!");
            return;
        }

        profile = ComponentContainer.currentProfile;

        if (profile.getRole().equals(ProfileRole.ADMIN)) {
            adminController.start();
        } else {
            userController.start();
        }
    }

    public void profileList() {
        List<ProfileDTO> list = profileRepository.profileList();
        if (list.isEmpty()){
            System.out.println("[]");
            return;
        }
        for (ProfileDTO profile : list) {
            Integer id = profile.getId();
            Integer cardCount = profileCardRepository.getCardCount(id);
            profile.setCardCount(cardCount);
            System.out.println(profile);
        }
    }

    public void changeStatus(String phone) {
        ProfileDTO profile = profileRepository.getByPhone(phone);
        if (profile==null){
            System.out.println("Profile not found!");
            return;
        }
        ProfileStatus status = profile.getStatus();
        if (status.equals(ProfileStatus.ACTIVE)){
            status = ProfileStatus.BLOCK;
        } else {
            status = ProfileStatus.ACTIVE;
        }
        profile.setStatus(status);
        profileRepository.changeStatus(profile);
        System.out.println("Success");
    }

    public boolean isNumeric(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            char num = phone.charAt(i);
            if (!Character.isDigit(num)) {
                return false;
            }
        }
        return true;
    }

    public boolean numberValidation(String phone) {
        if (isNumeric(phone) && phone.startsWith("9989") && phone.length() == 12) {
            return true;
        }
        return false;
    }
}
