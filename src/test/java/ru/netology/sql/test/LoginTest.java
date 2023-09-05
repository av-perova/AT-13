package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.data.SQLHelper.cleanDatabase;

public class LoginTest {
    @AfterAll
    static void cleanDB(){
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login with exist login and password from ut test data")
    void shouldSuccessfyllyLogin (){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorIfUserIsNotValid (){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisible();
    }

    @Test
    @DisplayName("Should get error notification if verification code not valid")
    void shouldGetErrorIfVerificationCodeIsNotValid (){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificatoinVasible();
    }
}
