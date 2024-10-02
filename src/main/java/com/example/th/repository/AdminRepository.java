package com.example.th.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.th.model.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
	Optional<Admin> findByUsername(String username);
}


//public interface AdminRepository extends MongoRepository<Admin, String> {
//	 Optional<Admin> findByUsername(String username);
//}