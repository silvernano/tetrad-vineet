package edu.cmu.tetrad.algcomparison.algorithms;

import edu.cmu.tetrad.algcomparison.simulation.Parameters;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.graph.Graph;

import java.util.List;

/**
 * Interface that algorithms must implement.
 * @author jdramsey
 */
public interface Algorithm {

    /**
     * Runs the search.
     * @param dataSet The data set to run to the search on.
     * @param parameters The paramters of the search.
     * @return The result graph.
     */
    Graph search(DataSet dataSet, Parameters parameters);

    /**
     * Returns that graph that the result should be compared to.
     * @param graph The true directed graph, if there is one.
     * @return The comparison graph.
     */
    Graph getComparisonGraph(Graph graph);

    /**
     * Returns a short, one-line description of this algorithm. This will be
     * printed in the report.
     * @return This description.
     */
    String getDescription();

    /**
     * Returns the data type that the search requires, whether continuous, discrete, or mixed.
     * @return This type.
     */
    DataType getDataType();

    /**
     * Returns the parameters that this search uses.
     * @return A list of String names of parameters.
     */
    List<String> getParameters();
}
