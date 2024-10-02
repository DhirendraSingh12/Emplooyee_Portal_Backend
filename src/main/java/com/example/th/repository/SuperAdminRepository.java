package com.example.th.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.th.model.SuperAdmin;

@Repository
public interface SuperAdminRepository extends MongoRepository<SuperAdmin, String> {
	Optional<SuperAdmin> findByUsername(String username);
	
   // SuperAdmin findByUsername(String username);
}
//public interface SuperAdminRepository extends MongoRepository<SuperAdmin, String> {
//	 Optional<SuperAdmin> findByUsername(String username);
//	
//}
