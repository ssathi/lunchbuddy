package sg.gov.lunchbuddy.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sg.gov.lunchbuddy.exception.InvalidOperationException;
import sg.gov.lunchbuddy.exception.NotFoundException;
import sg.gov.lunchbuddy.exception.UnauthorizedException;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidOperationException.class)
    public ProblemDetail handleInvalid(Exception e) {
        return forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(Exception e) {
        return forStatusAndDetail(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleUnauthorized(Exception e) {
        return forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
    }
}
