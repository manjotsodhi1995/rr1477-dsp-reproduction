package com.example.auth.flags;

import java.util.List;

/**
 * Compliance helper: enumerates the flags CURRENTLY REGISTERED
 * in dsp-feature-flag-rules.yml so reviewers (and the pre-merge check)
 * can cross-check code vs YAML.
 *
 * Keep this list in sync with the `flags:` block of the rules YAML.
 */
public final class FlagsRegistry {
    public static final List<String> REGISTERED_FLAGS = List.of(
        "ACCOUNT_FETCHING_V2",
        "THREEDS_HANDLER_OPTIMIZATION"
    );
    private FlagsRegistry() {}
}
