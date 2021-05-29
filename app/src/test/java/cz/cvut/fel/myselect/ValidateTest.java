package cz.cvut.fel.myselect;

import org.junit.jupiter.api.Assertions;

import cz.cvut.fel.myselect.Fragments.SignUp;
import cz.cvut.fel.myselect.validators.post.PostValidator;
import cz.cvut.fel.myselect.validators.signin.SignInValidator;
import cz.cvut.fel.myselect.validators.signup.SignUpValidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

//Unit tests without mockito

class ValidateTest {
    @ParameterizedTest
    @CsvSource({"kozhevik@cvut.cz, true", "k@c.z, true", "kozhevik@cvut, false", "kozhevikcvut.cz, false", "mail @cvut.cz, false", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111@mail.com, false"})
    public void mailValidateTest(String mail, boolean expected) {
        System.out.println("Test for validateEmail for " + mail);
        // ARRANGE
        SignInValidator validator = SignInValidator.Default;
        // ACT
        boolean result = validator.validateEmail(mail);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"123456, true", "123, false", "......., true", "Hello world, false", "kozhevik@cvut.cz, true", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111, false"})
    public void passValidateTest(String password, boolean expected) {
        System.out.println("Test for validatePassword for " + password);
        // ARRANGE
        SignInValidator validator = SignInValidator.Default;
        // ACT
        boolean result = validator.validatePassword(password);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"Nickna, true", "Nickn, false", "......., true", "Hello11 world11, false", "kozhevik@cvut.cz, true", "111111111111111111111111111111111, false"})
    public void validateNameTest(String name, boolean expected) {
        System.out.println("Test for validateName for " + name);
        // ARRANGE
        SignUpValidator validator = SignUpValidator.Default;
        // ACT
        boolean result = validator.validateName(name);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"a, true", "......., true", "Hello world! I love life!!, true", "11111111111111111111111111111111111111111111111111111111111111111, false"})
    public void validateTitleTest(String title, boolean expected) {
        System.out.println("Test for validateTitle for " + title);
        // ARRANGE
        PostValidator validator = PostValidator.Default;

        // ACT
        boolean result = validator.validateTitle(title);

        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"aaaaa, false", "......., true", "Hello world! I love life!!, true", "aaaaaa, true", "111111111111111111111111111111 1111111111111111111111 111111111111111111111 1111111111111 111111111111111111111111111111111111 11111111111111111111111111111111111 11111111111111111111111111111 1111111111111111111111111111111111111111 111111111111111111 1111, false"})
    public void validateTextPostTest(String text, boolean expected) {
        System.out.println("Test for validateTextPost for " + text);
        // ARRANGE
        PostValidator validator = PostValidator.Default;

        // ACT
        boolean result = validator.validateTextPost(text);

        // ASSERT
        Assertions.assertEquals(result, expected);
    }


}