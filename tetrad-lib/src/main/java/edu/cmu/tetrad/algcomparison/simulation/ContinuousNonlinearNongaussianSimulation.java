package edu.cmu.tetrad.algcomparison.simulation;

import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.data.*;
import edu.cmu.tetrad.graph.*;
import edu.cmu.tetrad.sem.*;

import java.text.ParseException;
import java.util.*;

/**
 * @author jdramsey
 */
public class ContinuousNonlinearNongaussianSimulation implements Simulation {
    private Graph graph;
    private List<DataSet> dataSets;

    @Override
    public void simulate(Parameters parameters) {
        this.dataSets = new ArrayList<>();
        this.graph = GraphUtils.randomGraphRandomForwardEdges(
                parameters.getInt("numMeasures"),
                parameters.getInt("numLatents"),
                parameters.getInt("numEdges"),
                parameters.getInt("maxDegree"),
                parameters.getInt("maxIndegree"),
                parameters.getInt("maxOutdegree"),
                parameters.getInt("connected") == 1);

        for (int i = 0; i < parameters.getInt("numRuns"); i++) {
            DataSet dataSet = simulate(graph, parameters);
            System.out.println(graph);
            System.out.println(dataSet);
            this.dataSets.add(dataSet);
        }
    }

    private DataSet simulate(Graph graph, Parameters parameters) {
        GeneralizedSemPm pm = getPm(graph);
        System.out.println("PM " + pm);
        GeneralizedSemIm im = new GeneralizedSemIm(pm);
        System.out.println("IM " + im);
        return im.simulateData(parameters.getInt("sampleSize"), false);
    }

    @Override
    public Graph getTrueGraph() {
        return graph;
    }

    @Override
    public int getNumDataSets() {
        return dataSets.size();
    }

    @Override
    public DataSet getDataSet(int index) {
        return dataSets.get(index);
    }

    @Override
    public DataType getDataType() {
        return DataType.Continuous;
    }

    public String getDescription() {
        return "Nonlinear, non-Gaussian SEM simulation";
    }

    @Override
    public List<String> getParameters() {
        List<String> parameters = new ArrayList<>();
        parameters.add("numMeasures");
        parameters.add("numLatents");
        parameters.add("numEdges");
        parameters.add("maxDegree");
        parameters.add("maxIndegree");
        parameters.add("maxOutdegree");
        parameters.add("numRuns");
        parameters.add("sampleSize");
        return parameters;
    }

    private GeneralizedSemPm getPm(Graph graph) {

        GeneralizedSemPm pm = new GeneralizedSemPm(graph);

        List<Node> variablesNodes = pm.getVariableNodes();
        List<Node> errorNodes = pm.getErrorNodes();

        Map<String, String> paramMap = new HashMap<String, String>();
        String[] funcs = {"TSUM(NEW(B)*$)","TSUM(NEW(B)*$^2)", "TSUM(NEW(B)*$+NEW(C)*sin(NEW(T)*$+NEW(A)))",
                "TSUM(NEW(B)*(.5*$ + .5*(sqrt(abs(NEW(b)*$+NEW(exoErrorType))) ) ) )"};
        paramMap.put("s", "U(1,3)");
        paramMap.put("B", "Split(-1.5,-.5,.5,1.5)");
        paramMap.put("C", "Split(-1.5,-.5,.5,1.5)");
        paramMap.put("T", "U(.5,1.5)");
        paramMap.put("A", "U(0,.25)");
        paramMap.put("exoErrorType", "U(-.5,.5)");
        paramMap.put("funcType", "U(1,5)");
Random rand = new Random();
        String nonlinearStructuralEdgesFunction = funcs[1];
        String nonlinearFactorMeasureEdgesFunction = funcs[1];

        try {
            for (Node node : variablesNodes) {
                if (node.getNodeType() == NodeType.LATENT) {
                    String _template = TemplateExpander.getInstance().expandTemplate(
                            nonlinearStructuralEdgesFunction, pm, node);
                    pm.setNodeExpression(node, _template);
                }
                else {
                    /*if(rand.nextDouble() > 0.5)
                        nonlinearFactorMeasureEdgesFunction = funcs[0];
                    else*/
                        nonlinearFactorMeasureEdgesFunction = funcs[1];
                    String _template = TemplateExpander.getInstance().expandTemplate(
                            nonlinearFactorMeasureEdgesFunction, pm, node);
                    pm.setNodeExpression(node, _template);
                }
            }

            for (Node node : errorNodes){
                String _template = TemplateExpander.getInstance().expandTemplate("U(-.5,.5)", pm, node);
                pm.setNodeExpression(node, _template);
            }

            Set<String> parameters = pm.getParameters();

            for (String parameter : parameters) {
                for (String type : paramMap.keySet()) {
                    if (parameter.startsWith(type)) {
                        pm.setParameterExpression(parameter, paramMap.get(type));
                    }
                }
            }
        } catch (ParseException e) {
            System.out.println(e);
        }

        return pm;
    }
}
