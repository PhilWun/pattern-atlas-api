package com.patternpedia.api.service;

import com.patternpedia.api.entities.designmodel.*;
import com.patternpedia.api.exception.AggregationException;
import com.patternpedia.api.exception.ConcreteSolutionNotFoundException;
import com.patternpedia.api.repositories.ConcreteSolutionRepository;
import com.patternpedia.api.repositories.DesignModelEdgeTypeRepository;
import com.patternpedia.api.rest.model.FileDTO;
import com.patternpedia.api.util.aggregator.Aggregator;
import com.patternpedia.api.util.aggregator.AggregatorScanner;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;


@Service
@CommonsLog
public class ConcreteSolutionServiceImpl implements ConcreteSolutionService {

    private final ConcreteSolutionRepository concreteSolutionRepository;
    private final DesignModelEdgeTypeRepository designModelEdgeTypeRepository;


    public ConcreteSolutionServiceImpl(ConcreteSolutionRepository concreteSolutionRepository,
                                       DesignModelEdgeTypeRepository designModelEdgeTypeRepository) {
        this.concreteSolutionRepository = concreteSolutionRepository;
        this.designModelEdgeTypeRepository = designModelEdgeTypeRepository;
    }


    public List<ConcreteSolution> getConcreteSolutions() {
        return this.concreteSolutionRepository.findAll();
    }


    public List<ConcreteSolution> getConcreteSolutions(URI patternUri) {
        return this.concreteSolutionRepository.findAllByPatternUri(patternUri.toString());
    }


    public ConcreteSolution getConcreteSolution(UUID uuid) {
        return this.concreteSolutionRepository.findTopById(uuid)
                .orElseThrow(() -> new ConcreteSolutionNotFoundException(uuid));
    }


    private void linkConcreteSolutionsToPatternInstances(List<DesignModelPatternInstance> patternInstances, Map<UUID, UUID> concreteSolutionMapping) {
        for (DesignModelPatternInstance patternInstance : patternInstances) {
            UUID piId = patternInstance.getPatternInstanceId();
            UUID csId = concreteSolutionMapping.get(piId);
            ConcreteSolution cs = this.concreteSolutionRepository.findTopById(csId)
                    .orElseThrow(() -> new ConcreteSolutionNotFoundException(csId));
            patternInstance.setConcreteSolution(cs);
        }
    }


    private void swapEdgeDirections(List<DesignModelPatternEdge> edges) {
        Set<String> edgeTypesToSwapDirections = this.designModelEdgeTypeRepository.findAll().stream()
                .filter(DesignModelEdgeType::getSwap)
                .map(DesignModelEdgeType::getName)
                .collect(Collectors.toSet());

        for (DesignModelPatternEdge edge : edges) {
            if (edge.isDirectedEdge() && edgeTypesToSwapDirections.contains(edge.getType())) {
                DesignModelPatternInstance source = edge.getPatternInstance1();
                edge.setPatternInstance1(edge.getPatternInstance2());
                edge.setPatternInstance2(source);
            }
        }
    }


    private Set<UUID> findRootNodes(List<DesignModelPatternInstance> patternInstances, List<DesignModelPatternEdge> edges) {
        Set<UUID> rootNodes = new HashSet<>();

        for (DesignModelPatternInstance patternInstance : patternInstances) {
            rootNodes.add(patternInstance.getPatternInstanceId());
        }

        for (DesignModelPatternEdge edge : edges) {
            rootNodes.remove(edge.getPatternInstance1().getPatternInstanceId());
        }

        log.info("Root nodes: " + rootNodes.toString());
        return rootNodes;
    }


    private Set<UUID> findLeafNodes(List<DesignModelPatternInstance> patternInstances, List<DesignModelPatternEdge> edges) {
        Set<UUID> leafNodes = new HashSet<>();

        for (DesignModelPatternInstance patternInstance : patternInstances) {
            leafNodes.add(patternInstance.getPatternInstanceId());
        }

        for (DesignModelPatternEdge edge : edges) {
            leafNodes.remove(edge.getPatternInstance2().getPatternInstanceId());
        }

        return leafNodes;
    }


    private UUID lastLeafUUID = null;

    private AggregationDataAndPatternEdge getLeafAndPredecessor(List<DesignModelPatternInstance> patternInstances, List<DesignModelPatternEdge> edges) {
        Set<UUID> leafNodes = findLeafNodes(patternInstances, edges);

        if (leafNodes.isEmpty()) {
            throw new RuntimeException("No leaf node found");
        }

        final UUID leafNodeUUID = leafNodes.contains(lastLeafUUID) ? lastLeafUUID : leafNodes.iterator().next();
        lastLeafUUID = leafNodeUUID;

        DesignModelPatternInstance leafPatternInstance = patternInstances.stream()
                .filter(designModelPatternInstance -> leafNodeUUID.equals(designModelPatternInstance.getPatternInstanceId()))
                .findAny().orElse(null);

        if (patternInstances.size() == 1) {
            return new AggregationDataAndPatternEdge(new AggregationData(leafPatternInstance, null), null);
        }

        DesignModelPatternEdge edge = edges.stream().filter(designModelPatternEdge -> leafNodeUUID.equals(designModelPatternEdge.getPatternInstance1().getPatternInstanceId())).findAny().orElse(null);

        DesignModelPatternInstance predecessorPatternInstance = null;

        if (edge != null) {
            UUID predecessorNodeUUID = edge.getPatternInstance2().getPatternInstanceId();

            predecessorPatternInstance = patternInstances.stream()
                    .filter(designModelPatternInstance -> predecessorNodeUUID.equals(designModelPatternInstance.getPatternInstanceId()))
                    .findAny().orElse(null);

            log.info("Found leaf [" + leafNodeUUID.toString() + "] and predecessor [" + predecessorNodeUUID.toString() + "]: " + leafPatternInstance.getPattern().getName() + " ---" + edge.getType() + "--- " + predecessorPatternInstance.getPattern().getName());
        }

        return new AggregationDataAndPatternEdge(new AggregationData(leafPatternInstance, predecessorPatternInstance), edge);
    }


    private void aggregate(AggregationData aggregationData) {

        String sourceAggregationType = aggregationData.getSource().getConcreteSolution().getAggregatorType();
        String targetAggregationType = null;
        try {
            targetAggregationType = aggregationData.getTarget().getConcreteSolution().getAggregatorType();
        } catch (NullPointerException ignored) {
        }

        Aggregator aggregator = AggregatorScanner.findMatchingAggregatorImpl(sourceAggregationType, targetAggregationType);

        if (aggregator == null) {
            throw new AggregationException("Aggregation type combination is not yet supported: [" + sourceAggregationType + "] --> [" + targetAggregationType + "]");
        }

        try {
            aggregator.aggregate(aggregationData);
        } catch (AggregationException e) {
            throw new AggregationException("Failed to aggregate concrete solutions");
        }
    }


    public List<FileDTO> aggregate(List<DesignModelPatternInstance> patternInstances, List<DesignModelPatternEdge> edges, Map<UUID, UUID> concreteSolutionMapping) {

        linkConcreteSolutionsToPatternInstances(patternInstances, concreteSolutionMapping);

        swapEdgeDirections(edges);

        Map<String, Object> templateContext = new HashMap<>();
        List<FileDTO> aggregatedFiles = new ArrayList<>();

        while (!patternInstances.isEmpty()) {
            AggregationDataAndPatternEdge aggregationDataAndPatternEdge = getLeafAndPredecessor(patternInstances, edges);
            AggregationData aggregationData = aggregationDataAndPatternEdge.getAggregationData();

            aggregationData.setTemplateContext(templateContext);

            aggregate(aggregationData);

            templateContext = aggregationData.getTemplateContext();

            if (aggregationData.getResult() != null) {
                aggregatedFiles.add(aggregationData.getResult());
            }

            edges.remove(aggregationDataAndPatternEdge.getEdge());
            if (!edges.stream().anyMatch(edge -> aggregationData.getSource().getPatternInstanceId().equals(edge.getPatternInstance1().getPatternInstanceId()))) {
                patternInstances.remove(aggregationData.getSource());
            }
        }


        return aggregatedFiles;
    }
}
