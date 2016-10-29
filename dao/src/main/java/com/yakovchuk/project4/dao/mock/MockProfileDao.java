package com.yakovchuk.project4.dao.mock;

import com.yakovchuk.project4.dao.ProfileDao;
import com.yakovchuk.project4.profile.NullProfile;
import com.yakovchuk.project4.profile.Profile;
import com.yakovchuk.project4.profile.ProfileImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MockProfileDao implements ProfileDao {

    Map<String, String> profilePasswordMap;
    Map<String, Profile> profileMap;

    public MockProfileDao() {
        profilePasswordMap = new HashMap<>();

        profilePasswordMap.put("tutor", "tutor");
        profilePasswordMap.put("student", "student");

        profileMap = new HashMap<>();

        ProfileImpl tutor = new ProfileImpl();
        tutor.setLogin("tutor");
        tutor.setRoles(Arrays.asList("tutor", "student"));

        ProfileImpl student = new ProfileImpl();
        student.setLogin("student");
        student.setRoles(Arrays.asList("student"));

        profileMap.put("tutor", tutor);
        profileMap.put("student", student);
    }

    @Override
    public Profile getProfile(String login, String password) {
        if (profilePasswordMap.get(login) != null && profilePasswordMap.get(login).equals(password)) {
            if (profileMap.get(login) != null) {
                return profileMap.get(login);
            }
        }

        return NullProfile.getInstance();
    }

    @Override
    public Profile changeLanguage(Profile profile, String newLanguage) {
        ProfileImpl updatedProfile = new ProfileImpl();
        updatedProfile.setLogin(profile.getLogin());
        updatedProfile.setRoles(profile.getRoles());
        updatedProfile.setLanguageCode(newLanguage);
        return updatedProfile;
    }
}
