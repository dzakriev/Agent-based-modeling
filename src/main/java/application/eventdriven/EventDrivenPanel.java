package application.eventdriven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EventDrivenPanel extends JPanel implements ActionListener, Runnable {
    private final JButton startButton;
    private final JButton stopButton;
    private final JLabel canvas;
    private EventModel model;
    private final JTextField iterations;
    private final JLabel iterationsLabel;

    //Integer.parseInt(iterations.getText())
    public EventDrivenPanel() throws InterruptedException {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        iterationsLabel = new JLabel("Iterations count");
        iterations = new JTextField("10");
        iterations.setMaximumSize(new Dimension(120, 20));
        model = new EventModel();
        canvas = new JLabel();
        canvas.setIcon(new ImageIcon(drawBlank()));
        //Метод model.Cycle вернет список команд, которые передаются методу drawModel, который вернет BufferedImage,
        //Который переведется в ImageIcon и установится иконкой лейбла канвас.
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(canvas)
                .addComponent(iterationsLabel)
                .addComponent(iterations)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addComponent(stopButton)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addComponent(canvas)
                .addComponent(iterationsLabel)
                .addComponent(iterations)
                .addGroup(layout.createParallelGroup()
                        .addComponent(startButton)
                        .addComponent(stopButton)
                )
        );

    }

    /**
     * Animates model in a different thread.
     */

    /*
     public void animateCanvas(String input) throws InterruptedException {

        exit
        addRight
        addLeft
        onBoard
        toRight
        toLeft

    String[] commands = input.split("\n");
    BufferedImage image;
    Graphics g;
    Graphics gg = getGraphics();
        for (String command : commands) {
        if (command.equals("toLeft")){
            image = drawBlank();
            g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.fillOval(500,80,95,10);
            canvas.setIcon(new ImageIcon(image));
            paint(gg);

            System.out.println(1);
            Thread.sleep(1000);

            image = drawBlank();
            g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.fillOval(400,80,95,10);
            canvas.setIcon(new ImageIcon(image));
            paint(gg);

            System.out.println(3);
            Thread.sleep(1000);

            image = drawBlank();
            g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.fillOval(300,80,95,10);
            canvas.setIcon(new ImageIcon(image));
            paint(gg);

            System.out.println(2);
            Thread.sleep(1000);
            image = drawBlank();
            g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.fillOval(200,80,95,10);
            canvas.setIcon(new ImageIcon(image));
            paint(gg);

            System.out.println(4);
            Thread.sleep(1000);
        }
    }
}*/
    public void animateCanvas(String input) throws InterruptedException {
        String[] commands = input.split("\n");
        BufferedImage image;
        Graphics g;
        Graphics gg = getGraphics();
        image = drawBlank();
        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case "toLeft":
                    image = drawBlank();
                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth() * 4 / 6 - j * image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < 4; z++) {
                            g.fillOval(image.getWidth() * 4 / 6 - j * image.getWidth() / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        paint(getGraphics());
                        Thread.sleep(1000);
                    }
                    break;

                case "toRight":

                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth() / 6 + j * image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < 1; z++) {
                            g.fillOval(image.getWidth() / 6 + j * image.getWidth() / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        paint(getGraphics());
                        Thread.sleep(1000);
                    }
                    break;

                case "exitRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z < 4; z++) {
                        g.fillOval(image.getWidth() * 5 / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);

                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    canvas.setVisible(true);
                    paint(getGraphics());
                    Thread.sleep(1000);
                    break;

                case "exitLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z < 2; z++) {
                        g.fillOval(image.getWidth() / 6 - (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);

                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    canvas.setVisible(true);
                    paint(getGraphics());
                    Thread.sleep(1000);
                    break;

                case "addRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z < 1; z++) {
                        g.fillOval(image.getWidth() * 5 / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    canvas.setVisible(true);
                    paint(getGraphics());
                    Thread.sleep(1000);
                    break;

                case "addLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z < 3; z++) {
                        g.fillOval(image.getWidth() / 6 - (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    canvas.setVisible(true);
                    paint(getGraphics());
                    Thread.sleep(1000);
                    break;

                case "onBoard":
                    break;
            }
        }
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    public BufferedImage drawBlank() {
        BufferedImage image = new BufferedImage(800, 200, 1);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 95, image.getWidth() / 6, image.getHeight() / 2 + 5);
        g.fillRect(image.getWidth() * 5 / 6, 95, image.getWidth() / 6, image.getHeight() / 2 + 5);
        g.setColor(Color.BLUE);
        g.fillRect(image.getWidth() / 6, 100, image.getWidth() * 4 / 6, image.getHeight() / 2);
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Start".equals(e.getActionCommand())) {
            try {
                animateCanvas(model.Cycle(Integer.parseInt(iterations.getText())));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        if ("Stop".equals(e.getActionCommand())) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            canvas.setIcon(new ImageIcon(drawBlank()));
        }
    }

    @Override
    public void run() {
        try {
            animateCanvas(model.Cycle(Integer.parseInt(iterations.getText())));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model = new EventModel();
    }
}
