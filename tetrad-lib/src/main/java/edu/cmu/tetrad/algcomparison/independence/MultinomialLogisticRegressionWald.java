package edu.cmu.tetrad.algcomparison.independence;

import edu.cmu.tetrad.algcomparison.simulation.Parameters;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.data.DataType;
import edu.pitt.csb.mgm.IndTestMultinomialLogisticRegressionWald;

import java.util.Collections;
import java.util.List;

/**
 * Wrapper for Fisher Z test.
 * @author jdramsey
 */
public class MultinomialLogisticRegressionWald implements IndTestWrapper {
    private DataSet dataSet = null;
    private edu.cmu.tetrad.search.IndependenceTest test = null;

    @Override
    public edu.cmu.tetrad.search.IndependenceTest getTest(DataSet dataSet, Parameters parameters) {
        if (dataSet != this.dataSet) {
            this.dataSet = dataSet;
            this.test = new IndTestMultinomialLogisticRegressionWald(
                    dataSet,
                    parameters.getDouble("alpha"),
                    false);
        }
        return test;
    }

    @Override
    public String getDescription() {
        return "MultinomialLogisticRetressionWald test";
    }

    @Override
    public DataType getDataType() {
        return DataType.Continuous;
    }

    @Override
    public List<String> getParameters() {
        return Collections.singletonList("alpha");
    }

}
