package edu.tcu.cs.superfrogscheduler.system.exception;

import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {


    /**
     * This handles already existed object when add new object.
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(ObjectAlreadyExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Result handleObjectAlreadyExistedException(Exception ex) {
        return new Result(false, StatusCode.CONFLICT, ex.getMessage());
    }


    /**
     * This handles object does not exist.
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleObjectNotFoundException(Exception ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }


    /**
     * This handles invalid inputs.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided arguments are invalid, see data for details.", map);
    }

    /**
     * This handles missing request body.
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return new Result(false, StatusCode.INVALID_ARGUMENT, "Request body is missing");
    }




}
