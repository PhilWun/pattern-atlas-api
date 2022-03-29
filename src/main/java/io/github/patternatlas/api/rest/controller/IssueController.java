package io.github.patternatlas.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.patternatlas.api.config.Authority;
import io.github.patternatlas.api.rest.model.issue.IssueModelRequest;
import io.github.patternatlas.api.rest.model.shared.*;
import io.github.patternatlas.api.rest.model.issue.IssueModel;
import io.github.patternatlas.api.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import liquibase.pro.packaged.P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/issues", produces = "application/hal+json")
public class IssueController {

    Logger logger = LoggerFactory.getLogger(IssueController.class);

    private IssueService issueService;
    private PatternLanguageService patternLanguageService;
    private ObjectMapper objectMapper;

    public IssueController(
            IssueService issueService,
            PatternLanguageService patternLanguageService,
            ObjectMapper objectMapper
    ) {
        this.issueService = issueService;
        this.patternLanguageService = patternLanguageService;
        this.objectMapper = objectMapper;
    }

    /**
     * GET Methods
     */
    @Operation(operationId = "getAllIssues", responses = {@ApiResponse(responseCode = "200")}, description = "Retrieve all issues")
    @GetMapping(value = "")
    // TODO: Filter for read permissions
    CollectionModel<EntityModel<IssueModel>> getAll() {
        List<EntityModel<IssueModel>> issues = this.issueService.getAllIssues()
                .stream()
                .map(issue -> new EntityModel<>(new IssueModel(issue)))
//                .sorted(Comparator.comparingInt(i -> i.getContent().getRating()))
                .sorted((i1, i2) -> Integer.compare(i2.getContent().getRating(), i1.getContent().getRating()))
                .collect(Collectors.toList());
        return CollectionModel.of(issues);
    }

    @Operation(operationId = "getIssueById", responses = {@ApiResponse(responseCode = "200")}, description = "Retrieve issue by id")
    @GetMapping(value = "/{issueId}")
    @PreAuthorize(value = Authority.ISSUE_READ_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> getIssueById(@PathVariable UUID issueId) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.getIssueById(issueId))));
    }

    @Operation(operationId = "getIssueByURI", responses = {@ApiResponse(responseCode = "200")}, description = "Retrieve issue by URI")
    @GetMapping(value = "/?uri={issueUri}")
    @PreAuthorize(value = Authority.ISSUE_READ_COMBINED_URI)
    ResponseEntity<EntityModel<IssueModel>> getIssueByUri(@PathVariable String issueUri) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.getIssueByURI(issueUri))));
    }

    /**
     * CREATE Methods
     */
    @Operation(operationId = "createIssue", responses = {@ApiResponse(responseCode = "201")}, description = "Create an issue")
    @PostMapping(value = "")
    @PreAuthorize(value = Authority.ISSUE_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<IssueModel>> newIssue(@RequestBody IssueModelRequest issueModelRequest, Principal principal) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.createIssue(issueModelRequest, UUID.fromString(principal.getName())))));
    }

    @Operation(operationId = "createIssueComment", responses = {@ApiResponse(responseCode = "201")}, description = "Create an issue comment")
    @PostMapping(value = "/{issueId}/comments")
    @PreAuthorize(value = Authority.ISSUE_COMMENT_COMBINED)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<IssueModel>> newIssueComment(@PathVariable UUID issueId, Principal principal, @RequestBody CommentModel commentModel) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.createComment(issueId, UUID.fromString(principal.getName()), commentModel))));
    }

    @Operation(operationId = "createIssueEvidence", responses = {@ApiResponse(responseCode = "201")}, description = "Create an issue evidence")
    @PostMapping(value = "/{issueId}/evidences")
    @PreAuthorize(value = Authority.ISSUE_EVIDENCE_COMBINED)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<IssueModel>> newIssueEvidence(@PathVariable UUID issueId, Principal principal, @RequestBody EvidenceModel evidenceModel) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.createEvidence(issueId, UUID.fromString(principal.getName()), evidenceModel))));
    }

    /**
     * UPDATE Methods
     */
    @Operation(operationId = "updateIssue", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue")
    @PreAuthorize(value = Authority.ISSUE_EDIT_COMBINED)
    @PutMapping(value = "/{issueId}")
    ResponseEntity<EntityModel<IssueModel>> putIssue(@PathVariable UUID issueId, Principal principal, @RequestBody IssueModelRequest issueModelRequest) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateIssue(issueId, UUID.fromString(principal.getName()), issueModelRequest))));
    }

    @Operation(operationId = "updateIssueRatings", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue ratings")
    @PutMapping(value = "/{issueId}/ratings")
    @PreAuthorize(value = Authority.ISSUE_VOTE_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> putIssueRating(@PathVariable UUID issueId, Principal principal, @RequestBody RatingModelRequest ratingModelRequest) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateIssueRating(issueId, UUID.fromString(principal.getName()), ratingModelRequest))));
    }

    @Operation(operationId = "updateIssueAuthors", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue authors")
    @PreAuthorize(value = Authority.ISSUE_EDIT_COMBINED)
    @PutMapping(value = "{issueId}/authors")
    ResponseEntity<EntityModel<IssueModel>> putIssueAuthor(@PathVariable UUID issueId, @RequestBody AuthorModelRequest authorModelRequest) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.saveIssueAuthor(issueId, authorModelRequest))));
    }

    @Operation(operationId = "updateIssue", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue comment")
    @PutMapping(value = "/{issueId}/comments/{issueCommentId}")
    @PreAuthorize(value = Authority.ISSUE_COMMENT_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> putIssueComment(@PathVariable UUID issueId, @PathVariable UUID issueCommentId, Principal principal, @RequestBody CommentModel commentModel) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateComment(issueId, issueCommentId, UUID.fromString(principal.getName()), commentModel))));
    }

    @Operation(operationId = "updateIssue", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue comment rating")
    @PutMapping(value = "/{issueId}/comments/{issueCommentId}/ratings")
    @PreAuthorize(value = Authority.ISSUE_VOTE_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> putIssueCommentRating(@PathVariable UUID issueId, @PathVariable UUID issueCommentId, Principal principal, @RequestBody RatingModelRequest ratingModelRequest) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateIssueCommentRating(issueId, issueCommentId, UUID.fromString(principal.getName()), ratingModelRequest))));
    }

    @Operation(operationId = "updateIssue", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue evidence")
    @PutMapping(value = "/{issueId}/evidences/{issueEvidenceId}")
    @PreAuthorize(value = Authority.ISSUE_EVIDENCE_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> putIssueEvidence(@PathVariable UUID issueId, @PathVariable UUID issueEvidenceId, Principal principal, @RequestBody EvidenceModel evidenceModel) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateEvidence(issueId, issueEvidenceId, UUID.fromString(principal.getName()), evidenceModel))));
    }

    @Operation(operationId = "updateIssueEvidenceRating", responses = {@ApiResponse(responseCode = "200")}, description = "Update an issue evidence rating")
    @PutMapping(value = "/{issueId}/evidences/{issueEvidenceId}/ratings")
    @PreAuthorize(value = Authority.ISSUE_VOTE_COMBINED)
    ResponseEntity<EntityModel<IssueModel>> putIssueEvidenceRating(@PathVariable UUID issueId, @PathVariable UUID issueEvidenceId, Principal principal, @RequestBody RatingModelRequest ratingModelRequest) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.updateIssueEvidenceRating(issueId, issueEvidenceId, UUID.fromString(principal.getName()), ratingModelRequest))));
    }

    /**
     * DELETE Methods
     */
    @Operation(operationId = "deleteIssue", responses = {@ApiResponse(responseCode = "200")}, description = "Delete an issue")
    @DeleteMapping(value = "/{issueId}")
    @PreAuthorize(value = Authority.ISSUE_DELETE_COMBINED)
    ResponseEntity<?> deleteIssue(@PathVariable UUID issueId) {
        this.issueService.deleteIssue(issueId);
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "deleteIssueAuthor", responses = {@ApiResponse(responseCode = "200")}, description = "Delete an issue")
    @PreAuthorize(value = Authority.ISSUE_EDIT_COMBINED)
    @DeleteMapping(value = "{issueId}/authors/{userId}")
    ResponseEntity<EntityModel<IssueModel>> deleteIssueAuthor(@PathVariable UUID issueId, @PathVariable UUID userId) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.deleteIssueAuthor(issueId, userId))));
    }

    @Operation(operationId = "deleteIssueComment", responses = {@ApiResponse(responseCode = "200")}, description = "Delete an issue comment")
    @PreAuthorize(value = Authority.ISSUE_EDIT_COMBINED)
    @DeleteMapping(value = "/{issueId}/comments/{issueCommentId}")
    ResponseEntity<EntityModel<IssueModel>> deleteComment(@PathVariable UUID issueId, @PathVariable UUID issueCommentId, Principal principal) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.deleteComment(issueId, issueCommentId, UUID.fromString(principal.getName())))));
    }

    @Operation(operationId = "deleteIssueEvidence", responses = {@ApiResponse(responseCode = "200")}, description = "Delete an issue evidence")
    @PreAuthorize(value = Authority.ISSUE_EDIT_COMBINED)
    @DeleteMapping(value = "/{issueId}/evidences/{issueEvidenceId}")
    ResponseEntity<EntityModel<IssueModel>> deleteEvidence(@PathVariable UUID issueId, @PathVariable UUID issueEvidenceId, Principal principal) {
        return ResponseEntity.ok(new EntityModel<>(new IssueModel(this.issueService.deleteEvidence(issueId, issueEvidenceId, UUID.fromString(principal.getName())))));
    }
}