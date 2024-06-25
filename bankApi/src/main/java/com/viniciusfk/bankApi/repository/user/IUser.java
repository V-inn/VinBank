package com.viniciusfk.bankApi.repository.user;

import com.viniciusfk.bankApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser extends JpaRepository<User, Integer> {
    User findByNameOrCpf(String name, String cpf);
    User findByName(String name);
}