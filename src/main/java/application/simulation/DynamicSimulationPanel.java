package application.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DynamicSimulationPanel extends JPanel implements ActionListener {
    private final JTextField input1;
    private final JTextField input2;
    private final JTextField input3;
    private final JTextField input4;
    private final JTextField input5;
    private final JTextArea tableText;
    private final JLabel graph;
    private DynamicModel model = new DynamicModel(10000, 0.6, 1.25, 10, 15);

    public DynamicSimulationPanel() {
        JLabel label1 = new JLabel("Population");
        JLabel label2 = new JLabel("Infectious");
        JLabel label3 = new JLabel("Contact Rate");
        JLabel label4 = new JLabel("Average Incubation Time");
        JLabel label5 = new JLabel("Average Illness Duration");

        input1 = new JTextField("10000", 5);
        input2 = new JTextField("0.6", 5);
        input3 = new JTextField("1.25", 5);
        input4 = new JTextField("10", 5);
        input5 = new JTextField("15", 5);

        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));
        input3.setMaximumSize(new Dimension(70, 26));
        input4.setMaximumSize(new Dimension(70, 26));
        input5.setMaximumSize(new Dimension(70, 26));

        JButton button1 = new JButton("Start");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50, 26));

        graph = new JLabel();

        tableText = new JTextArea("hey");
        tableText.setEditable(false);
        JScrollPane table = new JScrollPane(tableText);
        table.setVisible(true);
        table.setMaximumSize(new Dimension(500, 450));

        refresh();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label1)
                                .addComponent(input1)
                                .addComponent(label2)
                                .addComponent(input2)
                                .addComponent(label3)
                                .addComponent(input3)
                                .addComponent(label4)
                                .addComponent(input4)
                                .addComponent(label5)
                                .addComponent(input5)
                                .addComponent(button1)
                        )
                        .addComponent(graph)
                        .addComponent(table)
                );
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label1)
                        .addComponent(input1)
                        .addComponent(label2)
                        .addComponent(input2)
                        .addComponent(label3)
                        .addComponent(input3)
                        .addComponent(label4)
                        .addComponent(input4)
                        .addComponent(label5)
                        .addComponent(input5)
                        .addComponent(button1)
                )
                .addComponent(graph)
                .addComponent(table)
        );
    }

    private void refresh() {
        model = new DynamicModel(Integer.parseInt(input1.getText()), Double.parseDouble(input2.getText()),
                Double.parseDouble(input3.getText()), Integer.parseInt(input4.getText()), Integer.parseInt(input5.getText()));
        model.Cycle();
        graph.setIcon(new ImageIcon(createChart()));
        createTable();
    }

    private BufferedImage createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < model.getHistoryLength(); i++) {
            dataset.addValue(model.getHistory()[i][0], "Healthy", i + 1 + "");
            dataset.addValue(model.getHistory()[i][1], "Exposed", i + 1 + "");
            dataset.addValue(model.getHistory()[i][2], "Sick", i + 1 + "");
            dataset.addValue(model.getHistory()[i][3], "Recovered", i + 1 + "");
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

    private void createTable() {
        tableText.setText(model.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refresh();
    }
}