package com.openBook.repository;

import com.openBook.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    List<AccountHolder> findByFirstName(String firstName);

    List<AccountHolder> findByLastName(String lastName);

    List<AccountHolder> findByFirstNameAndLastName(String firstName, String lastName);

    List<AccountHolder> findByEmail(String email);
}
