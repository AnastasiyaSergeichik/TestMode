package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    SelenideElement form = $(".form");

    @BeforeEach
    static void setUp() {
        open("http://localhost:9999");
        //form = $("[action]");
    }

    @Test
    void shouldRegistrationActive() {
        RegistrationUser registrationUser = DataGenerator.getNewUser("active");

    }
}
