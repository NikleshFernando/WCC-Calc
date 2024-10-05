package org.spm.wcccalculatorspring.repo;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WCCRepository extends MongoRepository<WCCProject, String> {
    Optional<WCCProject> findByProjectKey(String projectKey);
}
