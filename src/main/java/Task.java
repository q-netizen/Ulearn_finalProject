package main.java;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Task {
    private final main.java.DatabaseHandler database;
    private final ArrayList<String> countries;

    public Task(main.java.DatabaseHandler database) {
        this.database = database;
        countries = database.getAllCountryList();
    }

    public void countriesEconomyBar() throws IOException {
        var lines = new ArrayList<String>();
        var data = new DefaultCategoryDataset();
        for (var e:countries) {
            var economy = database.getCountryField(e, "economy");
            data.addValue(economy, e, database.getCountryRegion(e));
            lines.add(String.format("Страна: %s     показатель экономики: %s", e, economy));
        }
        var file = Paths.get("countries.txt");
        Files.write(file, lines, StandardCharsets.UTF_8);
        var chart = ChartFactory.createBarChart3D(
                "Показатели экономики стран",
                "Страна",
                "Показатель экономики",
                data,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);
        var plot = chart.getCategoryPlot();
        var bar = (BarRenderer) plot.getRenderer();
        bar.setItemMargin(0.25);
        var domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        var frame = new JFrame("Таблица показателей стран 2015");
        var chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void highEconomyCountry() {
        var country = database.getNameWithHighestEconomy("Latin America and Caribbean",
                "Eastern Asia");
        System.out.printf(country);
    }

    public void averageEconomyCountry() {
        var countries = database.getCountryListByRegions("Western Europe", "North America");
        var fields = new String[]{"happinessScore", "standardError", "economy", "family", "health", "freedom", "trust", "generosity", "dystopiaResidual"};
        var referenceAverages = new double[fields.length];
        for (var i = 0; i < fields.length; i++) {
            referenceAverages[i] = database.getFieldAverage(fields[i], "Western Europe", "North America");
        }
        var min = Arrays.stream(new double[fields.length]).map(x -> Double.MAX_VALUE).toArray();
        var maxMin = 0;
        var averageCountry = "";
        for (var country : countries) {
            var curCount = 0;
            var curDeltas = new double[fields.length];
            for (var i = 0; i < fields.length; i++) {
                var field = database.getCountryField(country, fields[i]);
                curDeltas[i] = Math.abs(referenceAverages[i] - field);
                if (curDeltas[i] < min[i]) {
                    curCount++;
                }
            }
            if (curCount > maxMin) {
                min = Arrays.copyOf(curDeltas, curDeltas.length);
                maxMin = curCount;
                averageCountry = country;
            }
        }
        System.out.printf(averageCountry);
    }
}
