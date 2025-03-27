package com.innovatio_software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innovatio_software.model.IdReaderModel;

@Repository
public interface IdReaderRepository extends JpaRepository<IdReaderModel, Long>{
    
}
