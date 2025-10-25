package com.ucb.modulo9.userservice.services;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!¡¿?.*(){}\\[\\]|\\\\/'\"<>~`_\\-]).{8,}$");

    /**
     * Comprueba si un String no es nulo ni está vacío (después de quitar espacios).
     * @return true si es válido, false si no.
     */
    public boolean isStringNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Comprueba si la longitud de un String está dentro de un rango (inclusivo).
     * @return true si es válido, false si no.
     */
    public boolean isStringLengthBetween(String value, int min, int max) {
        return value != null && value.length() >= min && value.length() <= max;
    }

    /**
     * Comprueba si un String tiene un formato de email válido.
     * @return true si es válido, false si no.
     */
    public boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Comprueba si una contraseña cumple con los criterios de seguridad.
     * (min 8 chars, 1 mayúscula, 1 minúscula, 1 número, 1 caracter especial)
     * @return true si es válida, false si no.
     */
    public boolean isPasswordStrong(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
