# RR-1477 Reproduction

Reproduces an Adyen customer issue: `CustomCheckAgent` (Haiku 4.5) intermittently returns
`INCONCLUSIVE` with "file not found" when a custom pre-merge check references a YAML
rules file, even though the file exists in the repo.

See `paymentengine/authentication/webapp/authentication-engine/coderabbit/dsp-feature-flag-rules.yml`.

## Scenarios (branches / PRs)
- `scenario-a/adjacent-scope` — diff in `paymentengine/common/card-threeds-handler/**` (expected: agent confused, possibly INCONCLUSIVE)
- `scenario-b/in-scope` — diff in `paymentengine/authentication/webapp/authentication-engine/src/main/java/**` (expected: enforcement check)
- `scenario-c/out-of-scope` — diff in `ui/vue/src/views/pos/**` (expected: PASS fast)
