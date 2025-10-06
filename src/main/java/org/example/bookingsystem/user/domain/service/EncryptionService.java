package org.example.bookingsystem.user.domain.service;

public interface EncryptionService {

    String encryptPassword(String password);

    boolean matchPassword(String password, String encryptedPassword);
}
