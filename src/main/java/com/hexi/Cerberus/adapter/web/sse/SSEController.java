package com.hexi.Cerberus.adapter.web.sse;

import com.hexi.Cerberus.adapter.web.sse.Messages.SSEMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/sse")
@Slf4j
public class SSEController {
    private final SseEmitter sseEmitter = new SseEmitter();

    @GetMapping("/events")
    public SseEmitter handleSse() {
        return sseEmitter;
    }

    // Метод для отправки SSE событий
    public void sendSseEvent(SSEMessage message) {
        log.debug("SSE Emitting: " + message.get_message());
        try {
            sseEmitter.send(SseEmitter.event().name("message").data(message.get_message()));
        } catch (IOException e) {
            // Обработка ошибок отправки SSE
            e.printStackTrace();
        }
    }
}
