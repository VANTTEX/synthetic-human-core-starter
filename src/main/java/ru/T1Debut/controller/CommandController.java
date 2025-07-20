//package ru.T1Debut.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import ru.T1Debut.exception.QueueOverflowException;
//import ru.T1Debut.dto.CommandRequestDto;
//import ru.T1Debut.service.CommandService;
//
//@RestController
//@RequestMapping("/commands")
//public class CommandController {
//
//    private final CommandService commandService;
//
//    public CommandController(CommandService commandService) {
//        this.commandService = commandService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> receiveCommand(@Valid @RequestBody CommandRequestDto commandRequestDto) throws  QueueOverflowException {
//        commandService.handleCommand(commandRequestDto);
//        return ResponseEntity.accepted().build();
//    }
//}
