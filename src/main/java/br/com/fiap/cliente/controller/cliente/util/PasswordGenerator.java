package br.com.fiap.cliente.controller.cliente.util;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final int LENGTH = 12; // ajuste conforme sua política
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateTemporaryPassword() {
        StringBuilder sb = new StringBuilder(LENGTH);

        // Garantir pelo menos um de cada categoria
        sb.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        sb.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        sb.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

        // Preencher o restante aleatoriamente
        for (int i = 4; i < LENGTH; i++) {
            sb.append(ALL.charAt(RANDOM.nextInt(ALL.length())));
        }

        // Embaralhar para não ficar previsível
        return shuffleString(sb.toString());
    }

    private static String shuffleString(String input) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        return new String(a);
    }
}
