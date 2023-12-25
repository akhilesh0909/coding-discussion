package com.ems.codingdiscussion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findFirstByEmail(String email);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE user SET is_admin=true,is_locked=false WHERE id=:userId")
	void makeAdmin(@Param("userId") Long userId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE user SET is_locked=:isLocked WHERE id=:userId")
	void toggleUserAccess(@Param("userId") Long userId,@Param("isLocked") boolean isLocked);

	List<User> findAllByRole(String role);

    Optional<User> findByName(String userName);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE user SET name=:userName WHERE id=:userId")
	void updateUserName(Long userId, String userName);
}
