package com.example.threeds;

public class ThreeDSHandler {
    public String handle(String ref) {
        return "3ds:" + ref;
    }

    private boolean containsPlaceholder(String addr) {
        return addr != null && addr.contains("ZZ");
    }

    private String replacePlaceholder(String addr, String newPlaceholder) {
        if (addr == null) return null;
        return addr.replace("ZZ", newPlaceholder);
    }
}
