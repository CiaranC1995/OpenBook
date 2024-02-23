package com.openBook.controller;

import com.openBook.dto.AccountHolderCreationDto;
import com.openBook.dto.AccountHolderDto;
import com.openBook.service.AccountHolderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/accountHolders", "/accountHolders/"})
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    @Autowired
    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAllAccountHolders() {
        List<AccountHolderDto> accountHolders = accountHolderService.getAllAccountHolders();
        if (accountHolders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<AccountHolderDto> getAccountHolderById(@PathVariable Long id) {
        AccountHolderDto accountHolders = accountHolderService.getAccountHolderById(id)
                .orElse(null);
        if (accountHolders == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/byName", "/byName/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAccountHoldersByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<AccountHolderDto> accountHolders;

        if (firstName != null && lastName != null) {
            accountHolders = accountHolderService.getAccountHoldersByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            accountHolders = accountHolderService.getAccountHoldersByFirstName(firstName);
        } else if (lastName != null) {
            accountHolders = accountHolderService.getAccountHoldersByLastName(lastName);
        } else {
            return ResponseEntity.badRequest().build();
        }
        if (accountHolders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/byEmail", "/byEmail/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAccountHoldersByEmail(@RequestParam String email) {

        if (email == null) {
            return ResponseEntity.badRequest().build();
        }
        List<AccountHolderDto> accountHolders = accountHolderService.getAccountHoldersByEmail(email);

        if (accountHolders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createAccountHolder(@RequestBody @Valid AccountHolderCreationDto accountHolderCreationDto) {
        try {
            AccountHolderCreationDto createdAccountHolder = accountHolderService.createAccountHolder(accountHolderCreationDto);
            return ResponseEntity.ok(String.format("Account holder with id=%s successfully created...", createdAccountHolder.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.PUT)
    public ResponseEntity<String> updateAccountHolder(@PathVariable Long id, @RequestBody @Valid AccountHolderDto accountHolderDto) {
        if (accountHolderService.getAccountHolderById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            AccountHolderDto updatedAccountHolder = accountHolderService.updateAccountHolder(id, accountHolderDto);
            return ResponseEntity.ok(String.format("Account holder with id=%s successfully updated...", updatedAccountHolder.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/delete/{id}", "/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAccountHolder(@PathVariable Long id) {
        try {
            accountHolderService.deleteAccountHolder(id);
            return ResponseEntity.ok(String.format("Account holder with id=%s successfully deleted...", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/delete", "/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllAccountHolders() {
        accountHolderService.deleteAllAccountHolders();
        return ResponseEntity.ok().body("All account holders successfully deleted...");
    }
}
