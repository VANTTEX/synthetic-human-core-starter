package ru.T1Debut.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.T1Debut.annotation.WeylandWatchingYou;
import ru.T1Debut.configuration.WeylandAuditProperties;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class WeylandAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WeylandAuditProperties properties;

    public WeylandAspect(
            KafkaTemplate<String, String> kafkaTemplate,
            WeylandAuditProperties properties
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    @Around("@annotation(ru.T1Debut.annotation.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Object[] args = joinPoint.getArgs();
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }

        if (properties.isConsoleOutput()) {
            System.out.println("Метод: " + method.getName());
            System.out.println("Параметры: " + Arrays.toString(args));
            System.out.println("Результат: " + result);
        }

        String topic = properties.getKafkaTopic();
        if (topic != null && !topic.isEmpty()) {
            String message = "Метод: " + method.getName() + ", Параметры: " + Arrays.toString(args) + ", Результат: " + result;
            kafkaTemplate.send(topic, message);
        }

        return result;
    }
}
