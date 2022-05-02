package application.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DynamicSimulationPanel extends JPanel implements ActionListener {
    private final JTextField populationInput;
    private final JTextField infectiousInput;
    private final JTextField contactRateInput;
    private final JTextField averageIncubationTimeInput;
    private final JTextField averageIllnessDurationInput;
    private final JTextArea tableText;
    private final JLabel graph;
    private DynamicModel model = new DynamicModel(10000, 0.6, 1.25, 10, 15);

    public DynamicSimulationPanel() {
        JLabel populationLabel = new JLabel("Population");
        JLabel infectiousLabel = new JLabel("Infectious");
        JLabel contactRateLabel = new JLabel("Contact Rate");
        JLabel averageIncubationTimeLabel = new JLabel("Average Incubation Time");
        JLabel averageIllnessDurationLabel = new JLabel("Average Illness Duration");

        populationInput = new JTextField("10000");
        infectiousInput = new JTextField("0.6");
        contactRateInput = new JTextField("1.25");
        averageIncubationTimeInput = new JTextField("10");
        averageIllnessDurationInput = new JTextField("15");

        populationInput.setMaximumSize(new Dimension(70, 26));
        infectiousInput.setMaximumSize(new Dimension(70, 26));
        contactRateInput.setMaximumSize(new Dimension(70, 26));
        averageIncubationTimeInput.setMaximumSize(new Dimension(70, 26));
        averageIllnessDurationInput.setMaximumSize(new Dimension(70, 26));

        JButton createButton = new JButton("Start");
        createButton.addActionListener(this);
        createButton.setMaximumSize(new Dimension(70, 26));

        graph = new JLabel();

        tableText = new JTextArea();
        tableText.setEditable(false);
        JScrollPane table = new JScrollPane(tableText);
        table.setVisible(true);
        table.setMaximumSize(new Dimension(300,450));
        table.setPreferredSize(new Dimension(200,450));
        refresh();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(populationLabel)
                                .addComponent(populationInput)
                                .addComponent(infectiousLabel)
                                .addComponent(infectiousInput)
                                .addComponent(contactRateLabel)
                                .addComponent(contactRateInput)
                                .addComponent(averageIncubationTimeLabel)
                                .addComponent(averageIncubationTimeInput)
                                .addComponent(averageIllnessDurationLabel)
                                .addComponent(averageIllnessDurationInput)
                                .addComponent(createButton)
                        )
                        .addComponent(graph)
                        .addComponent(table)
                );
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(populationLabel)
                        .addComponent(populationInput)
                        .addComponent(infectiousLabel)
                        .addComponent(infectiousInput)
                        .addComponent(contactRateLabel)
                        .addComponent(contactRateInput)
                        .addComponent(averageIncubationTimeLabel)
                        .addComponent(averageIncubationTimeInput)
                        .addComponent(averageIllnessDurationLabel)
                        .addComponent(averageIllnessDurationInput)
                        .addComponent(createButton)
                )
                .addComponent(graph)
                .addComponent(table)
        );
    }

    private void refresh() {
        model = new DynamicModel(getPopulationInput(), getInfectiousInput(),
                getContactRateInput(), getAverageIncubationTimeInput(), getAverageIllnessDurationInput());
        graph.setIcon(new ImageIcon(createChart()));
        createTable();
    }

    private BufferedImage createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < model.getHistoryLength(); i++) {
            dataset.addValue(model.getHistory().get(i).get(0), "Healthy", i + 1 + "");
            dataset.addValue(model.getHistory().get(i).get(1), "Exposed", i + 1 + "");
            dataset.addValue(model.getHistory().get(i).get(2), "Sick", i + 1 + "");
            dataset.addValue(model.getHistory().get(i).get(3), "Recovered", i + 1 + "");
        }
        JFreeChart lineChart = ChartFactory.createLineChart("Population", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = lineChart.getCategoryPlot();
        if (dataset.getColumnCount() > 53) {
            plot.getDomainAxis().setVisible(false);
        }
        plot.setBackgroundPaint(Color.WHITE);
        plot.getDomainAxis().setLowerMargin(0);
        plot.getDomainAxis().setUpperMargin(0);
        plot.getRenderer().setSeriesPaint(0, Color.GREEN);
        plot.getRenderer().setSeriesPaint(1, Color.GRAY);
        plot.getRenderer().setSeriesPaint(2, Color.RED);
        plot.getRenderer().setSeriesPaint(3, Color.BLUE);
        return lineChart.createBufferedImage(1000, 450);
    }

    public int getPopulationInput() {
        try {
            Integer.parseInt(populationInput.getText());
        } catch (NumberFormatException ex) {
            populationInput.setText("1000");
            return 1000;
        }
        if (Integer.parseInt(populationInput.getText()) > 10000) {
            populationInput.setText("10000");
            return 10000;
        }
        if (Integer.parseInt(populationInput.getText()) < 10) {
            populationInput.setText("10");
            return 10;
        }
        return Integer.parseInt(populationInput.getText());
    }

    public double getInfectiousInput() {
        try {
            Double.parseDouble(infectiousInput.getText());
        } catch (NumberFormatException ex) {
            infectiousInput.setText("0.6");
            return 0.6;
        }
        if (Double.parseDouble(infectiousInput.getText()) > 1) {
            infectiousInput.setText("1");
            return 1;
        }
        if (Double.parseDouble(infectiousInput.getText()) <= 0) {
            infectiousInput.setText("0.1");
            return 0.1;
        }
        return Double.parseDouble(infectiousInput.getText());
    }

    public double getContactRateInput() {
        try {
            Double.parseDouble(contactRateInput.getText());
        } catch (NumberFormatException ex) {
            contactRateInput.setText("1.25");
            return 1.25;
        }
        if (Double.parseDouble(contactRateInput.getText()) > 10) {
            contactRateInput.setText("10");
            return 10;
        }
        if (Double.parseDouble(contactRateInput.getText()) <= 0) {
            contactRateInput.setText("0.1");
            return 0.1;
        }
        return Double.parseDouble(contactRateInput.getText());
    }

    public int getAverageIncubationTimeInput() {
        try {
            Integer.parseInt(averageIncubationTimeInput.getText());
        } catch (NumberFormatException ex) {
            averageIncubationTimeInput.setText("10");
            return 10;
        }
        if (Integer.parseInt(averageIncubationTimeInput.getText()) > 60) {
            averageIncubationTimeInput.setText("60");
            return 60;
        }
        if (Integer.parseInt(averageIncubationTimeInput.getText()) < 1) {
            averageIncubationTimeInput.setText("1");
            return 1;
        }
        return Integer.parseInt(averageIncubationTimeInput.getText());
    }

    public int getAverageIllnessDurationInput() {
        try {
            Integer.parseInt(averageIllnessDurationInput.getText());
        } catch (NumberFormatException ex) {
            averageIllnessDurationInput.setText("10");
            return 10;
        }
        if (Integer.parseInt(averageIllnessDurationInput.getText()) > 365) {
            averageIllnessDurationInput.setText("365");
            return 365;
        }
        if (Integer.parseInt(averageIllnessDurationInput.getText()) < 1) {
            averageIllnessDurationInput.setText("1");
            return 1;
        }
        return Integer.parseInt(averageIllnessDurationInput.getText());
    }

    private void createTable() {
        tableText.setText(model.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refresh();
    }
}