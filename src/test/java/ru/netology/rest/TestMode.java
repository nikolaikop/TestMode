package ru.netology.rest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestMode {

    @BeforeEach
    void before() {
        open("http://localhost:9999");
    }

    @Test
    void inputAllValidData() {
        val user = DataGeneration.nameData("active");
        DataGeneration.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("body div#root h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void ifUserIsBlocked() {
        val user = DataGeneration.nameData("blocked");
        DataGeneration.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void ifLoginIsNotCorrect() {
        val user = DataGeneration.nameData("active");
        DataGeneration.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(DataGeneration.wrongLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void ifPwIsNotCorrect() {
        val user = DataGeneration.nameData("active");
        DataGeneration.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(DataGeneration.wrongPw());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
