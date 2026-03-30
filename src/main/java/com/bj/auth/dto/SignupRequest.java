package com.bj.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank
    @Email
    private String email = "";

    @NotBlank
    @Size(min = 8, max = 100)
    private String password = "";

    @NotBlank
    @Size(min = 8, max = 100)
    private String passwordConfirm = "";

    @NotBlank
    @Size(max = 50)
    private String displayName = "";

    private boolean termsAgreed;
    private boolean privacyAgreed;
    private boolean marketingAgreed;

    public SignupRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isTermsAgreed() {
        return termsAgreed;
    }

    public void setTermsAgreed(boolean termsAgreed) {
        this.termsAgreed = termsAgreed;
    }

    public boolean isPrivacyAgreed() {
        return privacyAgreed;
    }

    public void setPrivacyAgreed(boolean privacyAgreed) {
        this.privacyAgreed = privacyAgreed;
    }

    public boolean isMarketingAgreed() {
        return marketingAgreed;
    }

    public void setMarketingAgreed(boolean marketingAgreed) {
        this.marketingAgreed = marketingAgreed;
    }
}
