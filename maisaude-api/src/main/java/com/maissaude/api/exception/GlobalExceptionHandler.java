package com.maissaude.api.exception;

import com.maissaude.api.dto.ErroRespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de @Valid no corpo da requisicao (validacao da camada de dados/DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> handleValidacaoBody(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(new ErroRespostaDTO(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro de validacao", mensagens));
    }

    // Erros de validacao em @PathVariable / @RequestParam (@Validated na classe do controller)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroRespostaDTO> handleValidacaoParametros(ConstraintViolationException ex) {
        List<String> mensagens = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();
        return ResponseEntity.badRequest().body(new ErroRespostaDTO(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro de validacao", mensagens));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroRespostaDTO(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Nao encontrado", List.of(ex.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroRespostaDTO> handleCredenciais(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErroRespostaDTO(
                LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), "Nao autorizado", List.of("Usuario ou senha invalidos")));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroRespostaDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErroRespostaDTO(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Requisicao invalida", List.of(ex.getMessage())));
    }
}
