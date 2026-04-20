package com.example.auth;

/**
 * In-scope file — lives under paymentengine/authentication/webapp/authentication-engine/src/main/java/**
 * so dsp-feature-flag-rules.yml applies here.
 *
 * Intentional compliance test cases (per dsp-feature-flag-rules.yml constraints):
 *   [VIOLATION 1] Unregistered flag: MFA_NEW_PATH_EXPERIMENT is not listed in the rules YAML's `flags:` section.
 *   [VIOLATION 2] Unregistered flag: RISK_SCORE_V3 is not listed either.
 *   [COMPLIANT]   ACCOUNT_FETCHING_V2 is registered in the YAML and guarded by FeatureFlagService.isEnabled().
 *   [CLEANUP]     Removes THREEDS_HANDLER_OPTIMIZATION reads — should be allowed via `cleanup_exception`.
 *
 * All reads use FeatureFlagService.isEnabled(), satisfying the "guard presence" constraint.
 */
public class MfaGateway {
    private final FeatureFlagService featureFlags;

    public MfaGateway(FeatureFlagService featureFlags) {
        this.featureFlags = featureFlags;
    }

    public String route(String userId, String riskSignal) {
        // [VIOLATION 1] MFA_NEW_PATH_EXPERIMENT — not registered in dsp-feature-flag-rules.yml
        if (featureFlags.isEnabled("MFA_NEW_PATH_EXPERIMENT")) {
            return mfaPath(userId);
        }

        // [VIOLATION 2] RISK_SCORE_V3 — not registered in dsp-feature-flag-rules.yml
        if (featureFlags.isEnabled("RISK_SCORE_V3") && riskSignal != null) {
            return riskScorePath(userId, riskSignal);
        }

        // [COMPLIANT] ACCOUNT_FETCHING_V2 is registered in the YAML
        if (featureFlags.isEnabled("ACCOUNT_FETCHING_V2")) {
            return accountFetchV2(userId);
        }

        return legacyPath(userId);
    }

    private String mfaPath(String uid)              { return "mfa:" + uid; }
    private String riskScorePath(String uid, String r) { return "risk:" + uid + ":" + r; }
    private String accountFetchV2(String uid)       { return "v2:" + uid; }
    private String legacyPath(String uid)           { return "legacy:" + uid; }
}
