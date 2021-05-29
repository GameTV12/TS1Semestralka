package cz.cvut.fel.myselect.validators.signin;

public class SignInValidator {
    private SignInValidatorRules rules;

    public SignInValidator(SignInValidatorRules rules) {
        this.rules = rules;
    }

    public boolean validateEmail(String email) {
        if ((email.length() < rules.getMinEmailLength()) || (email.length() > rules.getMaxEmailLength()) || !(email.indexOf('@') != -1) || !(email.indexOf('.') != -1) || (email.indexOf(' ') != -1)) {
            return false;
        }

        return true;
    }

    public boolean validatePassword(String password) {
        if ((password.length() < rules.getMinPasswordLength()) || (password.length() > rules.getMaxPasswordLength()) || (password.indexOf(' ') != -1)) {
            return false;
        }

        return true;
    }

    public static SignInValidator Default = new SignInValidator(new SignInValidatorRules() {
        @Override
        public int getMinEmailLength() {
            return 4;
        }

        @Override
        public int getMaxEmailLength() {
            return 128;
        }

        @Override
        public int getMinPasswordLength() {
            return 6;
        }

        @Override
        public int getMaxPasswordLength() {
            return 128;
        }
    });
}
