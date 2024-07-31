import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class CalculatorUI extends JFrame{

    final private Font maiFont = new Font("Arial", Font.BOLD, 18);
    final private String[] Operators = new String[]{"+", "-", "*", "/"};
    JTextField inputTextField, outputTextField;
    private String mainString;    
    public void Initialize(){

        //*********** Screen Panel ************//
        JLabel outputJLabel = new JLabel("Result");
        outputJLabel.setFont(maiFont);
        inputTextField = new JTextField();
        inputTextField.setFont(maiFont);        
        outputTextField = new JTextField();
        outputJLabel.setFont(maiFont);
        outputJLabel.setFont(maiFont);
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridLayout(3, 1, 5, 5));
        screenPanel.add(inputTextField);
        screenPanel.add(outputJLabel);
        screenPanel.add(outputTextField);

        //********** Button Panel **********//
        JButton[] numberButtons = new JButton[10];
        for(int i = 0; i < numberButtons.length; i++){
            String value = String.valueOf(i);
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(maiFont);
            numberButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {   
                    mainString = inputTextField.getText();
                    mainString = mainString + value;                   
                    inputTextField.setText(mainString);                    
                }
            });
        }
        JButton[] operatorButtons = new JButton[Operators.length];
        for(int i = 0; i < Operators.length; i++){
            String operator = Operators[i];
            operatorButtons[i] = new JButton(Operators[i]);
            operatorButtons[i].setFont(maiFont);
            operatorButtons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    char[] inputField = inputTextField.getText().toCharArray();
                    if(!Arrays.asList(Operators).contains(inputField[inputField.length-1])){
                        mainString = inputTextField.getText();
                        mainString += operator;
                        inputTextField.setText(mainString);                        
                    }
                }            
            });
        }
        /* *******************Equals Button******************* */
        JButton equalsButton = new JButton("=");
        equalsButton.setFont(maiFont);
        equalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainString = inputTextField.getText();
                outputTextField.setText(Interpreter(mainString));                
            }            
        });
        /* ******************Clear Button********************* */
        JButton clearButton = new JButton("AC");
        clearButton.setFont(maiFont);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                mainString = "";
                inputTextField.setText(mainString);
                outputTextField.setText(" ");
            }            
        });
        /*                                                     */
        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new GridLayout(4, 3, 5 ,5));
        for (JButton numberButton : numberButtons) {
            buttonsJPanel.add(numberButton);
        }
        buttonsJPanel.add(equalsButton);
        buttonsJPanel.add(clearButton);
        for (JButton operatorButton : operatorButtons) {
            buttonsJPanel.add(operatorButton);
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsJPanel, BorderLayout.CENTER);        
        add(mainPanel);
        setTitle("Calculator");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setVisible(true);
    }
    //Interprets the final String to find the solution of the given math equation 
    List<String> operators;
    List<Float> numbers;
    public String Interpreter (String mainString){
        operators = new ArrayList<String>();
        numbers = new ArrayList<Float>();   
        mainString += "+"; //initiate placeholder removed later ---->
        char[] testCharaters = mainString.toCharArray();
        String cont = "";
        for(int i = 0; i < testCharaters.length; i++){
            if(!Arrays.asList(Operators).contains(String.valueOf(testCharaters[i]))){
                cont += String.valueOf(testCharaters[i]);
            }
            else{
                numbers.add(Float.valueOf(cont));
                cont = "";
                operators.add(String.valueOf(testCharaters[i]));
            }
        }
        operators.remove(operators.size()-1); //removing last operator bc placeholder
        RekursiveMultiplikation();
        RekursiveAddition();        
        if(numbers.size()>1)
            System.err.print("Something went wrong... unkown symbol");
        System.out.println(numbers);
        return String.valueOf(numbers.get(0));
    }

    public void RekursiveMultiplikation (){
        for(int i = 0; i < operators.size(); i++){
            if("*".equals(operators.get(i))) {                
                numbers.set(i, numbers.get(i) * numbers.remove(i+1));
                operators.remove(i);
                RekursiveMultiplikation();
            }
            else if("/".equals(operators.get(i))){
                numbers.set(i, numbers.get(i) / numbers.remove(i+1));
                operators.remove(i);
                RekursiveMultiplikation();
            }
        }
    }

    public void RekursiveAddition(){
        for(int i = 0; i < operators.size(); i++){
            if("+".equals(operators.get(i))) {                
                numbers.set(i, numbers.get(i) + numbers.remove(i+1));
                operators.remove(i);
                RekursiveAddition();
            }
            else if("-".equals(operators.get(i))){
                numbers.set(i, numbers.get(i) - numbers.remove(i+1));
                operators.remove(i);
                RekursiveAddition();
            }
        }
    }

    public static void main(String[] args) {
        CalculatorUI calculatorUI = new CalculatorUI();
        calculatorUI.Initialize();
    }
}
