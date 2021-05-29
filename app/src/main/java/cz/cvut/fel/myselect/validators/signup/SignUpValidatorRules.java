package cz.cvut.fel.myselect.validators.signup;

public interface SignUpValidatorRules {
    int getMinEmailLength();
    int getMaxEmailLength();
    int getMinPasswordLength();
    int getMaxPasswordLength();
    int getMinNicknameLength();
    int getMaxNicknameLength();
}
