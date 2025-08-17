package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo1.model.AuthDB;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AuthRepo extends JpaRepository<AuthDB,Integer>{
	
	@Query(value="select * from authdb where email=?1",nativeQuery=true)
	public AuthDB findByEmail(String email);
	@Query(value="select email from authdb where role=patient and id=?1",nativeQuery=true)
	public String getEmailByPatientId(int id);

}
