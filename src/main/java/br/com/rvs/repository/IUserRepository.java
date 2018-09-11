package br.com.rvs.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.rvs.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {

}
