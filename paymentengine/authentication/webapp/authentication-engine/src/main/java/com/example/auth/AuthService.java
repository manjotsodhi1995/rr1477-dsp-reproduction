package com.example.auth;

public class AuthService {
    private final FeatureFlagService featureFlags;

    public AuthService(FeatureFlagService featureFlags) {
        this.featureFlags = featureFlags;
    }

    public String authenticate(String userId) {
        // CLEANUP: THREEDS_HANDLER_OPTIMIZATION branch removed — per dsp-feature-flag-rules.yml
        // `cleanup_exception`, flag removals are allowed without registration entries.
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
