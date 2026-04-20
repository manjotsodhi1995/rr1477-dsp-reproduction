package com.example.auth;

public interface FeatureFlagService {
    boolean isEnabled(String flagName);
}
