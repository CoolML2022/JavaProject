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
    final private String[] Operators = new String[]{".","+", "-", "*", "/", "(", ")", "^","√", "!"};
    final private String[] Numbers = new String[]{"7", "8", "9",
                                                  "4", "5", "6", 
                                                  "1", "2", "3",
                                                  "0", "=", "AC"};
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
        JButton[] numberButtons = new JButton[Numbers.length];
        for(int i = 0; i < numberButtons.length; i++){
            String value = Numbers[i];            
            numberButtons[i] = new JButton(Numbers[i]);
            numberButtons[i].setFont(mainFont);    
            int index = i;
            numberButtons[i].addActionListener((ActionEvent e) -> {
                /* *******************Equals Button******************* */
                if(Numbers[index].equals("=")){
                    mainString = inputTextField.getText();
                    mainString = mainString.replaceAll("\\s+","");
                    inputTextField.setText(mainString);
                    ScientificCalc calc = new ScientificCalc();
                    outputTextField.setText(String.valueOf(calc.Findsolution(mainString))); 
                }
                /* ******************Clear Button********************* */
                else if(Numbers[index].equals("AC")){
                    mainString = null;
                    inputTextField.setText(mainString);
                    outputTextField.setText("");   
                }
                else{
                    mainString = inputTextField.getText();
                    mainString = mainString + value;
                    inputTextField.setText(mainString);
                }

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
        
        /* ************ Button Panel Initialize ************* */
        JPanel operatorButtonsJPanel = new JPanel();
        JPanel numberButtonsJPanel = new JPanel();
        numberButtonsJPanel.setLayout(new GridLayout(4, 3, 5, 5));
        operatorButtonsJPanel.setLayout(new GridLayout(2, 3, 5 ,5));
        //operatorButtonsJPanel.setBackground(new Color(107, 155, 192, 75));
        for (JButton numberButton : numberButtons) {
            numberButton.setOpaque(true);
            //numberButton.setBackground(Color.BLACK);
            //numberButton.setForeground(Color.RED);
            numberButtonsJPanel.add(numberButton);
        }          
        for (JButton operatorButton : operatorButtons) {
            operatorButtonsJPanel.add(operatorButton);
        }
        JPanel ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new GridLayout(2, 1, 5 ,5));
        ButtonsPanel.add(numberButtonsJPanel);
        ButtonsPanel.add(operatorButtonsJPanel);
        /* ***************** Main Panel Initialize *************** */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(91, 173, 235, 92));
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(ButtonsPanel, BorderLayout.CENTER);       
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

