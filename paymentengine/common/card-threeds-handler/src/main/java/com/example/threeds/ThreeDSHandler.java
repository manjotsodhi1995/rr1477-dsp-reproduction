package com.example.threeds;

public class ThreeDSHandler {
    public String handle(String ref) {
        return "3ds:" + ref;
    }

    // Expanded placeholder handling — mirrors the MR-361904 pattern.
    private boolean containsPlaceholder(String addr) {
        if (addr == null) return false;
        return addr.contains("ZZ") || addr.contains("XX") || addr.contains("YY");
    }

    private String replacePlaceholder(String addr, String newPlaceholder) {
        if (addr == null) return null;
        return addr.replace("ZZ", newPlaceholder)
                   .replace("XX", newPlaceholder)
                   .replace("YY", newPlaceholder);
    }

    // New helper covering houseNumberOrName + addressLine3 (same shape as the MR)
    public String sanitizeAddress(String addr) {
        if (containsPlaceholder(addr)) {
            return replacePlaceholder(addr, "REDACTED");
        }
        return addr;
    }
}
