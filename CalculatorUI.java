import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorUI extends JFrame{

    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    final private String[] Operators = new String[]{"(", ")", ".", "+", "-", "*", "/", "^","√", "!"};
    JTextField inputTextField, outputTextField;    
    private String mainString;    
    public void Initialize(){
        mainString = null;
        //*********** Screen Panel ************//
        JLabel outputJLabel = new JLabel("Result");
        outputJLabel.setFont(mainFont);
        inputTextField = new JTextField();
        inputTextField.setFont(mainFont);        
        outputTextField = new JTextField();
        outputJLabel.setFont(mainFont);
        outputJLabel.setFont(mainFont);
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridLayout(3, 1, 5, 5));
        screenPanel.add(inputTextField);
        screenPanel.add(outputJLabel);
        screenPanel.add(outputTextField);
        //********** Button Panel **********//
        /*
         * Number Buttons
         */
        JButton[] numberButtons = new JButton[10];
        for(int i = 0; i < numberButtons.length; i++){
            String value = String.valueOf(i);
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(mainFont);    
            numberButtons[i].addActionListener((ActionEvent e) -> {
                mainString = inputTextField.getText();
                mainString = mainString + value;
                inputTextField.setText(mainString);
            });
        }
        /* ************** Operator Buttons ************** */
        JButton[] operatorButtons = new JButton[Operators.length];
        for(int i = 0; i < Operators.length; i++){
            String operator = Operators[i];
            operatorButtons[i] = new JButton(Operators[i]);
            operatorButtons[i].setFont(mainFont);
            operatorButtons[i].addActionListener((ActionEvent e) -> {
                char[] inputField = inputTextField.getText().toCharArray();
                System.out.println(mainString == null);
                if(mainString == null){
                    mainString = inputTextField.getText();
                    mainString += operator;
                    if("√".equals(operator)){
                        mainString+= "(";
                    }
                    
                    inputTextField.setText(mainString);
                }          
                else if(!Arrays.asList(Operators).contains(inputField[inputField.length-1])){
                    mainString = inputTextField.getText();
                    mainString += operator;
                    if("√".equals(operator)){
                        mainString+= "(";
                    }
                    inputTextField.setText(mainString);
                }            
            });

        }
        /* *******************Equals Button******************* */
        JButton equalsButton = new JButton("=");
        equalsButton.setFont(mainFont);
        equalsButton.setBackground(new java.awt.Color(255, 0 ,0));
        //equalsButton.setBackground(new Color(107, 155, 192, 75));
        equalsButton.addActionListener((ActionEvent e) -> {
            mainString = inputTextField.getText();
            mainString = mainString.replaceAll("\\s+","");
            inputTextField.setText(mainString);
            ScientificCalc calc = new ScientificCalc();
            outputTextField.setText(String.valueOf(calc.Findsolution(mainString)));            
        });
        equalsButton.setOpaque(true);
        /* ******************Clear Button********************* */
        JButton clearButton = new JButton("AC");
        clearButton.setFont(mainFont);
        clearButton.addActionListener((ActionEvent e) -> {
            mainString = null;
            inputTextField.setText(mainString);
            outputTextField.setText(" ");            
        });
        /* ************ Button Panel Initialize ************* */
        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new GridLayout(6, 3, 5 ,5));
        buttonsJPanel.setBackground(new Color(107, 155, 192, 75));
        for (JButton numberButton : numberButtons) {
            buttonsJPanel.add(numberButton);
        }                
        for (JButton operatorButton : operatorButtons) {
            buttonsJPanel.add(operatorButton);
        }
        buttonsJPanel.add(equalsButton);
        buttonsJPanel.add(clearButton);
        /* ***************** Main Panel Initialize *************** */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(91, 173, 235, 92));
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsJPanel, BorderLayout.CENTER);        
        add(mainPanel);
        setTitle("Calculator");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setVisible(true);
    }
    public static void main(String[] args) {
        CalculatorUI calculatorUI = new CalculatorUI();
        calculatorUI.Initialize();
    }
}

