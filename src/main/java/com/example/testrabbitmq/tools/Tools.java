package com.example.testrabbitmq.tools;

import java.util.UUID;

public class Tools {

    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
