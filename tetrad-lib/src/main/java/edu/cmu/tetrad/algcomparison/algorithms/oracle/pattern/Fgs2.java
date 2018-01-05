package edu.cmu.tetrad.algcomparison.algorithms.oracle.pattern;

import edu.cmu.tetrad.algcomparison.algorithms.Algorithm;
import edu.cmu.tetrad.algcomparison.simulation.Parameters;
import edu.cmu.tetrad.algcomparison.score.ScoreWrapper;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.search.SearchGraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * FGS (the heuristic version).
 * @author jdramsey
 */
public class Fgs2 implements Algorithm {
    private ScoreWrapper score;
    private Algorithm initialGraph = null;

    public Fgs2(ScoreWrapper score) {
        this.score = score;
    }

    public Fgs2(ScoreWrapper score, Algorithm initialGraph) {
        this.score = score;
        this.initialGraph = initialGraph;
    }

    @Override
    public Graph search(DataSet dataSet, Parameters parameters) {
        Graph initial = null;

        if (initialGraph != null) {
            initial = initialGraph.search(dataSet, parameters);
        }

        edu.cmu.tetrad.search.Fgs2 fgs = new edu.cmu.tetrad.search.Fgs2(score.getScore(dataSet, parameters));

        if (initial != null) {
            fgs.setInitialGraph(initial);
        }

        return fgs.search();
    }

    @Override
    public Graph getComparisonGraph(Graph graph) {
        return SearchGraphUtils.patternForDag(graph);
    }

    @Override
    public String getDescription() {
        return "FGS using " + score.getDescription();
    }

    @Override
    public DataType getDataType() {
        return DataType.Continuous;
    }

    @Override
    public List<String> getParameters() {
        List<String> parameters = new ArrayList<>();
        parameters.add("penaltyDiscount");
        parameters.add("fgsDepth");
        return parameters;
    }}
