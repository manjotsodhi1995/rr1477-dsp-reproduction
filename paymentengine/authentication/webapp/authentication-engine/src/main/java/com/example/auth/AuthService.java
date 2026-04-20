package com.example.auth;

public class AuthService {
    private final FeatureFlagService featureFlags;

    public AuthService(FeatureFlagService featureFlags) {
        this.featureFlags = featureFlags;
    }

    public String authenticate(String userId) {
        if (featureFlags.isEnabled("ACCOUNT_FETCHING_V2")) {
            return fetchAccountV2(userId);
        }
        return fetchAccountLegacy(userId);
    }

    private String fetchAccountV2(String userId) {
        return "v2:" + userId;
    }

    private String fetchAccountLegacy(String userId) {
        return "v1:" + userId;
    }
}
