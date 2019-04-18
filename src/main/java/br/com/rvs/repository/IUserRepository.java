package br.com.rvs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.rvs.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {
	 List<User> findByLogin(String login);
}
