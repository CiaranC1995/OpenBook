package com.openBook.config;

import com.openBook.mapper.AccountHolderMapper;
import com.openBook.mapper.AuthorMapper;
import com.openBook.mapper.BMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public AuthorMapper authorMapper() {
        return Mappers.getMapper(AuthorMapper.class);
    }

    @Bean
    public BMapper bookMapper() { return BMapper.INSTANCE; }

    @Bean
    public AccountHolderMapper accountHolderMapper() { return AccountHolderMapper.INSTANCE; }
}
