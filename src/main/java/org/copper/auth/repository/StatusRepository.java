package org.copper.auth.repository;

import org.copper.auth.common.StatusCode;
import org.copper.auth.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
  Status findByCode(StatusCode statusCode);
}