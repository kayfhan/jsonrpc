package me.kay.service.impl;

import me.kay.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

public class ClientMessageServiceImpl implements ClientMessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public void sendToTopic(String topic, Object dto) {
        simpMessagingTemplate.convertAndSend(topic, dto);
    }
}
