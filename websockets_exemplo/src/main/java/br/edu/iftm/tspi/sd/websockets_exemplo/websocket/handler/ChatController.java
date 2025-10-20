package br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final Set<String> usuariosOnline = new HashSet<>();
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Mensagem enviartexto(Mensagem mensagem){
        mensagem.setTipoMensagem(TipoMensagem.ENVIAR_TEXTO);
        mensagem.setDataHora(Instant.now());
        return mensagem;
    }

    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public Mensagem entrar(Mensagem mensagem, SimpMessageHeaderAccessor headerAccessor){
        mensagem.setTipoMensagem(TipoMensagem.ENTRAR);
        mensagem.setDataHora(Instant.now());
        mensagem.setTexto(mensagem.getOrigem() + " entrou");
        
        headerAccessor.getSessionAttributes().put("username", mensagem.getOrigem());
        usuariosOnline.add(mensagem.getOrigem());
        enviarListaUsuariosOnline();
        
        return mensagem;
    }

    @MessageMapping("/chat.leave")
    @SendTo("/topic/public")
    public Mensagem sair(Mensagem mensagem){
        mensagem.setTipoMensagem(TipoMensagem.SAIR);
        mensagem.setDataHora(Instant.now());
        mensagem.setTexto(mensagem.getOrigem() + " saiu");
        
        removerUsuario(mensagem.getOrigem());
        
        return mensagem;
    }
    
    @MessageMapping("/chat.private")
    public void enviarMensagemPrivada(Mensagem mensagem) {
        mensagem.setTipoMensagem(TipoMensagem.PRIVADO);
        mensagem.setDataHora(Instant.now());
        messagingTemplate.convertAndSend("/topic/dm." + mensagem.getDestino(), mensagem);
    }
    
    public void removerUsuario(String username) {
        usuariosOnline.remove(username);
        enviarListaUsuariosOnline();
    }
    
    private void enviarListaUsuariosOnline() {
        messagingTemplate.convertAndSend("/topic/online", usuariosOnline);
    }
}
