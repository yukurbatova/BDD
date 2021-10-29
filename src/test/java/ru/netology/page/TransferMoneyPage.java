package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class TransferMoneyPage {
    private final SelenideElement heading = $(withText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement from = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement error = $("[data-test-id='error-notification']");

    public TransferMoneyPage(){
        heading.shouldBe(visible);
    }
    public DashboardPage successTransfer(String sum, DataHelper.CardNumber cardNumber){
        amount.setValue(sum);
        from.setValue(String.valueOf(cardNumber));
        transferButton.click();
        return new DashboardPage();
    }
    public void errorMessage(){
        error.shouldBe(visible);
    }
}
