package cz.cvut.fel.myselect.validators.signup;

public class SignUpValidator {
    private SignUpValidatorRules rules;

    public SignUpValidator(SignUpValidatorRules rules) {
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

    public boolean validateName(String name) {
        if ((name.length() < rules.getMinNicknameLength()) || (name.length() > rules.getMaxNicknameLength()) || (name.indexOf(' ') != -1)) {
            return false;
        }
        return true;
    }

    public static SignUpValidator Default = new SignUpValidator(new SignUpValidatorRules() {
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

        @Override
        public int getMinNicknameLength() {
            return 6;
        }

        @Override
        public int getMaxNicknameLength() {
            return 32;
        }
    });
}
