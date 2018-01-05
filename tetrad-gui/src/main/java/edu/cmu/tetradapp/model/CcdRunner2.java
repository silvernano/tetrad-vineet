///////////////////////////////////////////////////////////////////////////////
// For information as to what this class does, see the Javadoc, below.       //
// Copyright (C) 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006,       //
// 2007, 2008, 2009, 2010, 2014, 2015 by Peter Spirtes, Richard Scheines, Joseph   //
// Ramsey, and Clark Glymour.                                                //
//                                                                           //
// This program is free software; you can redistribute it and/or modify      //
// it under the terms of the GNU General Public License as published by      //
// the Free Software Foundation; either version 2 of the License, or         //
// (at your option) any later version.                                       //
//                                                                           //
// This program is distributed in the hope that it will be useful,           //
// but WITHOUT ANY WARRANTY; without even the implied warranty of            //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             //
// GNU General Public License for more details.                              //
//                                                                           //
// You should have received a copy of the GNU General Public License         //
// along with this program; if not, write to the Free Software               //
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA //
///////////////////////////////////////////////////////////////////////////////

package edu.cmu.tetradapp.model;

import edu.cmu.tetrad.data.*;
import edu.cmu.tetrad.graph.*;
import edu.cmu.tetrad.search.*;
import edu.cmu.tetrad.util.TetradSerializableUtils;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends AbstractAlgorithmRunner to produce a wrapper for the FCI algorithm.
 *
 * @author Joseph Ramsey
 */
public class CcdRunner2 extends AbstractAlgorithmRunner
        implements IndTestProducer, GraphSource {
    static final long serialVersionUID = 23L;

    private transient List<PropertyChangeListener> listeners;
    private transient Ccd2 ccd;


    //=========================CONSTRUCTORS================================//

    /**
     * Constructs a wrapper for the given DataWrapper. The DataWrapper must
     * contain a DataSet that is either a DataSet or a DataSet or a DataList
     * containing either a DataSet or a DataSet as its selected model.
     */
    public CcdRunner2(DataWrapper dataWrapper, BasicSearchParams params, KnowledgeBoxModel knowledgeBoxModel) {
        super(dataWrapper, params, knowledgeBoxModel);
    }

    public CcdRunner2(Graph graph, BasicSearchParams params) {
        super(graph, params);
    }


    public CcdRunner2(GraphWrapper graphWrapper, BasicSearchParams params) {
        super(graphWrapper.getGraph(), params);
    }

    public CcdRunner2(DagWrapper dagWrapper, BasicSearchParams params) {
        super(dagWrapper.getDag(), params);
    }

    public CcdRunner2(SemGraphWrapper dagWrapper, BasicSearchParams params) {
        super(dagWrapper.getGraph(), params);
    }

    public CcdRunner2(IndependenceFactsModel model, BasicSearchParams params) {
        super(model, params, null);
    }

    public CcdRunner2(IndependenceFactsModel model, BasicSearchParams params, KnowledgeBoxModel knowledgeBoxModel) {
        super(model, params, knowledgeBoxModel);
    }

    public CcdRunner2(DataWrapper dataWrapper, BasicSearchParams params) {
        super(new MergeDatasetsWrapper(dataWrapper), params, null);
    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      DataWrapper dataWrapper6,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5,
                        dataWrapper6
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      DataWrapper dataWrapper6,
                      DataWrapper dataWrapper7,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5,
                        dataWrapper6,
                        dataWrapper7
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      DataWrapper dataWrapper6,
                      DataWrapper dataWrapper7,
                      DataWrapper dataWrapper8,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5,
                        dataWrapper6,
                        dataWrapper7,
                        dataWrapper8
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      DataWrapper dataWrapper6,
                      DataWrapper dataWrapper7,
                      DataWrapper dataWrapper8,
                      DataWrapper dataWrapper9,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5,
                        dataWrapper6,
                        dataWrapper7,
                        dataWrapper8,
                        dataWrapper9
                ),
                params, null);

    }

    public CcdRunner2(DataWrapper dataWrapper1,
                      DataWrapper dataWrapper2,
                      DataWrapper dataWrapper3,
                      DataWrapper dataWrapper4,
                      DataWrapper dataWrapper5,
                      DataWrapper dataWrapper6,
                      DataWrapper dataWrapper7,
                      DataWrapper dataWrapper8,
                      DataWrapper dataWrapper9,
                      DataWrapper dataWrapper10,
                      BasicSearchParams params) {

        super(new MergeDatasetsWrapper(
                        dataWrapper1,
                        dataWrapper2,
                        dataWrapper3,
                        dataWrapper4,
                        dataWrapper5,
                        dataWrapper6,
                        dataWrapper7,
                        dataWrapper8,
                        dataWrapper9,
                        dataWrapper10
                ),
                params, null);

    }

    public CcdRunner2(GraphWrapper graphWrapper, BasicSearchParams params, KnowledgeBoxModel knowledgeBoxModel) {
        super(graphWrapper.getGraph(), params, knowledgeBoxModel);
    }

//    public CcdRunner(GraphWrapper graphWrapper, BasicSearchParams params) {
//        super(graphWrapper.getGraph(), params, null);
//    }


    /**
     * Generates a simple exemplar of this class to test serialization.
     *
     * @see TetradSerializableUtils
     */
    public static CcdRunner2 serializableInstance() {
        return new CcdRunner2(Dag.serializableInstance(),
                BasicSearchParams.serializableInstance().serializableInstance());
    }

    //=================PUBLIC METHODS OVERRIDING ABSTRACT=================//

    /**
     * Executes the algorithm, producing (at least) a result workbench. Must be
     * implemented in the extending class.
     */
//    public void execute() {
//        IKnowledge knowledge = getParams().getKnowledge();
//        SearchParams searchParams = getParams();
//
//        GFciIndTestParams indTestParams = (GFciIndTestParams) searchParams.getIndTestParams();
//
//        Graph graph;
//
//        if (getIndependenceTest() instanceof IndTestDSep) {
//            GFci gfci = new GFci(getIndependenceTest());
//            graph = gfci.search();
//        } else {
//            GFci fci = new GFci(getIndependenceTest());
//            fci.setKnowledge(knowledge);
//            fci.setCompleteRuleSetUsed(indTestParams.isCompleteRuleSetUsed());
//            fci.setMaxPathLength(indTestParams.getMaxReachablePathLength());
//            fci.setMaxIndegree(indTestParams.getDepth());
//            double penaltyDiscount = indTestParams.getPenaltyDiscount();
//
//            fci.setPenaltyDiscount(penaltyDiscount);
//            fci.setSamplePrior(indTestParams.getSamplePrior());
//            fci.setStructurePrior(indTestParams.getStructurePrior());
//            fci.setCompleteRuleSetUsed(false);
//            fci.setHeuristicSpeedup(indTestParams.isFaithfulnessAssumed());
//            graph = fci.search();
//        }
//
//        if (getSourceGraph() != null) {
//            GraphUtils.arrangeBySourceGraph(graph, getSourceGraph());
//        } else if (knowledge.isDefaultToKnowledgeLayout()) {
//            SearchGraphUtils.arrangeByKnowledgeTiers(graph, knowledge);
//        } else {
//            GraphUtils.circleLayout(graph, 200, 200, 150);
//        }
//
//        setResultGraph(graph);
//    }

    /**
     * Executes the algorithm, producing (at least) a result workbench. Must be
     * implemented in the extending class.
     */
    public void execute() {
//        IKnowledge knowledge = getParams().getKnowledge();

        Object model = getDataModel();

        if (model == null && getSourceGraph() != null) {
            model = getSourceGraph();
        }

        if (model == null) {
            throw new RuntimeException("Data source is unspecified. You may need to double click all your data boxes, \n" +
                    "then click Save, and then right click on them and select Propagate Downstream. \n" +
                    "The issue is that we use a seed to simulate from IM's, so your data is not saved to \n" +
                    "file when you save the session. It can, however, be recreated from the saved seed.");
        }

        BasicIndTestParams indTestParams = (BasicIndTestParams) getParams().getIndTestParams();
        double penaltyDiscount = 20.0;//indTestParams.getPenaltyDiscount();

        if (model instanceof Graph) {
            GraphScore gesScore = new GraphScore((Graph) model);
            ccd = new Ccd2(gesScore);
//            ccd.setKnowledge(getParams().getKnowledge());
            ccd.setVerbose(true);
        } else {

            if (model instanceof DataSet) {
                DataSet dataSet = (DataSet) model;

                if (dataSet.isContinuous()) {
                    SemBicScore gesScore = new SemBicScore(new CovarianceMatrixOnTheFly((DataSet) model));
//                    SemBicScore2 gesScore = new SemBicScore2(new CovarianceMatrixOnTheFly((DataSet) model));
//                    SemGpScore gesScore = new SemGpScore(new CovarianceMatrixOnTheFly((DataSet) model));
//                    SvrScore gesScore = new SvrScore((DataSet) model);
                    gesScore.setPenaltyDiscount(penaltyDiscount);
                    System.out.println("Score done");
                    ccd = new Ccd2(gesScore);
                }
//                else if (dataSet.isDiscrete()) {
//                    double samplePrior = ((BasicSearchParams) getParams()).getSamplePrior();
//                    double structurePrior = ((BasicSearchParams) getParams()).getStructurePrior();
//                    BDeuScore score = new BDeuScore(dataSet);
//                    score.setSamplePrior(samplePrior);
//                    score.setStructurePrior(structurePrior);
//                    gfci = new GFci(score);
//                }
                else {
                    throw new IllegalStateException("Data set must either be continuous or discrete.");
                }
            } else if (model instanceof ICovarianceMatrix) {
                SemBicScore gesScore = new SemBicScore((ICovarianceMatrix) model);
                gesScore.setPenaltyDiscount(penaltyDiscount);
                gesScore.setPenaltyDiscount(penaltyDiscount);
                ccd = new Ccd2(gesScore);
            } else if (model instanceof DataModelList) {
                DataModelList list = (DataModelList) model;

                for (DataModel dataModel : list) {
                    if (!(dataModel instanceof DataSet || dataModel instanceof ICovarianceMatrix)) {
                        throw new IllegalArgumentException("Need a combination of all continuous data sets or " +
                                "covariance matrices, or else all discrete data sets, or else a single initialGraph.");
                    }
                }

                if (list.size() != 1) {
                    throw new IllegalArgumentException("FGS takes exactly one data set, covariance matrix, or initialGraph " +
                            "as input. For multiple data sets as input, use IMaGES.");
                }

//                BasicSearchParams BasicSearchParams = (BasicSearchParams) getParams();
//                FgsIndTestParams indTestParams = (FgsIndTestParams) BasicSearchParams.getIndTestParams();

                if (allContinuous(list)) {
                    double penalty = 4;//indTestParams.getPenaltyDiscount();

                    SemBicScoreImages fgsScore = new SemBicScoreImages(list);
                    fgsScore.setPenaltyDiscount(penalty);
                    ccd = new Ccd2(fgsScore);
                }
//                else if (allDiscrete(list)) {
//                    double structurePrior = ((BasicSearchParams) getParams()).getStructurePrior();
//                    double samplePrior = ((BasicSearchParams) getParams()).getSamplePrior();
//
//                    BdeuScoreImages fgsScore = new BdeuScoreImages(list);
//                    fgsScore.setSamplePrior(samplePrior);
//                    fgsScore.setStructurePrior(structurePrior);
//
//                    gfci = new GFci(fgsScore);
//                }
                else {
                    throw new IllegalArgumentException("Data must be either all discrete or all continuous.");
                }
            } else {
                System.out.println("No viable input.");
            }
        }

//        gfci.setInitialGraph(initialGraph);
//        gfci.setKnowledge(getParams().getKnowledge());
//        gfci.setNumPatternsToStore(params.getIndTestParams().getNumPatternsToSave());
        ccd.setVerbose(true);
//        gfci.setHeuristicSpeedup(true);
//        gfci.setDepth(3);
//        ccd.setHeuristicSpeedup(indTestParams.isFaithfulnessAssumed());
        Graph graph = ccd.search();

        if (getSourceGraph() != null) {
            GraphUtils.arrangeBySourceGraph(graph, getSourceGraph());
        }
//        else if (getParams().getKnowledge().isDefaultToKnowledgeLayout()) {
//            SearchGraphUtils.arrangeByKnowledgeTiers(graph, getParams().getKnowledge());
//        } else
        {
            GraphUtils.circleLayout(graph, 200, 200, 150);
        }

        setResultGraph(graph);

//        this.topGraphs = new ArrayList<>(gfci.getTopGraphs());
//
//        if (topGraphs.isEmpty()) {
//
//            topGraphs.add(new ScoredGraph(getResultGraph(), Double.NaN));
//        }
//
//        setIndex(topGraphs.size() - 1);
    }

    private boolean allContinuous(List<DataModel> dataModels) {
        for (DataModel dataModel : dataModels) {
            if (dataModel instanceof DataSet) {
                if (!((DataSet) dataModel).isContinuous() || dataModel instanceof ICovarianceMatrix) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean allDiscrete(List<DataModel> dataModels) {
        for (DataModel dataModel : dataModels) {
            if (dataModel instanceof DataSet) {
                if (!((DataSet) dataModel).isDiscrete()) {
                    return false;
                }
            }
        }

        return true;
    }

    public IndependenceTest getIndependenceTest() {
        Object dataModel = getDataModel();

        if (dataModel == null) {
            dataModel = getSourceGraph();
        }

        SearchParams params = getParams();
        IndTestType testType = null;

        BasicSearchParams _params = (BasicSearchParams) params;
        testType = _params.getIndTestType();

        return new IndTestChooser().getTest(dataModel, params, testType);
    }

    public Graph getGraph() {
        return getResultGraph();
    }

    /**
     * @return the names of the triple classifications. Coordinates with
     */
    public List<String> getTriplesClassificationTypes() {
        List<String> names = new ArrayList<String>();
//        names.add("Definite Colliders");
//        names.add("Definite Noncolliders");
//        names.add("Ambiguous Triples");
        names.add("Underlines");
        names.add("Dotted Underlines");
        return names;
    }

    /**
     * @return the list of triples corresponding to <code>getTripleClassificationNames</code>.
     */
    public List<List<Triple>> getTriplesLists(Node node) {
        List<List<Triple>> triplesList = new ArrayList<List<Triple>>();
        Graph graph = getGraph();
        triplesList.add(GraphUtils.getUnderlinedTriplesFromGraph(node, graph));
        triplesList.add(GraphUtils.getDottedUnderlinedTriplesFromGraph(node, graph));
//        triplesList.add(DataGraphUtils.getDefiniteCollidersFromGraph(node, graph));
//        triplesList.add(DataGraphUtils.getDefiniteNoncollidersFromGraph(node, graph));
//        triplesList.add(GraphUtils.getAmbiguousTriplesFromGraph(node, graph));
        return triplesList;
    }


    @Override
    public String getAlgorithmName() {
        return "CCD";
    }
}



