package edu.cmu.tetrad.algcomparison.simulation;

import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.data.DataReader;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.GraphUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jdramsey
 */
public class LoadContinuousDataAndGraphs implements Simulation {
    private String path;
    private Graph graph;
    private List<DataSet> dataSets;

    public LoadContinuousDataAndGraphs(String path) {
        this.path = path;
    }

    @Override
    public void simulate(Parameters parameters) {
        this.dataSets = new ArrayList<>();

        if (new File(path + "/data").exists()) {
            int numDataSets = new File(path + "/data").listFiles().length;

            File file2 = new File(path + "/graph/graph.txt");
            System.out.println("Loading graph from " + file2.getAbsolutePath());
            this.graph = GraphUtils.loadGraphTxt(file2);

            try {
                for (int i = 0; i < numDataSets; i++) {
                    File file1 = new File(path + "/data/data." + (i + 1) + ".txt");

                    System.out.println("Loading data from " + file1.getAbsolutePath());
                    DataReader reader = new DataReader();
                    reader.setVariablesSupplied(true);
                    dataSets.add(reader.parseTabular(file1));
                }

                File file = new File(path, "parameters.txt");
                BufferedReader r = new BufferedReader(new FileReader(file));

                String line;

                while ((line = r.readLine()) != null) {
                    if (line.contains(" = ")) {
                        String[] tokens = line.split(" = ");
                        String key = tokens[0];
                        String value = tokens[1];
                        parameters.put(key, Double.parseDouble(value));
                    }
                }

                parameters.put("numRuns", numDataSets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Graph getTrueGraph() {
        return graph;
    }

    @Override
    public DataSet getDataSet(int index) {
        return dataSets.get(index);
    }

    @Override
    public String getDescription() {
        try {
            File file = new File(path, "parameters.txt");
            BufferedReader r = new BufferedReader(new FileReader(file));

            StringBuilder b = new StringBuilder();
            b.append("Load data sets and graphs from a directory.").append("\n\n");
            String line;

            while ((line = r.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                b.append(line).append("\n");
            }

            return b.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getParameters() {
        List<String> parameters = new ArrayList<>();
        return parameters;
    }

    @Override
    public int getNumDataSets() {
        return dataSets.size();
    }

    @Override
    public DataType getDataType() {
        return DataType.Continuous;
    }
}
