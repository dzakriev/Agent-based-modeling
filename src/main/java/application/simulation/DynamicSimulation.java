package application.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DynamicSimulation extends JPanel implements ActionListener {
    private JButton button1;
    private JTextField input1;
    private JTextField input2;
    private JTextField input3;
    private JTextField input4;
    private JTextField input5;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JTextArea img1;
    private JTextArea img2;
    private JTextArea img3;
    private JTextArea img4;
    private JLabel graph;
    private DynamicModel model = new DynamicModel(10000);
    public DynamicSimulation() {
        label1 = new JLabel("Population");
        label2 = new JLabel("Infectious");
        label3 = new JLabel("ContactRateInfectious");
        label4 = new JLabel("AverageIncubationTime");
        label5 = new JLabel("AverageIllnessDuration");
        label6 = new JLabel("Healthy");
        label7 = new JLabel("Exposed");
        label8 = new JLabel("Sick");
        label9 = new JLabel("Recovered");
        label10 = new JLabel("ExposedRate");
        label11 = new JLabel("InfectRate");
        label12 = new JLabel("RecoveredRate");

        img1 = new JTextArea((model.getHealthyCount()) + "");
        img1.setMaximumSize(new Dimension(25,15));
        img2 = new JTextArea((model.getLatentCount()) + "");
        img2.setMaximumSize(new Dimension(25,15));
        img3 = new JTextArea((model.getSickCount()) + "");
        img3.setMaximumSize(new Dimension(25,15));
        img4 = new JTextArea((model.getRecoveredCount()) + "");
        img4.setMaximumSize(new Dimension(25,15));
        graph = new JLabel();
        refresh();
        createChart();
        input1 = new JTextField("10000", 5);
        input2 = new JTextField("0,6", 5);
        input3 = new JTextField("1,25", 5);
        input4 = new JTextField("10", 5);
        input5 = new JTextField("15", 5);
        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));
        input3.setMaximumSize(new Dimension(70, 26));
        input4.setMaximumSize(new Dimension(70, 26));
        input5.setMaximumSize(new Dimension(70, 26));
        button1 = new JButton("Start");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50, 26));

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
                        )
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label6)
                                                .addComponent(img1)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label10)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label7)
                                                .addComponent(img2)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label11)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label8)
                                                .addComponent(img3)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label12)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(label9)
                                                .addComponent(img4)
                                        )
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(graph)
                                        .addComponent(button1)
                                )
                        )
                );
        layout.setVerticalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label6)
                                        .addComponent(img1)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label10)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label7)
                                        .addComponent(img2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label11)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label8)
                                        .addComponent(img3)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label12)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(label9)
                                        .addComponent(img4)
                                )
                        )
                        .addGroup(layout.createParallelGroup()
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
                                )
                                .addComponent(graph)
                        )
                        .addComponent(button1)
                );
    }
    public void refresh() {
        model.Cycle();
        img1.setText((model.getHealthyCount()) + "");
        img2.setText((model.getLatentCount()) + "");
        img3.setText((model.getSickCount()) + "");
        img4.setText((model.getRecoveredCount()) + "");
    }
    public BufferedImage createChart(){
        String a1 = "Active";
        String a2 = "Inactive";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < model.getHistoryLength(); i++) {
            dataset.addValue(model.getHistory()[i][0], "Healthy",  i/3 + 1 + "");
            dataset.addValue(model.getHistory()[i][1], "Exposed", i/3 + 1 + "");
            dataset.addValue(model.getHistory()[i][2], "Sick", i/3 + 1 + "");
            dataset.addValue(model.getHistory()[i][3], "Recovered",  i/3 + 1 + "");
        }
        JFreeChart lineChart = ChartFactory.createLineChart("Population", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = lineChart.getCategoryPlot();
        lineChart.getPlot().setBackgroundPaint( Color.WHITE );
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0);
        domainAxis.setUpperMargin(0);
        plot.getRenderer().setSeriesPaint(0, Color.GREEN);
        plot.getRenderer().setSeriesPaint(1, Color.GRAY);
        plot.getRenderer().setSeriesPaint(2, Color.RED);
        plot.getRenderer().setSeriesPaint(3, Color.BLUE);
        BufferedImage img = lineChart.createBufferedImage(1000,450);
        graph.setIcon(new ImageIcon(img));
        return img;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}