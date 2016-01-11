package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.workflow.PostBoxNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestControllerSupport {


    @ExceptionHandler(PostBoxNotFoundException.class)
    public void postBoxNotFound(PostBoxNotFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(404, "Postbox not found");
        response.flushBuffer();
    }
}
