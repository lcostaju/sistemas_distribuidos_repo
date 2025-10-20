package br.edu.iftm.tspi.sd.websockets_exemplo.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler.ChatController;
import br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler.Mensagem;
import br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler.TipoMensagem;

import java.time.Instant;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatController chatController;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        
        if(username != null) {
            Mensagem mensagem = new Mensagem();
            mensagem.setTipoMensagem(TipoMensagem.SAIR);
            mensagem.setOrigem(username);
            mensagem.setDataHora(Instant.now());
            mensagem.setTexto(username + " saiu (desconectado)");

            chatController.removerUsuario(username);
            messagingTemplate.convertAndSend("/topic/public", mensagem);
        }
    }
}