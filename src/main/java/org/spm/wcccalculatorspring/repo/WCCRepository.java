package org.spm.wcccalculatorspring.repo;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WCCRepository extends MongoRepository<WCCProject, String> {
}
