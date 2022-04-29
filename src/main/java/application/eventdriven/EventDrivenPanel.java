package application.eventdriven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EventDrivenPanel extends JPanel implements ActionListener, Runnable{
    private JButton startButton;
    private JButton stopButton;
    private JLabel canvas;
    private EventModel model;
    private JTextField iterations;

    //Integer.parseInt(iterations.getText())
    public EventDrivenPanel() throws InterruptedException {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        iterations = new JTextField("10");
        iterations.setMaximumSize(new Dimension(20, 20));
        model = new EventModel();
        canvas = new JLabel();
        canvas.setIcon(new ImageIcon(drawBlank()));
        //Метод model.Cycle вернет список команд, которые передаются методу drawModel, который вернет BufferedImage,
        //Который переведется в ImageIcon и установится иконкой лейбла канвас.
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(canvas)
                .addComponent(iterations)
                .addComponent(startButton)
                .addComponent(stopButton)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addComponent(canvas)
                .addComponent(iterations)
                .addComponent(startButton)
                .addComponent(stopButton)
        );

    }

    public void animateCanvas(String input) throws InterruptedException {
        /*
        exit
        addRight
        addLeft
        onBoard
        toRight
        toLeft
         */
        String[] commands = input.split("\n");
        BufferedImage image;
        Graphics g;
        Graphics gg = getGraphics();
        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case "toLeft":
                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth()*4/6 - j * image.getWidth()/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < model.getHistory().get(i); z++) {
                            g.fillOval(image.getWidth()*4/6 - j * image.getWidth()/6 + (z+1) * 25, image.getHeight()*2/5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        paint(gg);
                        Thread.sleep(1000);
                    }
                    break;

                case "toRight":
                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth()/6 + j * image.getWidth()/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < model.getHistory().get(i); z++) {
                            g.fillOval(image.getWidth()/6 + j * image.getWidth()/6 + (z+1) * 25, image.getHeight()*2/5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        paint(gg);
                        Thread.sleep(1000);
                    }
                    break;

                case "exitRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()*4/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z <  model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth()*5/6 + (z+1) * 25, image.getHeight()*2/5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);

                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()*4/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);
                    break;

                case "exitLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z <  model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth()/6 - (z+1) * 25, image.getHeight()*2/5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);

                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);
                    break;

                case "addRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()*4/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z <  model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth()*5/6 + (z+1) * 25, image.getHeight()*2/5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
                    Thread.sleep(1000);
                    break;

                case "addLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth()/6, image.getHeight()*2/5+10, image.getWidth()/6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z <  model.getHistory().get(i); z++) {
                    g.fillOval(image.getWidth()/6 - (z+1) * 25, image.getHeight()*2/5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    paint(gg);
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
        g.fillRect(0, 95, image.getWidth()/6, image.getHeight()/2+5);
        g.fillRect(image.getWidth()*5/6, 95, image.getWidth()/6, image.getHeight()/2+5);
        g.setColor(Color.BLUE);
        g.fillRect(image.getWidth()/6, 100, image.getWidth()*4/6, image.getHeight()/2);
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread(this);
        if ("Start".equals(e.getActionCommand())) {
            thread.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        if ("Stop".equals(e.getActionCommand())) {
            thread.interrupt();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
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
