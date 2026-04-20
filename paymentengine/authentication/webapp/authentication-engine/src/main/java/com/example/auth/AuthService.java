package com.example.auth;

public class AuthService {
    private final FeatureFlagService featureFlags;

    public AuthService(FeatureFlagService featureFlags) {
        this.featureFlags = featureFlags;
    }

    public String authenticate(String userId) {
        // Intentional violation: reads a feature flag WITHOUT a guard wrapper,
        // and uses a flag not registered in dsp-feature-flag-rules.yml.
        if (featureFlags.isEnabled("MFA_NEW_PATH_EXPERIMENT")) {
            return fetchAccountMfa(userId);
        }
        if (featureFlags.isEnabled("ACCOUNT_FETCHING_V2")) {
            return fetchAccountV2(userId);
        }
        return fetchAccountLegacy(userId);
    }

    private String fetchAccountMfa(String userId) {
        return "mfa:" + userId;
    }

    private String fetchAccountV2(String userId) {
        return "v2:" + userId;
    }

    private String fetchAccountLegacy(String userId) {
        return "v1:" + userId;
    }
}
