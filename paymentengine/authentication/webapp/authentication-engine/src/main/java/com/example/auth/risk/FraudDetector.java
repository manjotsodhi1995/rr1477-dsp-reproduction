package com.example.auth.risk;

import com.example.auth.FeatureFlagService;

/**
 * In-scope: paymentengine/authentication/webapp/authentication-engine/src/main/java/**
 * Exercises DSP-FEATURE-FLAG-ENFORCEMENT:
 *   [VIOLATION] FRAUD_ML_V2       — not in rules YAML `flags:` list
 *   [VIOLATION] DEVICE_BINDING    — not in rules YAML `flags:` list
 *   [COMPLIANT] ACCOUNT_FETCHING_V2 — registered and guarded
 */
public class FraudDetector {
    private final FeatureFlagService featureFlags;

    public FraudDetector(FeatureFlagService featureFlags) {
        this.featureFlags = featureFlags;
    }

    public double score(String userId, String deviceId) {
        double risk = 0.3;

        // [VIOLATION] new ML model behind an unregistered flag
        if (featureFlags.isEnabled("FRAUD_ML_V2")) {
            risk = runMlScoring(userId);
        }

        // [VIOLATION] device fingerprint check behind another unregistered flag
        if (featureFlags.isEnabled("DEVICE_BINDING")) {
            risk += deviceBindingBoost(deviceId);
        }

        // [COMPLIANT] registered flag
        if (featureFlags.isEnabled("ACCOUNT_FETCHING_V2")) {
            risk *= 0.9;
        }
        return Math.min(risk, 1.0);
    }

    private double runMlScoring(String uid)           { return 0.5; }
    private double deviceBindingBoost(String device)  { return 0.1; }
}
