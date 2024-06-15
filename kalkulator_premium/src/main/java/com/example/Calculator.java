package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Calculator {
    private JFrame frame;
    private JTextField display;
    private JTextField display2;
    private JPanel panel;
    private double number1 = 0;
    private String operation = "";
    private double number2 = 0;
    private double result = 0;

    public Calculator() {
        // Tworzymy główne okno aplikacji
        frame = new JFrame("Kalkulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 500);

        // Tworzymy pole tekstowe do wyświetlania wyniku
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 48));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(400, 100));
        display.setBackground(Color.WHITE);

        display2 = new JTextField();
        display2.setEditable(false);
        display2.setFont(new Font("Arial", Font.PLAIN, 12));
        display2.setHorizontalAlignment(JTextField.LEFT);
        display2.setPreferredSize(new Dimension(100, 50));
 

        // Tworzymy główny panel z układem BorderLayout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Dodajemy marginesy

        // Dodajemy pole tekstowe do górnej części panelu
        
        panel.add(display, BorderLayout.NORTH);

        // Tworzymy panel z przyciskami z układem GridBagLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Funkcja do dodawania przycisków do panelu
        addButton(buttonPanel, gbc, "%", 0, 0);
        addButton(buttonPanel, gbc, "Save", 1, 0);
        addButton(buttonPanel, gbc, "Mem", 2, 0);
        addButton(buttonPanel, gbc, "C", 3, 0);
        addButton(buttonPanel, gbc, "\u2190", 4, 0); // Znak strzałki w lewo

        addButton(buttonPanel, gbc, "7", 0, 1);
        addButton(buttonPanel, gbc, "8", 1, 1);
        addButton(buttonPanel, gbc, "9", 2, 1);
        addButton(buttonPanel, gbc, "+", 3, 1);
        addButton(buttonPanel, gbc, "*", 4, 1);

        addButton(buttonPanel, gbc, "4", 0, 2);
        addButton(buttonPanel, gbc, "5", 1, 2);
        addButton(buttonPanel, gbc, "6", 2, 2);
        addButton(buttonPanel, gbc, "-", 3, 2);
        addButton(buttonPanel, gbc, "/", 4, 2);

        addButton(buttonPanel, gbc, "1", 0, 3);
        addButton(buttonPanel, gbc, "2", 1, 3);
        addButton(buttonPanel, gbc, "3", 2, 3);
        addButton(buttonPanel, gbc, "=", 3, 3, 1, 2); // Zajmuje dwa wiersze
        addButton(buttonPanel, gbc, "^", 4, 3); 

        addButton(buttonPanel, gbc, "0", 0, 4, 2, 1); // Zajmuje dwie kolumny
        addButton(buttonPanel, gbc, ".", 2, 4);
        addButton(buttonPanel, gbc, "\u221A", 4, 4); // Znak pierwiastka

        // Dodajemy panel z przyciskami do centralnej części panelu
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Dodajemy główny panel do okna
        frame.add(panel);

        // Ustawiamy widoczność okna
        frame.setVisible(true);

        // Dodajemy ActionListener do każdego przycisku
        Component[] components = buttonPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (button.getText().matches("[0-9.,]")) {
                            String buttonText = button.getText();
                            display.setText(display.getText() + buttonText);
                        }
                        else if (button.getText().matches("[+,\\-,\\*,/,%,\u221A, ^]")) {
                            operation = button.getText();
                            if(operation.length() >= 1){
                                number1 = Double.parseDouble(display.getText());
                                display.setText("");
                            }
                        } else if (button.getText().equals("=")) {
                            number2 = Double.parseDouble(display.getText());
                            if(display.getText().length() != 0) {
                                switch (operation) {
                                    case "+":
                                        result = number1 + number2;
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "-":
                                        result = number1 - number2;
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "*":
                                        result = number1 * number2;
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "/":
                                        if(number2 == 0){
                                            display.setText("Nie dziel przez 0!");
                                            break;
                                        }
                                        result = number1 / number2;
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "%":
                                        result = number2 * (number1/100);
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "\u221A":
                                        result = Math.pow(number1, 1/number2); // tutaj niestety jest problem z pierwiastkowaniem
                                        display.setText(String.valueOf(result));
                                        break;
                                    case "^":
                                        result = Math.pow(number1, number2);
                                        display.setText(String.valueOf(result));
                                        break;
                                    default:
                                        break;
                                }
                                number1 = 0;
                                number2 = 0;
                                operation = "";
                            }
                        } else if (button.getText().equals("C")) {
                            display.setText("");
                        } else if (button.getText().equals("\u2190")) {
                            String currentText = display.getText();
                            if (!currentText.isEmpty()) {
                                display.setText(currentText.substring(0, currentText.length() - 1));
                            }
                        } else {
                            // Handle other button actions here
                        }
                    }
                });
            }
        }
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyChar()) {
                    case '0':
                        simulateButtonClick("0");
                        break;
                    case '1':
                        simulateButtonClick("1");
                        break;
                    case '2':
                        simulateButtonClick("2");
                        break;
                    case '3':
                        simulateButtonClick("3");
                        break;
                    case '4':
                        simulateButtonClick("4");
                        break;
                    case '5':
                        simulateButtonClick("5");
                        break;
                    case '6':
                        simulateButtonClick("6");
                        break;
                    case '7':
                        simulateButtonClick("7");
                        break;
                    case '8':
                        simulateButtonClick("8");
                        break;
                    case '9':
                        simulateButtonClick("9");
                        break;
                    case '.':
                        simulateButtonClick(".");
                        break;
                    case '+':
                        simulateButtonClick("+");
                        break;
                    case '-':
                        simulateButtonClick("-");
                        break;
                    case '*':
                        simulateButtonClick("*");
                        break;
                    case '/':
                        simulateButtonClick("/");
                        break;
                    case '%':
                        simulateButtonClick("%");
                        break;
                    case '=':
                        simulateButtonClick("=");
                        break;
                    case '\b': // Backspace
                        simulateButtonClick("\u2190");
                        break;
                    case '\u221A': // Square root
                        simulateButtonClick("\u221A");
                        break;
                    case '^':
                        simulateButtonClick("^");
                        break;
                    default:
                        break;
                }
            }
        });

        // Set JFrame to focusable to receive key events
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        // Initialize buttons and their action listeners as before
    }
    private void simulateButtonClick(String buttonText) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(buttonText)) {
                    button.doClick();
                    return;
                }
            }
        }
    }
    

    private void addButton(JPanel panel, GridBagConstraints gbc, String text, int x, int y) {
        addButton(panel, gbc, text, x, y, 1, 1);
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, String text, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;

        gbc.gridwidth = width;
        gbc.gridheight = height;
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(button, gbc);
    }


}
