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
import java.awt.*;
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
        String prediction = "USD/EURO tomorrow: 0.8832 (+0.5%)"; // tmp
        boolean growth = true; // tmp
        plotDataSets(dataSets, "Training data - FX: USD/EUR", prediction, growth);
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

    private static void plotDataSets(List<DataSet> DataSets, String title, String prediction, boolean growth){
        XYSeriesCollection collection = new XYSeriesCollection();
        for (DataSet dataSet : DataSets) {
            INDArray features = dataSet.getFeatures();
            INDArray outputs= dataSet.getLabels();
            int rowsNumber = features.rows();
            XYSeries series = new XYSeries("S" + 1);
            for (int i = 0; i < rowsNumber; i++){
                series.add(features.getDouble(i), outputs.getDouble(i));
            }
            collection.addSeries(series);
        }
        JFreeChart chart = ChartFactory.createScatterPlot(
                " " ,
                "Time [days]",
                "USD/EUR",
                collection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        JPanel panel = new ChartPanel(chart);
        JLabel label = new JLabel(prediction);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        Color color = growth ? Color.GREEN : Color.RED;
        label.setForeground(color);
        panel.add(label);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(title);
        frame.setVisible(true);
    }
}
