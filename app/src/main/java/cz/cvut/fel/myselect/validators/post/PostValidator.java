package cz.cvut.fel.myselect.validators.post;

public class PostValidator {
    private PostValidatorRules rules;

    public PostValidator(PostValidatorRules rules) {
        this.rules = rules;
    }

    public boolean validateTitle(String title) {
        if ((title.length() > rules.getTitleMaxLength())) {
            return false;
        }
        return true;
    }

    public boolean validateTextPost(String text) {
        if ((text.length() > rules.getTextPostMaxLength() || text.length() < rules.getTextPostMinLength())) {
            return false;
        }
        return true;
    }

    public static PostValidator Default = new PostValidator(new PostValidatorRules() {
        @Override
        public int getTitleMaxLength() {
            return 64;
        }

        @Override
        public int getTextPostMinLength() {
            return 6;
        }

        @Override
        public int getTextPostMaxLength() {
            return 256;
        }
    });
}
