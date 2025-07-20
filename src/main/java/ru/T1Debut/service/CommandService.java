package ru.T1Debut.service;

import org.springframework.stereotype.Service;
import ru.T1Debut.util.CommandTask;
import ru.T1Debut.exception.QueueOverflowException;
import ru.T1Debut.dto.CommandRequestDto;

@Service
public class CommandService {

    public final CommandQueue commandQueue;
    public final TaskMetrics taskMetrics;

    public CommandService(CommandQueue commandQueue, TaskMetrics taskMetrics) {
        this.commandQueue = commandQueue;
        this.taskMetrics = taskMetrics;
    }

    public void handleCommand(CommandRequestDto commandRequestDto) throws QueueOverflowException {
        if (commandRequestDto.getPriority() == CommandRequestDto.Priority.CRITICAL) {
            System.out.println("Получена критическая команда: " + commandRequestDto.getDescription());
            taskMetrics.registerCompletedByAuthor(commandRequestDto.getAuthor());
        } else {
            try{
                commandQueue.submit(new CommandTask(() -> {
                    System.out.println("Выполняется обычная задача: " + commandRequestDto.getDescription());
                    taskMetrics.registerCompletedByAuthor(commandRequestDto.getAuthor());
                }));
            } catch (QueueOverflowException e){
                throw new QueueOverflowException("Очередь переполнена");
            }
        }
    }
}
