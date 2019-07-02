package me.kay.service;

public interface ClientMessageService {

    void sendToTopic(String topic, Object dto);
}
