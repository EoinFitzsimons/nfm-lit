package nfm.lit.racesettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result of validating race settings, containing errors and warnings.
 */
public class ValidationResult {
    private final List<String> errors;
    private final List<String> warnings;
    private final boolean valid;
    
    private ValidationResult(Builder builder) {
        this.errors = Collections.unmodifiableList(new ArrayList<>(builder.errors));
        this.warnings = Collections.unmodifiableList(new ArrayList<>(builder.warnings));
        this.valid = builder.errors.isEmpty();
    }
    
    /**
     * Returns true if validation passed (no errors, warnings are okay).
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * Returns true if there are warnings (but validation still passes).
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
    
    /**
     * Gets all validation errors.
     */
    public List<String> getErrors() {
        return errors;
    }
    
    /**
     * Gets all validation warnings.
     */
    public List<String> getWarnings() {
        return warnings;
    }
    
    /**
     * Returns a human-readable summary of errors and warnings.
     */
    public String getSummary() {
        if (isValid() && !hasWarnings()) {
            return "Valid configuration";
        }
        
        StringBuilder sb = new StringBuilder();
        
        if (!errors.isEmpty()) {
            sb.append("Errors: ");
            sb.append(String.join(", ", errors));
        }
        
        if (!warnings.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("; ");
            }
            sb.append("Warnings: ");
            sb.append(String.join(", ", warnings));
        }
        
        return sb.toString();
    }
    
    /**
     * Builder for creating ValidationResult instances.
     */
    public static class Builder {
        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        
        public Builder addError(String error) {
            errors.add(error);
            return this;
        }
        
        public Builder addWarning(String warning) {
            warnings.add(warning);
            return this;
        }
        
        public Builder addErrors(List<String> errors) {
            this.errors.addAll(errors);
            return this;
        }
        
        public Builder addWarnings(List<String> warnings) {
            this.warnings.addAll(warnings);
            return this;
        }
        
        public ValidationResult build() {
            return new ValidationResult(this);
        }
    }
    
    @Override
    public String toString() {
        return getSummary();
    }
}
