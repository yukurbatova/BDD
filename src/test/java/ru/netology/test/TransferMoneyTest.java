package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }

    @Test
    void shouldTransferMoneyToFirstCard() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.transferMoneyToFirstCard();
        String sum = "10";
        var card = DataHelper.getSecondCardNumber();
        transferMoney.successTransfer(sum, card);
        assertEquals(firstBalance + Integer.parseInt(sum), dashBoardPage.getFirstCardBalance());
        assertEquals(secondBalance - Integer.parseInt(sum), dashBoardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyToSecondCard() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.transferMoneyToSecondCard();
        String sum = "100";
        var card = DataHelper.getFirstCardNumber();
        transferMoney.successTransfer(sum, card);
        assertEquals(firstBalance - Integer.parseInt(sum), dashBoardPage.getFirstCardBalance());
        assertEquals(secondBalance + Integer.parseInt(sum), dashBoardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyFromInvalidCard() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.transferMoneyToSecondCard();
        String sum = "200";
        var card = DataHelper.getInvalidCardNumber();
        transferMoney.successTransfer(sum, card);
        transferMoney.errorMessage();
    }

    @Test
    void shouldTransferMoneyIfSumMoreBalance() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.transferMoneyToSecondCard();
        String sum = "9911";
        var card = DataHelper.getFirstCardNumber();
        transferMoney.successTransfer(sum, card);
        assertEquals(firstBalance, dashBoardPage.getFirstCardBalance());
        assertEquals(secondBalance, dashBoardPage.getSecondCardBalance());
    }
}
