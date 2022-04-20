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


public class AgentBased extends JPanel implements ActionListener {
    private JButton button1;
    private JTextField input1;
    private JTextField input2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel[] labels;
    private JLabel habitatLabel = new JLabel("Habitat");
    private JLabel input1Label = new JLabel("Population");
    private JLabel input2Label = new JLabel("Iterations");
    private JLabel comboBox1Label= new JLabel("Active");
    private JLabel comboBox2Label= new JLabel("Inactive");
    private JLabel habitatImg;
    private JLabel graphImg;
    private Agent[] agents;
    private BufferedImage graph;
    private BufferedImage habitat;
    private int agentsCount;
    private JPanel buttonsPanel;
    private int iterations = 10;
    private int[] activeData;
    private int[] inactiveData;
    private Color activeColor = Color.GREEN;
    private Color inactiveColor = Color.RED;

    private static Agent[] initializeAgents() { //Метод для вызова одного конструктора через другой
        Agent[] agents = new Agent[10];
        for (int i = 0; i < 10; i++) {
            agents[i] = new Agent();
        }
        return agents;
    }

    public AgentBased(Agent[] agents) throws IOException { //Конструктор
        this.agents = agents;
        String[] items = {
                "Green",
                "Red",
                "Yellow",
                "Blue"
        };
        comboBox1 = new JComboBox(items);
        comboBox2 = new JComboBox(items);
        comboBox2.setSelectedIndex(1);
        comboBox1.setMaximumSize(new Dimension(70, 20));
        comboBox2.setMaximumSize(new Dimension(70, 20));

        button1 = new JButton("Create");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50,26));

        input1 = new JTextField("100", 5);
        input2 = new JTextField("10", 5);
        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));

        agentsCount = getText();

        graph = ImageIO.read(new File("blank.png"));
        habitat = ImageIO.read(new File("habitat.png"));

        graphImg = new JLabel();
        graphImg.setIcon(new ImageIcon(graph));
        habitatImg = new JLabel();
        habitatImg.setIcon(new ImageIcon(habitat));

        JButton[] buttons = new JButton[15];
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Чтобы сделать кнопки по центру, необходимо убрать FlowLayout
        for(int i = 0; i < 15; i++) {
            buttons[i] = new JButton(i+1+"");
            buttonsPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
            if (i > iterations-1) buttons[i].setVisible(false);
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

    public AgentBased() throws IOException {
        this((initializeAgents()));
    }
    public void paintComponent() throws IOException {
        habitat = ImageIO.read(new File("habitat.png"));
        Graphics g = habitat.createGraphics();
        updateColors();
        //clear();

        for (Agent agent : agents) {
            if (agent.isUser) {
                g.setColor(activeColor);
            }
            else {
                g.setColor(inactiveColor);
            }
            g.fillOval(agent.x+256, agent.y+256, 10, 10);
        }
        try {
            createChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        habitatImg.setIcon(new ImageIcon(habitat));
        graphImg.setIcon(new ImageIcon(graph));
    }
    /** Gets values from comboBoxes and sets them as colors for agents. */
    private void updateColors(){
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
    /** Creates a barChart using JFreeChart library*/
    public BufferedImage createChart() throws IOException {
        String a1 = "Active";
        String a2 = "Inactive";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        // Player 1
        for (int i = 0; i < 10; i++) {
            dataset.addValue(inactiveData[i], a2,i+1+"");
            dataset.addValue(activeData[i], a1, i+1+"");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Chart", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, inactiveColor);
        plot.getRenderer().setSeriesPaint(1, activeColor);
        ChartUtils.saveChartAsPNG(new File("chart.png"), barChart, 650, 400);
        graph = ImageIO.read(new File("chart.png"));
        return graph;
    }
    /** Clears area in agents habitat by drawing a white rectangle. */
    public void clear() {
        Graphics g = getGraphics();
        g.setColor(Color.white);
        g.fillRect( 200, 50, 600, 600);
    }
    public void setData(int[] activeData, int[] inactiveData) {
        this.activeData = activeData;
        this.inactiveData = inactiveData;
    }
    public int getText() { //Возвращает значение из текстового окошка
        return Integer.parseInt(input1.getText());
    }
    public void setAgents(Agent[] agents){
        this.agents = agents;
        agentsCount = agents.length;
    }
    public Agent[] getAgents() {
        return agents;
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
    /** Makes 10 iterations in the model, sets data as. */
    public void cycle() {
        int n = getText();
        int[] active_data = new int [10];
        int[] inactive_data = new int [10];
        for (int j = 0; j < iterations; j++) {
            active_data[j] =  getActiveAgents();
            inactive_data[j] = getInactiveAgents();
            for (int i = 0; i < n; i++) {
                getAgents()[i].update();
            }
        }
        setData(active_data, inactive_data);
    }
    /** Creates agents population. */
    public Agent[] create(int n) throws IOException {
        Agent[] agents = new Agent[n];
        for (int i = 0; i < n; i++) {
            agents[i] = new Agent();
        }
        //clear();
        setAgents(agents);
        cycle();
        paintComponent();
        setVisible(true);
        return agents;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Create".equals(e.getActionCommand())) {
            int agentsCount = Integer.parseInt(input1.getText());
            try {
                create(agentsCount);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
