package ru.T1Debut.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.T1Debut.annotation.WeylandWatchingYou;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class WeylandAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WeylandAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(ru.T1Debut.annotation.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        WeylandWatchingYou annotation = method.getAnnotation(WeylandWatchingYou.class);

        Object[] args = joinPoint.getArgs();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }

        if (annotation.consoleOutput()){
            System.out.println("Метод: " + method.getName());
            System.out.println("Параметры: " + Arrays.toString(args));
            System.out.println("Результат: " + result);
        }

        if (!annotation.kafkaTopic().isEmpty()){
            String message = "Метод: " + method.getName() + ", Параметры: " + Arrays.toString(args) + ", Результат: " + result;
            kafkaTemplate.send(annotation.kafkaTopic(), message);
        }

        return result;
    }
}
