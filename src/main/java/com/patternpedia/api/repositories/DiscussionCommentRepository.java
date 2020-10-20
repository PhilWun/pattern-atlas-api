package com.patternpedia.api.repositories;

import com.patternpedia.api.entities.DiscussionComment;
import com.patternpedia.api.entities.DiscussionTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface DiscussionCommentRepository extends CrudRepository<DiscussionComment, UUID> {
    List<DiscussionComment> findDiscussionCommentByDiscussionTopic(DiscussionTopic discussionTopic);
}
