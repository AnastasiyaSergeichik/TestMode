package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {
    SelenideElement form = $(".form");
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        form = $("[action]");
    }

    @Test
    void shouldSubmitRequestUserStatusActive() {
        RegistrationUser registrationUser = DataGenerator.getNewUser("active");
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void shouldSubmitRequestUserStatusBlocked() {

        RegistrationUser registrationUser = DataGenerator.getNewUser("blocked");
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        String successText = $("[data-test-id=error-notification]").getText();
        assertEquals("Ошибка\nОшибка! Пользователь заблокирован", successText);
    }

    @Test
    void shouldSubmitRequestInvalidLogin() {


        RegistrationUser registrationUser = DataGenerator.getNewUser("active");
        form.$("[data-test-id=login] input").setValue(faker.name().firstName());
        form.$("[data-test-id=password] input").setValue(registrationUser.getPassword());
        $$(".button").find(exactText("Продолжить")).click();
        String successText = $("[data-test-id=error-notification]").getText();
        assertEquals("Ошибка\nОшибка! Неверно указан логин или пароль", successText);
    }

    @Test
    void shouldSubmitRequestInvalidPassword() {

        RegistrationUser registrationUser = DataGenerator.getNewUser("active");
        form.$("[data-test-id=login] input").setValue(registrationUser.getLogin());
        form.$("[data-test-id=password] input").setValue(faker.internet().password());
        $$(".button").find(exactText("Продолжить")).click();
        String successText = $("[data-test-id=error-notification]").getText();
        assertEquals("Ошибка\nОшибка! Неверно указан логин или пароль", successText);
    }
}
