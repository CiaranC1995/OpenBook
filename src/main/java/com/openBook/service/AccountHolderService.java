package com.openBook.service;

import com.openBook.dto.AccountHolderDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public interface AccountHolderService {

    List<AccountHolderDto> getAllAccountHolders();

    Optional<AccountHolderDto> getAccountHolderById(Long id);

    List<AccountHolderDto> getAccountHoldersByFirstName(String firstName);

    List<AccountHolderDto> getAccountHoldersByLastName(String lastName);

    List<AccountHolderDto> getAccountHoldersByFirstNameAndLastName(String firstName, String lastName);

    List<AccountHolderDto> getAccountHoldersByEmail(String email);

    AccountHolderDto createAccountHolder(@Valid AccountHolderDto accountHolderDto);

    AccountHolderDto updateAccountHolder(Long id, @Valid AccountHolderDto accountHolderDto);

    void deleteAccountHolder(Long id);

    void deleteAllAccountHolders();
}