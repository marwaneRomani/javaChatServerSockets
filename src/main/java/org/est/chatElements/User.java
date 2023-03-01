package org.est.chatElements;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public @Data class User implements Serializable {
    private String id;
    private String name;


    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();

}
