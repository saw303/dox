package ch.silviowangler.dox;

import ch.silviowangler.dox.domain.Client;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.repository.security.DoxUserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silvio Wangler on 24.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
@Component
public class ClientCapabilitySecurityAdvice {

    public static final String CLIENT_FIELD_NAME = "client";

    private DoxUserRepository doxUserRepository;

    @Autowired
    public ClientCapabilitySecurityAdvice(DoxUserRepository doxUserRepository) {
        this.doxUserRepository = doxUserRepository;
    }

    public Object verifyUserCanPerformActionOnCurrentClient(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean isAuth = isAuthenticated();
        DoxUser doxUser = null;

        if (isAuth) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = (User) principal;
            doxUser = doxUserRepository.findByUsername(user.getUsername());
        }

        boolean hasClientAssigned = false;
        boolean clientPropertyDetected = false;
        String rejectedClient = null;

        for (Object arg : joinPoint.getArgs()) {

            if (containsClient(arg)) {
                clientPropertyDetected = true;

                if (isAuth) {
                    String clientName = (String) new PropertyDescriptor(CLIENT_FIELD_NAME, arg.getClass()).getReadMethod().invoke(arg);

                    if (clientName == null) {
                        throw new IllegalArgumentException("You need to provide a client name on class " + arg.getClass());
                    }

                    for (Client client : doxUser.getClients()) {
                        if (client.getShortName().equals(clientName)) {
                            hasClientAssigned = true;
                            break;
                        }
                        rejectedClient = clientName;
                        hasClientAssigned = false;
                    }
                }
            }
        }

        if (clientPropertyDetected && !hasClientAssigned) {
            throw new AccessDeniedException("You have no access to client " + rejectedClient);
        }

        Object retVal = joinPoint.proceed();
        return retVal;
    }

    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private boolean containsClient(Object arg) {

        Map<String, Field> allFields = getAllFields(arg.getClass());

        return allFields.containsKey(CLIENT_FIELD_NAME) && allFields.get(CLIENT_FIELD_NAME).getType() == String.class;
    }

    private Map<String, Field> getAllFields(Class clazz) {
        Map<String, Field> fields = new HashMap<>();

        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());

        for (Field field : fieldList) {
            fields.put(field.getName(), field);
        }

        if (clazz.getSuperclass() != null) {
            fields.putAll(getAllFields(clazz.getSuperclass()));
        }
        return fields;
    }
}
