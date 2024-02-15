package com.openBook.service.implementation;

import com.openBook.dto.AccountHolderDto;
import com.openBook.mapper.AccountHolderMapper;
import com.openBook.model.AccountHolder;
import com.openBook.repository.AccountHolderRepository;
import com.openBook.service.AccountHolderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class AccountHolderServiceImpl implements AccountHolderService {

    private final AccountHolderRepository accountHolderRepository;
    private final AccountHolderMapper accountHolderMapper;

    @Autowired
    public AccountHolderServiceImpl(AccountHolderRepository accountHolderRepository, AccountHolderMapper accountHolderMapper) {
        this.accountHolderRepository = accountHolderRepository;
        this.accountHolderMapper = accountHolderMapper;
    }

    @Override
    public List<AccountHolderDto> getAllAccountHolders() {
        return accountHolderRepository.findAll()
                .stream()
                .map(accountHolderMapper::accountHolderToAccountHolderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountHolderDto> getAccountHolderById(Long id) {
        return accountHolderRepository.findById(id).map(accountHolderMapper::accountHolderToAccountHolderDto);
    }

    @Override
    public List<AccountHolderDto> getAccountHoldersByFirstName(String firstName) {
        return accountHolderRepository.findByFirstName(firstName).stream()
                .map(accountHolderMapper::accountHolderToAccountHolderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHolderDto> getAccountHoldersByLastName(String lastName) {
        return accountHolderRepository.findByLastName(lastName).stream()
                .map(accountHolderMapper::accountHolderToAccountHolderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHolderDto> getAccountHoldersByFirstNameAndLastName(String firstName, String lastName) {
        return accountHolderRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(accountHolderMapper::accountHolderToAccountHolderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHolderDto> getAccountHoldersByEmail(String email) {
        return accountHolderRepository.findByEmail(email).stream()
                .map(accountHolderMapper::accountHolderToAccountHolderDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountHolderDto createAccountHolder(@Valid AccountHolderDto accountHolderDto) {
        return accountHolderMapper.accountHolderToAccountHolderDto(
                accountHolderRepository.save(accountHolderMapper.accountHolderDtoToAccountHolder(accountHolderDto)));
    }

    @Override
    public AccountHolderDto updateAccountHolder(Long id, @Valid AccountHolderDto accountHolderDto) throws RuntimeException {
        AccountHolder existingAccountHolder = accountHolderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Holder not found with id: " + id));

        existingAccountHolder.setFirstName(accountHolderDto.getFirstName());
        existingAccountHolder.setLastName(accountHolderDto.getLastName());
        existingAccountHolder.setEmail(accountHolderDto.getEmail());

        AccountHolder updatedAccountHolder = accountHolderRepository.save(existingAccountHolder);

        return accountHolderMapper.accountHolderToAccountHolderDto(updatedAccountHolder);
    }

    @Override
    public void deleteAccountHolder(Long id) throws EntityNotFoundException {
        if (!accountHolderRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Account Holder with id: %s not found...", id));
        }
        accountHolderRepository.deleteById(id);
    }

    @Override
    public void deleteAllAccountHolders() {
        accountHolderRepository.deleteAll();
    }
}
