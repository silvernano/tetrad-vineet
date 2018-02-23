package edu.pitt.csb.mgm;

import cern.colt.matrix.DoubleMatrix2D;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.data.DiscreteVariable;
import edu.cmu.tetrad.graph.Edge;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.Node;
import edu.cmu.tetrad.graph.NodeType;
import edu.cmu.tetrad.search.CpcStable;
import edu.cmu.tetrad.search.FciMaxP;
import edu.cmu.tetrad.search.IndependenceTest;
import edu.pitt.csb.latents.LatentPrediction;
import edu.pitt.csb.stability.SearchWrappers;
import edu.pitt.csb.stability.StabilityUtils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static edu.pitt.csb.stability.StabilityUtils.StabilitySearchPar;

/**
 * Created by vinee_000 on 9/13/2017.
 */
public class STARS {
        private DataSet d;
        private int N;
        public int b;
        private double [] alpha;
        public double [] mgmLambda = {0.2,0.2,0.2};
        private double gamma;
        private boolean includeZeros = true;
        private final int iterLimit = 1000;
        public double origLambda;
        public Graph pdGraph;
        public Graph lastGraph;
        public double lastAlpha;
        private boolean leaveOneOut = false;
        private Algorithm alg;
        public double [][] stabilities = null;
        private DataSet [] subsamples;
        private Graph trueGraph;



        //Delete this once done debugging
        private int runNumber = 0;
        //TODO

        public STARS(DataSet dat,double [] alp,double g,int numSub)
        {
            N = numSub;
            gamma = g;
            alpha = alp;
            d = dat;
            b = (int)Math.floor( 10*Math.sqrt(dat.getNumRows()));
            if (b > d.getNumRows())
                b = d.getNumRows()/2;

        }
    public STARS(DataSet dat,double [] alp,double g,int numSub, Algorithm a)
    {
        N = numSub;
        gamma = g;
        alpha = alp;
        d = dat;
        b = (int)Math.floor( 10*Math.sqrt(dat.getNumRows()));
        if (b > d.getNumRows())
            b = d.getNumRows()/2;
        alg = a;

    }
    public STARS(DataSet dat,double [] alp,double g,DataSet [] subs, Algorithm a)
    {
        subsamples = subs;
        gamma = g;
        alpha = alp;
        d = dat;
        b = (int)Math.floor( 10*Math.sqrt(dat.getNumRows()));
        if (b > d.getNumRows())
            b = d.getNumRows()/2;
        alg = a;

    }

        public STARS(DataSet dat,double [] alp,double g,int numSub,int b)
        {
            N = numSub;
            gamma = g;
            alpha = alp;
            d = dat;
            this.b = b;

        }
        public STARS(DataSet dat,double [] alp,double g,int numSub,boolean loo)
        {
            leaveOneOut = loo;
            N = numSub;
            gamma = g;
            alpha = alp;
            d = dat;
            b = (int)Math.floor( 10*Math.sqrt(dat.getNumRows()));
            if (b > d.getNumRows())
                b = d.getNumRows()/2;

        }

        public void setRun(int n)
        {
            runNumber = n;
        }

        public void setTrueGraph(Graph g)
        {
            trueGraph = g;
        }
        public void setAlgorithm (Algorithm a)
        {
            alg = a;
        }

        public void setMGMLambda(double [] lambda)
        {
            mgmLambda = lambda;
        }
        public double [][] runSTARS()
        {

            PrintStream out;
            PrintStream out2;

            try {
                out = new PrintStream("Detailed_Results_" + d.getNumColumns() + "_"  + runNumber + "_" + d.getNumColumns() + ".txt");
                out2 = new PrintStream("Detailed_Results_" + d.getNumColumns() + "_" + runNumber + "_" + d.getNumColumns() + "_AUC.txt");
                out.println("Alpha\tNum_Latents_Predicted\tEdge_Stability\tLatent_Stability\tPrecision\tRecall");
                out2.println("Alpha\tPrediction\tActual");
            }
            catch(Exception e)
            {
                return null;
            }
            double [][] result = new double[alpha.length][4];
            Arrays.sort(alpha);

            int currIndex = alpha.length-1;
            double CC = -1;
            double CD = -1;
            double DD = -1;
            double CCMax = 100;
            double CCMaxI = -1;
            double CDMax = 100;
            double CDMaxI = -1;
            double DDMax = 100;
            double DDMaxI = -1;
            double oneLamb = -1;
            double allMax = 100;
            double allMaxI = -1;
            int p = 0;
            int q = 0;
            for(int i =0; i < d.getNumColumns();i++)
            {
                Node n = d.getVariable(i);
                if(n instanceof DiscreteVariable)
                    q++;
                else
                    p++;
            }
            // System.out.println("P:" + p);
            //   System.out.println("Q:" + q);
            //  System.out.println("b:" + b);
            A:while(true) //go until we break by having instability better than threshold
            {
                System.out.println("Alpha: " + alpha[currIndex]);
                double [] paramsCurr = {mgmLambda[0],mgmLambda[1],mgmLambda[2],alpha[currIndex]};
                DoubleMatrix2D adjMat= null;
                DoubleMatrix2D adjMat2 = null;
                if(leaveOneOut)
                {
                    //TODO SearchPar or SearchParLatent? basically can we select a good alpha value better than just using stability?
                    if(alg ==Algorithm.FCI) {
                        double [] fciParam = {alpha[currIndex]};
                        adjMat = StabilityUtils.StabilitySearchPar(d, new SearchWrappers.FCIWrapper(fciParam));
                    }
                    else if(alg==Algorithm.MGMFCI)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d,new SearchWrappers.MGMFCIWrapper(paramsCurr));
                    }
                    else if(alg==Algorithm.MGMFCIMAX)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d,new SearchWrappers.MFMWrapper(paramsCurr));
                    }
                }
                else if(subsamples==null) {
                    if(alg ==Algorithm.FCI) {
                        double [] fciParam = {alpha[currIndex]};
                        adjMat = StabilityUtils.StabilitySearchPar(d, new SearchWrappers.FCIWrapper(fciParam),N,b);
                    }
                    else if(alg==Algorithm.MGMFCI)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d,new SearchWrappers.MGMFCIWrapper(paramsCurr),N,b);
                    }
                    else if(alg==Algorithm.MGMFCIMAX)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d, new SearchWrappers.MFMWrapper(paramsCurr), N, b);
                    }
                }
                else
                {
                    if(alg ==Algorithm.FCI) {
                        double [] fciParam = {alpha[currIndex]};
                        adjMat = StabilityUtils.StabilitySearchPar(d, new SearchWrappers.FCIWrapper(fciParam),subsamples);
                    }
                    else if(alg==Algorithm.MGMFCI)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d,new SearchWrappers.MGMFCIWrapper(paramsCurr),subsamples);
                    }
                    else if(alg==Algorithm.MGMFCIMAX)
                    {
                        adjMat = StabilityUtils.StabilitySearchPar(d, new SearchWrappers.MFMWrapper(paramsCurr), subsamples);
                        adjMat2 = StabilityUtils.StabilitySearchParLatent(d,new SearchWrappers.MFMWrapper(paramsCurr),subsamples);
                        //TODO CHANGE BACK TO NORMAL
                    }
                }

                double ccDestable = 0;
                double cdDestable = 0;
                double ddDestable = 0;
                double numCC = 0;
                double numCD = 0;
                double numDD = 0;
                for(int j = 0; j < d.getNumColumns();j++)
                {
                    for(int k = j+1; k < d.getNumColumns();k++)
                    {
                        Node one = d.getVariable(j);
                        Node two = d.getVariable(k);
                        if(one instanceof DiscreteVariable && two instanceof DiscreteVariable)
                        {
                            //          System.out.println("DD: " + adjMat[j][k]);
                            //       if(adjMat[j][k]!=0)
                            numDD++;
                            ddDestable += 2*adjMat.get(j,k)*(1-adjMat.get(j,k));
                        }
                        else if(one instanceof DiscreteVariable || two instanceof DiscreteVariable)
                        {
                            //     if(adjMat[j][k]!=0)
                            numCD++;
                            //         System.out.println("CD: " + adjMat[j][k]);
                            cdDestable += 2*adjMat.get(j,k)*(1-adjMat.get(j,k));

                        }
                        else
                        {
                            //   if(adjMat[j][k]!=0)
                            numCC++;
                            //       System.out.println("CC:" + adjMat[j][k]);
                            ccDestable+=2*adjMat.get(j,k)*(1-adjMat.get(j,k));
                        }
                    }
                }
                double allDestable = ccDestable + cdDestable + ddDestable;
                allDestable = allDestable/(numCC+numCD+numDD);
                if(allDestable <= gamma && oneLamb < 0)
                    oneLamb = alpha[currIndex];
                if(allDestable <= allMax)
                {
                    allMax = allDestable;
                    allMaxI = alpha[currIndex];
                }
            /*
            ccDestable = ccDestable/ MathUtils.choose(p,2);
            cdDestable = cdDestable/(p*q);
            ddDestable = ddDestable/(MathUtils.choose(q,2));
            */
                ccDestable = ccDestable / numCC;
                cdDestable = cdDestable / numCD;
                ddDestable = ddDestable/numDD;
                result[currIndex][0] = ccDestable;
                result[currIndex][1] = cdDestable;
                result[currIndex][2] = ddDestable;
                result[currIndex][3] = allDestable;
                // System.out.println("CC:" + ccDestable);
                // System.out.println("CD:" + cdDestable);
                // System.out.println("DD:" + ddDestable);
                /*if(ccDestable <= gamma && CC == -1)
                    CC = lambda[currIndex];
                if(cdDestable <= gamma && CD==-1)
                    CD = lambda[currIndex];
                if(ddDestable <= gamma && DD == -1)
                    DD = lambda[currIndex];
                if(ccDestable <= CCMax)
                {
                    CCMax = ccDestable;
                    CCMaxI = lambda[currIndex];
                }
                if(cdDestable <= CDMax)
                {
                    CDMax = cdDestable;
                    CDMaxI = lambda[currIndex];
                }
                if(ddDestable <= DDMax)
                {
                    DDMax = ddDestable;
                    DDMaxI = lambda[currIndex];
                }*/
                if(oneLamb!=-1)
                    break A;
                if(currIndex==0)
                    break A;
                currIndex--;

                //TODO DELETE THIS PART WHEN DONE EXPLORING
                try {
                    out.print(alpha[currIndex + 1] + "\t");
                    int numLatents = 0;
                    ArrayList<LatentPrediction.Pair> estLatents = new ArrayList<LatentPrediction.Pair>();
                    for(int i = 0; i < adjMat2.rows();i++)
                    {
                        for(int j = i+1; j < adjMat2.columns();j++)
                        {
                            if(adjMat2.get(i,j)>0.5) {
                                estLatents.add(new LatentPrediction.Pair(d.getVariable(i),d.getVariable(j)));
                                numLatents++;
                            }
                            out2.println(alpha[currIndex+1] + "\t" + adjMat2.get(i,j) + "\t" + isLatent(trueGraph,d,i,j));
                        }
                    }
                    out.println(numLatents+"\t"+ allDestable + "\t" + latentStability(adjMat2) + "\t" + LatentPrediction.getPrecision(estLatents,trueGraph,d,"All") + "\t" + LatentPrediction.getRecall(estLatents,trueGraph,d,"All"));
                    out.flush();
                    out2.flush();
                    //out.close();
                }
                catch(Exception e)
                {

                }
                /////////////////////////////////////////////////////
            }
            if(CC==-1)
                CC = CCMaxI;
            if(CD==-1)
                CD = CDMaxI;
            if(DD==-1)
                DD = DDMaxI;

            if(oneLamb < 0)
                oneLamb = allMaxI;
            origLambda = oneLamb;

            out.flush();
            out.close();
            out2.flush();
            out2.close();
            // DoubleMatrix2D stabs = StabilitySearchPar(d,new SearchWrappers.MGMWrapper(lambda));
            // this.stabilities = stabs.toArray();
            lastAlpha = oneLamb;
            System.out.println("Alpha chosen: " + lastAlpha);
            return result;
        }


        private synchronized int isLatent(Graph truth, DataSet d, int i, int j)
        {
            for(Node n : truth.getParents(truth.getNode(d.getVariable(i).getName())))
            {
                if(n.getNodeType()== NodeType.LATENT && truth.isParentOf(n,truth.getNode(d.getVariable(j).getName())))
                {
                    return 1;
                }
            }
            return 0;
        }
        private synchronized double latentStability(DoubleMatrix2D adjMat)
        {
            double deStable = 0;
            int numStable = 0;
            for(int j = 0; j < d.getNumColumns();j++)
            {
                for(int k = j+1; k < d.getNumColumns();k++)
                {
                        numStable++;
                        deStable+=2*adjMat.get(j,k)*(1-adjMat.get(j,k));
                }
            }
            return deStable/numStable;
        }
    }
