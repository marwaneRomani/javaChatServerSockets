package org.est.chat;

import lombok.Data;

import java.io.Serializable;

public @Data class Message implements Serializable {
    private String id;
    private String message;

    private User sender;
    private User receiver;

}
