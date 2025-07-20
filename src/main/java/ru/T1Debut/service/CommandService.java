package ru.T1Debut.service;

import org.springframework.stereotype.Service;
import ru.T1Debut.annotation.WeylandWatchingYou;
import ru.T1Debut.util.CommandTask;
import ru.T1Debut.exception.QueueOverflowException;
import ru.T1Debut.model.Command;

@Service
public class CommandService {

    public final CommandQueue commandQueue;
    public final TaskMetrics taskMetrics;

    public CommandService(CommandQueue commandQueue, TaskMetrics taskMetrics) {
        this.commandQueue = commandQueue;
        this.taskMetrics = taskMetrics;
    }

    @WeylandWatchingYou
    public void process(Command command) throws QueueOverflowException {
        if (command.getPriority() == Command.Priority.CRITICAL) {
            System.out.println("Получена критическая команда: " + command.getDescription());
            taskMetrics.registerCompletedByAuthor(command.getAuthor());
        } else {
            try{
                commandQueue.submit(new CommandTask(() -> {
                    System.out.println("Выполняется обычная задача: " + command.getDescription());
                    taskMetrics.registerCompletedByAuthor(command.getAuthor());
                }));
            } catch (QueueOverflowException e){
                throw new QueueOverflowException("Очередь переполнена");
            }
        }
    }
}
