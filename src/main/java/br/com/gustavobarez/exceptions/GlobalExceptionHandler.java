package br.com.gustavobarez.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.annotation.Priority;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

public class GlobalExceptionHandler {
    @Provider
    @Priority(Priorities.USER - 1)
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException exception) {
            List<ErrorMessageDTO> errors = mapConstraintViolations(exception.getConstraintViolations());

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }

        private List<ErrorMessageDTO> mapConstraintViolations(Set<ConstraintViolation<?>> violations) {
            return violations.stream()
                    .map(violation -> {
                        String propertyPath = violation.getPropertyPath().toString();
                        String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                        String message = violation.getMessage();
                        String formattedMessage = String.format("param: %s (type: body) - %s", fieldName, message);
                        return new ErrorMessageDTO("400", formattedMessage);
                    })
                    .collect(Collectors.toList());
        }
    }

    @Provider
    @Priority(Priorities.USER - 2)
    public static class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

        @Override
        public Response toResponse(JsonProcessingException exception) {
            List<ErrorMessageDTO> errors = new ArrayList<>();
            errors.add(new ErrorMessageDTO("400", "Invalid JSON format"));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

        @Override
        public Response toResponse(EntityNotFoundException exception) {
            List<ErrorMessageDTO> errors = new ArrayList<>();
            errors.add(new ErrorMessageDTO("404", exception.getMessage()));

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

        @Override
        public Response toResponse(NotFoundException exception) {
            List<ErrorMessageDTO> errors = new ArrayList<>();
            String message = exception.getMessage() != null ? exception.getMessage() : "Resource not found";
            errors.add(new ErrorMessageDTO("404", message));

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

        @Override
        public Response toResponse(BadRequestException exception) {
            List<ErrorMessageDTO> errors = new ArrayList<>();
            String message = exception.getMessage() != null ? exception.getMessage() : "Bad request";
            errors.add(new ErrorMessageDTO("400", message));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER)
    public static class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

        @Override
        public Response toResponse(IllegalArgumentException exception) {
            List<ErrorMessageDTO> errors = new ArrayList<>();
            errors.add(new ErrorMessageDTO("400", exception.getMessage()));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER + 100)
    public static class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

        @Override
        public Response toResponse(WebApplicationException exception) {
            int statusCode = exception.getResponse().getStatus();
            String statusMessage = Response.Status.fromStatusCode(statusCode).getReasonPhrase();

            List<ErrorMessageDTO> errors = new ArrayList<>();
            String message = exception.getMessage();
            if (message == null || message.isEmpty()) {
                message = statusMessage;
            }
            errors.add(new ErrorMessageDTO(String.valueOf(statusCode), message));

            return Response.status(statusCode)
                    .entity(errors)
                    .build();
        }
    }

    @Provider
    @Priority(Priorities.USER + 200)
    public static class GenericExceptionMapper implements ExceptionMapper<Throwable> {

        @Override
        public Response toResponse(Throwable exception) {
            exception.printStackTrace();

            List<ErrorMessageDTO> errors = new ArrayList<>();
            errors.add(new ErrorMessageDTO("500", "An internal server error occurred"));

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errors)
                    .build();
        }
    }
}