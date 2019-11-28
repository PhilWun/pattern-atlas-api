package com.patternpedia.api.rest.model;

import java.util.UUID;

import com.patternpedia.api.entities.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PatternModel {

    private UUID id;

    private String uri;

    private String name;

    @JsonIgnore
    private Pattern pattern;

    private PatternModel(Pattern pattern) {
        this.pattern = pattern;
        this.id = pattern.getId();
        this.uri = pattern.getUri();
        this.name = pattern.getName();
    }

    public static PatternModel from(Pattern pattern) {
        return new PatternModel(pattern);
    }
}