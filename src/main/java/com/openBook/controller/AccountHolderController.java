package com.openBook.controller;

import com.openBook.dto.AccountHolderCreationDto;
import com.openBook.dto.AccountHolderDto;
import com.openBook.service.AccountHolderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/accountHolders", "/accountHolders/"})
public class AccountHolderController {
    private static final Logger log = LoggerFactory.getLogger(AccountHolderController.class);

    private final AccountHolderService accountHolderService;

    @Autowired
    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAllAccountHolders() {
        List<AccountHolderDto> accountHolders = accountHolderService.getAllAccountHolders();
        if (accountHolders.isEmpty()) {
            log.info("No account holders exist ...");
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} account holder(s) ...", accountHolders.size());
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<AccountHolderDto> getAccountHolderById(@PathVariable Long id) {
        AccountHolderDto accountHolders = accountHolderService.getAccountHolderById(id)
                .orElse(null);
        if (accountHolders == null) {
            log.info("No account holder with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning account holder with id={} ...", id);
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/byName", "/byName/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAccountHoldersByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<AccountHolderDto> accountHolders;

        if (firstName != null && lastName != null) {
            log.info("Searching for account holder(s) named firstName={} lastName={} ...", firstName, lastName);
            accountHolders = accountHolderService.getAccountHoldersByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            log.info("Searching for account holder(s) named firstName={} ...", firstName);
            accountHolders = accountHolderService.getAccountHoldersByFirstName(firstName);
        } else if (lastName != null) {
            log.info("Searching for account holder(s) named lastName={} ...", lastName);
            accountHolders = accountHolderService.getAccountHoldersByLastName(lastName);
        } else {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        if (accountHolders.isEmpty()) {
            log.info("No matching account holder(s) exist ...");
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} account holder(s) ...", accountHolders.size());
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(value = {"/byEmail", "/byEmail/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountHolderDto>> getAccountHoldersByEmail(@RequestParam String email) {

        if (email == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<AccountHolderDto> accountHolders = accountHolderService.getAccountHoldersByEmail(email);

        if (accountHolders.isEmpty()) {
            log.info("No account holder(s) with email={} exist ...", email);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} account holder(s) ...", accountHolders.size());
        return ResponseEntity.ok(accountHolders);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createAccountHolder(@RequestBody @Valid AccountHolderCreationDto accountHolderCreationDto) {
        try {
            log.info("Attempting to create account holder with id={} ...", accountHolderCreationDto.getId());
            AccountHolderCreationDto createdAccountHolder = accountHolderService.createAccountHolder(accountHolderCreationDto);
            log.info("Account holder with with id={} successfully created ...", createdAccountHolder.getId());
            return ResponseEntity.ok(createdAccountHolder.toString());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create account holder with id={} ...", accountHolderCreationDto.getId());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.PUT)
    public ResponseEntity<String> updateAccountHolder(@PathVariable Long id, @RequestBody @Valid AccountHolderDto accountHolderDto) {
        if (accountHolderService.getAccountHolderById(id).isEmpty()) {
            log.warn("Account holder with id={} does not exist ...", id);
            return ResponseEntity.notFound().build();
        }

        try {
            AccountHolderDto updatedAccountHolder = accountHolderService.updateAccountHolder(id, accountHolderDto);
            log.info("Account holder with with id={} successfully updated ...", id);
            return ResponseEntity.ok(updatedAccountHolder.toString());
        } catch (IllegalArgumentException e) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/delete/{id}", "/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAccountHolder(@PathVariable Long id) {
        try {
            log.info("Attempting to delete account holder with id={} ...", id);
            accountHolderService.deleteAccountHolder(id);
            log.info("Successfully deleted account holder with id={} ...", id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.warn("Deletion attempt unsuccessful, no account holder with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/delete", "/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllAccountHolders() {
        log.info("Attempting to delete all account holders ...");
        accountHolderService.deleteAllAccountHolders();
        log.info("All account holders successfully deleted ...");
        return ResponseEntity.ok().build();
    }
}
