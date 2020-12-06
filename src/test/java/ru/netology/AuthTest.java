package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {
    SelenideElement form = $(".form");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        form = $("[action]");
    }

    @Test
    void shouldSubmitRequestUserStatusActive() {
        RegistrationUser registrationUser = DataGenerator.generateUserStatusActive();
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void shouldSubmitRequestUserStatusBlocked() {
        RegistrationUser registrationUser = DataGenerator.generateUserStatusBlocked();
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        $(".notification_status_error").waitUntil(Condition.visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldSubmitRequestInvalidLogin() {
        RegistrationUser registrationUser = DataGenerator.generateUserInvalidLogin();
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        $(".notification_status_error").waitUntil(Condition.visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldSubmitRequestInvalidPassword() {
        RegistrationUser registrationUser = DataGenerator.generateNewUserInvalidPassword();
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        $(".notification_status_error").waitUntil(Condition.visible, 15000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }
}
