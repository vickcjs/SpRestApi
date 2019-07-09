package com.spapi;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface FriendsConnectionRepository extends CrudRepository<FriendsConnection, Integer> {

	List<FriendsConnection> findByConnectedUserA(Integer connectedUserA);

	List<FriendsConnection> findByConnectedUserB(Integer connectedUserB);

	List<FriendsConnection> findBySubscribeUserA(Integer subscribeUserA);

	List<FriendsConnection> findBySubscribeUserB(Integer subscribeUserB);

	List<FriendsConnection> findByBlockUserA(Integer blockUserA);

	List<FriendsConnection> findByBlockUserB(Integer blockUserB);

	@Query("select c from FriendsConnection c where c.connectedUserA = :connectedUserA")
	Stream<FriendsConnection> findByConnectedUserAReturnStream(@Param("connectedUserA") Integer connectedUserA);

	@Query("select c from FriendsConnection c where c.connectedUserB = :connectedUserB")
	Stream<FriendsConnection> findByConnectedUserBReturnStream(@Param("connectedUserB") Integer connectedUserB);

	@Query("select c from FriendsConnection c where c.subscribeUserA = :subscribeUserA")
	Stream<FriendsConnection> findBySubscribeUserAReturnStream(@Param("subscribeUserA") Integer subscribeUserA);

	@Query("select c from FriendsConnection c where c.subscribeUserB = :subscribeUserB")
	Stream<FriendsConnection> findBySubscribeUserBReturnStream(@Param("subscribeUserB") Integer subscribeUserB);

	@Query("select c from FriendsConnection c where c.blockUserA = :blockUserA")
	Stream<FriendsConnection> findByBlockUserAReturnStream(@Param("blockUserA") Integer blockUserA);

	@Query("select c from FriendsConnection c where c.blockUserB = :blockUserB")
	Stream<FriendsConnection> findByBlockUserBReturnStream(@Param("blockUserB") Integer blockUserB);
}
