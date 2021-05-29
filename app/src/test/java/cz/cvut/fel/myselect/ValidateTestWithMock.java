package cz.cvut.fel.myselect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import cz.cvut.fel.myselect.Fragments.SignUp;
import cz.cvut.fel.myselect.validators.post.PostValidator;
import cz.cvut.fel.myselect.validators.post.PostValidatorRules;
import cz.cvut.fel.myselect.validators.signin.SignInValidator;
import cz.cvut.fel.myselect.validators.signin.SignInValidatorRules;
import cz.cvut.fel.myselect.validators.signup.SignUpValidator;
import cz.cvut.fel.myselect.validators.signup.SignUpValidatorRules;

//Unit tests and mockit
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ValidateTestWithMock {
    @Mock
    SignInValidatorRules signInValidatorRules;

    @Mock
    SignUpValidatorRules signUpValidatorRules;

    @Mock
    PostValidatorRules postValidatorRules;

    @InjectMocks
    SignInValidator signInValidator;

    @InjectMocks
    SignUpValidator signUpValidator;

    @InjectMocks
    PostValidator postValidator;

    @ParameterizedTest
    @CsvSource({"kozhevik@cvut.cz, true", "k@c.z, true", "kozhevik@cvut, false", "kozhevikcvut.cz, false", "mail @cvut.cz, false", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111@mail.com, false"})
    public void mailValidateTest(String mail, boolean expected) {
        System.out.println("Test for validateEmail for " + mail);
        // ARRANGE
        Mockito.when(signInValidatorRules.getMinEmailLength()).thenReturn(4);
        Mockito.when(signInValidatorRules.getMaxEmailLength()).thenReturn(128);
        // ACT
        boolean result = signInValidator.validateEmail(mail);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"123456, true", "123, false", "......., true", "Hello world, false", "kozhevik@cvut.cz, true", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111, false"})
    public void passValidateTest(String password, boolean expected) {
        System.out.println("Test for validatePassword for " + password);
        // ARRANGE
        Mockito.when(signInValidatorRules.getMinPasswordLength()).thenReturn(6);
        Mockito.when(signInValidatorRules.getMaxPasswordLength()).thenReturn(128);
        // ACT
        boolean result = signInValidator.validatePassword(password);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"Nickna, true", "Nickn, false", "......., true", "Hello11 world11, false", "kozhevik@cvut.cz, true", "111111111111111111111111111111111, false"})
    public void validateNameTest(String name, boolean expected) {
        System.out.println("Test for validateName for " + name);
        // ARRANGE
        Mockito.when(signUpValidatorRules.getMinNicknameLength()).thenReturn(6);
        Mockito.when(signUpValidatorRules.getMaxNicknameLength()).thenReturn(32);
        // ACT
        boolean result = signUpValidator.validateName(name);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"a, true", "......., true", "Hello world! I love life!!, true", "11111111111111111111111111111111111111111111111111111111111111111, false"})
    public void validateTitleTest(String title, boolean expected) {
        System.out.println("Test for validateTitle for " + title);
        // ARRANGE
        Mockito.when(postValidatorRules.getTitleMaxLength()).thenReturn(64);
        // ACT
        boolean result = postValidator.validateTitle(title);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"aaaaa, false", "......., true", "Hello world! I love life!!, true", "aaaaaa, true", "111111111111111111111111111111 1111111111111111111111 111111111111111111111 1111111111111 111111111111111111111111111111111111 11111111111111111111111111111111111 11111111111111111111111111111 1111111111111111111111111111111111111111 111111111111111111 1111, false"})
    public void validateTextPostTest(String text, boolean expected) {
        System.out.println("Test for validateTextPost for " + text);
        // ARRANGE
        Mockito.when(postValidatorRules.getTextPostMinLength()).thenReturn(6);
        Mockito.when(postValidatorRules.getTextPostMaxLength()).thenReturn(256);
        // ACT
        boolean result = postValidator.validateTextPost(text);
        // ASSERT
        Assertions.assertEquals(result, expected);
    }


}