//package com.kun.controller.commom;
//
//import com.kun.result.Result;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Parameter;
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Flux;
//
//import java.util.Map;
//
//@Slf4j
//@RestController
//public class ChatController {
//
//    private final OpenAiChatModel chatModel;
//
//    @Autowired
//    public ChatController(OpenAiChatModel chatModel) {
//        this.chatModel = chatModel;
//    }
//
//    @GetMapping("/ai/generate")
//    public Map generate(@Parameter(description = "输入的消息", required = true) @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        return Map.of("generation", this.chatModel.call(message));
//    }
//
//    @GetMapping(value = "/ai/generateStream",produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
//    public Flux<ChatResponse> generateStream(@Parameter(description = "输入的消息", required = true) @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        Prompt prompt = new Prompt(new UserMessage(message));
//        return this.chatModel.stream(prompt);
//    }
//}
