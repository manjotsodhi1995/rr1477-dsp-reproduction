package com.example.auth.mfa;

import com.example.auth.FeatureFlagService;

/**
 * In-scope file. Tests the "Feature flag reads MUST be guarded by
 * FeatureFlagService.isEnabled()" constraint via both compliant and
 * (subtly) non-guarded patterns.
 *
 *   [COMPLIANT]   ACCOUNT_FETCHING_V2     — registered + guarded via isEnabled()
 *   [VIOLATION]   STEP_UP_CHALLENGE_V3    — unregistered flag
 *   [EDGE CASE]   OTP_SMS_FALLBACK        — read with a plain config lookup,
 *                                            NOT via FeatureFlagService.isEnabled()
 */
public class MfaChallengeRouter {
    private final FeatureFlagService featureFlags;
    private final FlagConfigStore rawConfig;

    public MfaChallengeRouter(FeatureFlagService featureFlags, FlagConfigStore rawConfig) {
        this.featureFlags = featureFlags;
        this.rawConfig = rawConfig;
    }

    public String route(String userId) {
        if (featureFlags.isEnabled("STEP_UP_CHALLENGE_V3")) {
            return stepUp(userId);
        }
        // [EDGE CASE] bypasses the mandated guard — reads config key directly.
        if ("true".equalsIgnoreCase(rawConfig.lookup("OTP_SMS_FALLBACK"))) {
            return smsFallback(userId);
        }
        if (featureFlags.isEnabled("ACCOUNT_FETCHING_V2")) {
            return enrichedAuth(userId);
        }
        return basicAuth(userId);
    }

    private String stepUp(String uid)       { return "stepup:" + uid; }
    private String smsFallback(String uid)  { return "sms:" + uid; }
    private String enrichedAuth(String uid) { return "enriched:" + uid; }
    private String basicAuth(String uid)    { return "basic:" + uid; }
}
