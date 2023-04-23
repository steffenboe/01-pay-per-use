package com.steffenboe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserRepository {

    private List<AppUser> appUsers = new ArrayList<>();

    Optional<AppUser> findById(UUID appId, String userId) {
        return appUsers.stream().filter(user -> user.appId().equals(appId) && user.userId().equals(userId)).findFirst();
    }

    void save(AppUser appUser) {
        appUsers.add(appUser);
    }
}
