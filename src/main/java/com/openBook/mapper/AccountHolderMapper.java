package com.openBook.mapper;

import com.openBook.dto.AccountHolderDto;
import com.openBook.model.AccountHolder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountHolderMapper {

    AccountHolderMapper INSTANCE = Mappers.getMapper(AccountHolderMapper.class);

    AccountHolderDto accountHolderToAccountHolderDto(AccountHolder accountHolder);

    List<AccountHolderDto> accountHoldersToAccountHolderDtos(List<AccountHolder> accountHolders);

    AccountHolder accountHolderDtoToAccountHolder(AccountHolderDto accountHolderDto);

    List<AccountHolder> accountHolderDtosToAccountHolders(List<AccountHolderDto> accountHolderDtos);
}