package com.epam;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FxPredictor {

    public static void main(String[] args) throws Exception {
        String fileName = new ClassPathResource("dollar-euro.csv").getFile().getPath();
        DataSet dataSet = readCSVDataset(fileName);
        List<DataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        plotDatasets(dataSets, "Training data - FX: USD/EUR");
    }

    private static DataSet readCSVDataset(String filename) throws IOException, InterruptedException {
        RecordReader recordReader = new CSVRecordReader();
        recordReader.initialize(new FileSplit(new File(filename)));
        DataSetIterator dataSetIterator =  new RecordReaderDataSetIterator(
                recordReader,
                1000,
                1,
                1,
                true);
        return dataSetIterator.next();
    }

    private static void plotDatasets(List<DataSet> DataSets, String title){
        XYSeriesCollection collection = new XYSeriesCollection();
        for (DataSet dataSet : DataSets) {
            INDArray features = dataSet.getFeatures();
            INDArray outputs= dataSet.getLabels();
            int nRows = features.rows();
            XYSeries series = new XYSeries("S" + 1);
            for (int i = 0; i < nRows; i++){
                series.add(features.getDouble(i), outputs.getDouble(i));
            }
            collection.addSeries(series);
        }
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        JFreeChart chart = ChartFactory.createScatterPlot(
                title ,
                "Time [days]",
                "USD/EUR",
                collection,
                orientation,
                false,
                false,
                false);
        JPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(title);
        frame.setVisible(true);
    }
}
