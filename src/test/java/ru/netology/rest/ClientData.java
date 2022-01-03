package ru.netology.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientData {

    private final String login;
    private final String password;
    private final String status;
}
