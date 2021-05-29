package cz.cvut.fel.myselect.validators.signin;

public interface SignInValidatorRules {
    int getMinEmailLength();
    int getMaxEmailLength();
    int getMinPasswordLength();
    int getMaxPasswordLength();
}
