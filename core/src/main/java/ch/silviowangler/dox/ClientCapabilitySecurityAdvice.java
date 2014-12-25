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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

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
        List<String> clientAccessDeniedList = newArrayList();

        if (isAuth) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = (User) principal;
            doxUser = doxUserRepository.findByUsername(user.getUsername());
        }

        for (Object arg : joinPoint.getArgs()) {
            if (doxUser != null) {
                clientAccessDeniedList.addAll(analyze(arg, doxUser));
            }
        }

        if (!clientAccessDeniedList.isEmpty()) {
            return throwAccessDeniedException(clientAccessDeniedList);
        }

        Object retVal = joinPoint.proceed();

        if (retVal != null) {

            if (retVal instanceof Iterable) {
                Iterable iterable = (Iterable) retVal;
                for (Object arg : iterable) {
                    if (containsClient(arg)) {
                        clientAccessDeniedList.addAll(analyze(arg, doxUser));
                    }
                }
            } else {
                if (containsClient(retVal)) {
                    clientAccessDeniedList = analyze(retVal, doxUser);
                }
            }

            if (!clientAccessDeniedList.isEmpty()) {
                throwAccessDeniedException(clientAccessDeniedList);
            }
        }
        return retVal;
    }

    private Object throwAccessDeniedException(List<String> clientAccessDeniedList) {
        StringBuilder sb = new StringBuilder("You have no access to ");

        if (clientAccessDeniedList.size() > 1) {
            sb.append("clients ");

            for (int i = 0; i < clientAccessDeniedList.size(); i++) {
                String clientName = clientAccessDeniedList.get(i);

                if (i > 0) {
                    sb.append(" and ");
                }
                sb.append(clientName);
            }

        } else {
            sb.append("client ").append(clientAccessDeniedList.get(0));
        }
        throw new AccessDeniedException(sb.toString());
    }

    private List<String> analyze(Object arg, DoxUser doxUser) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        List<String> list = newArrayList();

        boolean hasClientAssigned = false;

        if (containsClient(arg)) {

            String clientName = (String) new PropertyDescriptor(CLIENT_FIELD_NAME, arg.getClass()).getReadMethod().invoke(arg);

            if (clientName == null) {
                throw new IllegalArgumentException("You need to provide a client name on class " + arg.getClass());
            }

            for (Client client : doxUser.getClients()) {
                if (client.getShortName().equals(clientName)) {
                    hasClientAssigned = true;
                    break;
                }
            }

            if (!hasClientAssigned) {
                list.add(clientName);
            }

        }
        return list;
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
