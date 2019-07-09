package com.spapi;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface FriendRepository extends CrudRepository<Friend, Integer> {

	List<Friend> findByEmail(String email);

	@Query("select c from Friend c where c.email = :email")
	Stream<Friend> findByEmailReturnStream(@Param("email") String email);

}
