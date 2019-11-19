package com.patternpedia.api.service;

import com.patternpedia.api.entities.Pattern;
import com.patternpedia.api.exception.NullPatternException;
import com.patternpedia.api.exception.NullPatternLanguageException;
import com.patternpedia.api.exception.PatternNotFoundException;
import com.patternpedia.api.repositories.PatternRepository;
import com.patternpedia.api.validator.PatternContentConstraint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
@Transactional
public class PatternServiceImpl implements PatternService {

    private PatternRepository patternRepository;

    public PatternServiceImpl(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    @Override
    @Transactional
    public Pattern createPattern(@Valid @PatternContentConstraint Pattern pattern) {
        if (null == pattern) {
            throw new NullPatternException();
        }
        if (null == pattern.getPatternLanguage()) {
            throw new NullPatternLanguageException();
        }

        return this.patternRepository.save(pattern);
    }

    @Override
    @Transactional
    public Pattern updatePattern(@Valid @PatternContentConstraint Pattern pattern) {
        if (null == pattern) {
            throw new NullPatternException();
        }
        if (null == pattern.getPatternLanguage()) {
            throw new NullPatternLanguageException();
        }

        return this.patternRepository.save(pattern);
    }

    @Override
    @Transactional
    public void deletePattern(Pattern pattern) {
        if (null == pattern) {
            throw new NullPatternException();
        }

        this.patternRepository.deleteById(pattern.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Pattern getPatternById(UUID patternId) {
        return this.patternRepository.findById(patternId)
                .orElseThrow(() -> new PatternNotFoundException(String.format("Pattern %s not found!", patternId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Pattern getPatternByUri(String uri) {
        return this.patternRepository.findByUri(uri)
                .orElseThrow(() -> new PatternNotFoundException(String.format("Pattern with URI %s not found!", uri)));
    }
}
