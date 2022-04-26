package application.agent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class AgentBasedPanel extends JPanel implements ActionListener {
    private JButton button1;
    private JButton[] buttons;
    private JTextField input1;//Максимальная популяция - 10000
    private JTextField input2; //Максимум итераций - 15
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel habitatLabel = new JLabel("Habitat");
    private JLabel input1Label = new JLabel("Population");
    private JLabel input2Label = new JLabel("Iterations");
    private JLabel comboBox1Label = new JLabel("Active");
    private JLabel comboBox2Label = new JLabel("Inactive");
    private JLabel habitatImg;
    private JLabel graphImg;
    private Agent[] agents;
    private Agent[][] agentsHistory;
    private BufferedImage graph;
    private BufferedImage habitat;
    private int agentsCount;
    private JPanel buttonsPanel;
    private int iterations;
    private int[] activeData;
    private int[] inactiveData;
    private Color activeColor = Color.GREEN;
    private Color inactiveColor = Color.RED;

    public AgentBasedPanel(Agent[] agents) throws IOException { //Конструктор
        String[] colors = {
                "Green",
                "Red",
                "Yellow",
                "Blue"
        };
        comboBox1 = new JComboBox(colors);
        comboBox2 = new JComboBox(colors);
        comboBox2.setSelectedIndex(1);
        comboBox1.setMaximumSize(new Dimension(70, 20));
        comboBox2.setMaximumSize(new Dimension(70, 20));

        button1 = new JButton("Create");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50, 26));

        input1 = new JTextField("100", 5);
        input2 = new JTextField("15", 5);
        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));
        input1.setMinimumSize(new Dimension(70, 26));
        input2.setMinimumSize(new Dimension(70, 26));

        this.agents = agents;
        agentsCount = getPopulation();
        iterations = getIterations();
        agentsHistory = new Agent[15][10000];
        for (int i = 1; i < iterations; i++) {
            agentsHistory[i] = new Agent[10000];
            for (int j = 0; j < agentsCount; j++) {
                agentsHistory[i][j] = new Agent();
            }
        }

        graph = ImageIO.read(new File("blank.png"));
        habitat = ImageIO.read(new File("habitat.png"));

        graphImg = new JLabel();
        graphImg.setIcon(new ImageIcon(graph));
        habitatImg = new JLabel();
        habitatImg.setIcon(new ImageIcon(habitat));

        buttons = new JButton[15];
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Чтобы сделать кнопки по центру, необходимо убрать FlowLayout
        for (int i = 0; i < 15; i++) {
            buttons[i] = new JButton(i + 1 + "");
            buttonsPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
            if (i > iterations - 1) buttons[i].setVisible(false);
        }


        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(input1Label)
                                        .addGap(30)
                                        .addComponent(input2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(input1)
                                        .addGap(20)
                                        .addComponent(input2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboBox1Label)
                                        .addGap(55)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboBox1)
                                        .addGap(20)
                                        .addComponent(comboBox2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(45)
                                        .addComponent(button1)
                                )
                        )
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(240)
                                        .addComponent(habitatLabel)
                                )
                                .addComponent(habitatImg)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(graphImg)
                                .addComponent(buttonsPanel)
                        )

                );
        layout.setVerticalGroup
                (layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(input1Label)
                                        .addComponent(input2Label)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(input1)
                                        .addComponent(input2)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(comboBox1Label)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(comboBox1)
                                        .addComponent(comboBox2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20)
                                        .addComponent(button1)
                                )
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(habitatLabel)
                                .addComponent(habitatImg)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40)
                                .addComponent(graphImg)
                                .addComponent(buttonsPanel)
                        )
                );
    }

    public AgentBasedPanel() throws IOException {
        this((initializeAgents(10)));
    }
    /**
     * Static method for initializing object with no arguments.
     */
    private static Agent[] initializeAgents(int agentsCount) { //Метод для вызова одного конструктора через другой
        Agent[] agents = new Agent[agentsCount];
        Agent.clearConnection();
        for (int i = 0; i < agentsCount; i++) {
            agents[i] = new Agent();
        }
        return agents;
    }

    /**
     * Paints agents, updates JLabel responsible for habitat.
     */
    public void paintAgents(Agent[] agents) throws IOException {
        habitat = ImageIO.read(new File("habitat.png"));
        Graphics g = habitat.createGraphics();
        updateColors();
        for (int i = 0; i < agentsCount; i++) {
            if (agents[i].isUser) {
                g.setColor(activeColor);
            } else {
                g.setColor(inactiveColor);
            }
            g.fillOval(agents[i].x + 256, agents[i].y + 256, 10, 10);
        }
        habitatImg.setIcon(new ImageIcon(habitat));
    }

    public void paintComponent() throws IOException {
        paintAgents(agents);
        try {
            createChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets values from comboBoxes and sets them as colors for agents.
     */
    private void updateColors() {
        switch ((String) (Objects.requireNonNull(comboBox1.getSelectedItem()))) {
            case ("Red") -> activeColor = Color.RED;
            case ("Blue") -> activeColor = Color.BLUE;
            case ("Yellow") -> activeColor = Color.YELLOW;
            default -> activeColor = Color.GREEN;
        }
        switch ((String) (Objects.requireNonNull(comboBox2.getSelectedItem()))) {
            case ("Green") -> inactiveColor = Color.GREEN;
            case ("Blue") -> inactiveColor = Color.BLUE;
            case ("Yellow") -> inactiveColor = Color.YELLOW;
            default -> inactiveColor = Color.RED;
        }
    }

    /**
     * Creates a barChart using JFreeChart library
     */
    public BufferedImage createChart() throws IOException {
        String a1 = "Active";
        String a2 = "Inactive";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < iterations; i++) {
            dataset.addValue(inactiveData[i], a2, i + 1 + "");
            dataset.addValue(activeData[i], a1, i + 1 + "");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Population", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, inactiveColor);
        plot.getRenderer().setSeriesPaint(1, activeColor);
        ChartUtils.saveChartAsPNG(new File("chart.png"), barChart, 650, 400);
        graph = ImageIO.read(new File("chart.png"));
        graphImg.setIcon(new ImageIcon(graph));
        return graph;
    }

    public void setData(int[] activeData, int[] inactiveData) {
        this.activeData = activeData;
        this.inactiveData = inactiveData;
    }

    public int getPopulation() { //Возвращает значение из текстового окошка
        if (Integer.parseInt(input1.getText()) > 10000) {
            input1.setText("10000");
        }
        if (Integer.parseInt(input1.getText()) < 1) {
            input1.setText("1");
        }
        return Integer.parseInt(input1.getText());
    }

    public int getIterations() { //Возвращает значение из текстового окошка
        if (Integer.parseInt(input2.getText()) > 15) {
            input2.setText("15");
        }
        if (Integer.parseInt(input2.getText()) < 1) {
            input2.setText("1");
        }
        return Integer.parseInt(input2.getText());
    }

    public Agent[] getAgents() {
        return agents;
    }

    public void setAgents(Agent[] agents) {
        this.agents = agents;
        agentsCount = getPopulation();
        iterations = getIterations();
    }

    public int getActiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (agent.isUser) i++;
        }
        return i;
    }

    public int getInactiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (!agent.isUser) i++;
        }
        return i;
    }

    /**
     * Makes iterations in the model, saves data.
     */
    public void cycle() {
        int[] active_data = new int[iterations];
        int[] inactive_data = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < agentsCount; j++) {
                Agent temp_agent = new Agent();
                temp_agent.x = agents[j].x;
                temp_agent.y = agents[j].y;
                temp_agent.isUser = agents[j].isUser;
                temp_agent.daysLeft = agents[j].daysLeft;
                agentsHistory[i][j] = temp_agent;
            }
            active_data[i] = getActiveAgents();
            inactive_data[i] = getInactiveAgents();
            if (i != iterations - 1) for (int j = 0; j < agentsCount; j++) {
                agents[j].update();
            }
        }
        setData(active_data, inactive_data);
    }

    /**
     * Creates agents population.
     */
    public void create(int n) throws IOException {
        Agent[] agents = new Agent[n];
        Agent.clearConnection();
        for (int i = 0; i < n; i++) {
            agents[i] = new Agent();
        }
        setAgents(agents);
        cycle();
        paintComponent();
        for (int i = 0; i < 15; i++) {
            buttons[i].setVisible(i <= iterations - 1);
        }
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Create".equals(e.getActionCommand())) {
            agentsCount = getPopulation();
            iterations = getIterations();
            try {
                create(agentsCount);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        for (int i = 1; i < 16; i++) {
            if ((i + "").equals(e.getActionCommand())) {
                try {
                    paintAgents(agentsHistory[i - 1]);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

    }
}
