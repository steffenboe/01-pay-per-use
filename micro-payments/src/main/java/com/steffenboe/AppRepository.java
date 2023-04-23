package com.steffenboe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class AppRepository {

    private List<App> apps = new ArrayList<>();

    public Optional<App> findById(UUID appId) {
        return apps.stream().filter(app -> app.appId().equals(appId)).findFirst();
    }

    public void save(App app) {
        apps.add(app);
    }
}
