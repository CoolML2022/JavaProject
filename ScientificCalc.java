
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ScientificCalc {
    final static private String[] Operators = new String[]{"+", "-", "*", "/", "^", "!"};
    public Double Findsolution(String inputString){
        String[] bracketStrings;
        Double[] bracketvalues;
        bracketStrings = SeperateBrackets(inputString);
        bracketvalues = new Double[bracketStrings.length];    
        return SolveEquation(bracketStrings, bracketvalues);
    }
    /*
     * General principal:
     * 1.   Identifying the brackets and thier degree of beeing nested in the overall equation
     * 2.0. Calculating eqach bracket and using a pointer in the higher nested equation which will then be replaced with the solution of the deeper nested equation
     * 2.1. calculation the equation inside a bracket pair by following the general mathematical rules: Power -> Multiplication -> Addition
     * 3.   Returning the solution of the least nested equation
     */
    public String[] SeperateBrackets(String inputString){
        String[] Strings_to_resolve;
        //Adding a 1 after the factorial to have a consisten structure of the array to identify the operators and the corresponding numbers
        inputString = inputString.replace("!", "!1");
        //if there are no brackets skip the bracket-solver
        if(!inputString.contains("(")&&!inputString.contains(")")){
            Strings_to_resolve = new String[1];
            Strings_to_resolve[0] = inputString;
            return Strings_to_resolve;
        }                
        char[] inputChar = inputString.toCharArray();        
        //checking for any obvoious mistakes by counting the open and closed brackets -> Also used to define the depth of the brackets
        int numOfBracketsOpen = 0;
        int numOfBracketsClose = 0;
        Print("InputString: "+ inputString);
        for(int i = 0; i < inputChar.length; i++){
            if("(".equals(String.valueOf(inputChar[i])))
                numOfBracketsOpen++;
            else if(")".equals(String.valueOf(inputChar[i])))
                numOfBracketsClose++;    
        }
        Strings_to_resolve = new String[numOfBracketsClose+1];
        //To do: adding a correct error message
        if(numOfBracketsClose != numOfBracketsOpen){
            return Strings_to_resolve;
        }
        //Rewriting the square root operator to (X)^0.5 and nested square roots: e.g. √(√(36))  ->  ((36)^0.5^0.5)
        boolean root = false;
        String rootvalue = "";
        String rootreplacement;
        for(int i = 0; i < inputChar.length; i++){
            if("√".equals(String.valueOf(inputChar[i]))){
                root = true;   
                rootvalue = "";
            } 
            if(root == true){
                rootvalue += String.valueOf(inputChar[i]);
                if(")".equals(String.valueOf(inputChar[i]))){
                    rootreplacement = rootvalue.replaceFirst("√", "");
                    inputString = inputString.replace(rootvalue, rootreplacement + "^0.5");
                    rootvalue = "";
                    inputChar = inputString.toCharArray();
                    i = -1; //restarting the loop to begin at index i = 0. In case there are nested roots
                    root = false;                    
                }
            }

        }
        
        /*
         * Adds a multiplication sign bewteen a number and an open bracket: e.g 45+4(3-5) -> 45+4*(3-5)
         * Also between two brackets: e.g.  )(   ->   )*(
         */
        for(int i = 0; i<10; i++){
            inputString = inputString.replace(i+"(", i+"*(");
        }
        inputString = inputString.replace(")(", ")*1*(");
        /*
         * First String in bracketString is the most nested bracket equation -> no pointers : e.g. "?0?"
         * Initial String: (3536*(46547+356)-4)
         * [0] 46547+356
         * [1] (3536*?0?-4)
         * [2] ?1?
         * The last element in the bracketvalues is the final solution expressed as an pointer (in case there are no brackets)
         */
        for(int j = 0; j < numOfBracketsClose; j++){
            String newstring = "";
            boolean openBracket = false;
            inputChar = inputString.toCharArray();
            for(int i = 0; i< inputString.toCharArray().length; i++){
                if("(".equals(String.valueOf(inputChar[i]))){
                    openBracket = true;
                    newstring = "";                
                }
                newstring += String.valueOf(inputChar[i]);
                if(")".equals(String.valueOf(inputChar[i])) && openBracket == true){
                    Strings_to_resolve[j] = newstring;
                    inputString = inputString.replaceFirst(Pattern.quote(newstring), "?"+j+"?");
                    break;
                }
            }
        }
        Strings_to_resolve[numOfBracketsClose] = inputString;
        return Strings_to_resolve;
    }

    private Double SolveEquation(String[] bracketString, Double[] bracketvalues){
        //preparing the string for calculating by removing all brackets and adding the value of the pointer (bracketvalues), which was calculated prior
        for(int y = 0; y < bracketString.length; y++){
            String equation = bracketString[y];
            Print(equation);
            equation = equation.replace("(", "");
            equation = equation.replace(")", "");
            List<Double> numbers = new ArrayList<>();
            List<String> operators = new ArrayList<>();
            char[] equationChar = equation.toCharArray();
            String value = "";            
            for(int i = 0; i<equationChar.length; i++){
                if(Arrays.asList(Operators).contains(String.valueOf(equationChar[i]))){
                    numbers.add(Double.valueOf(value));
                    operators.add(String.valueOf(equationChar[i]));
                    value = "";
                }
                else if("?".equals(String.valueOf(equationChar[i]))){                    
                    value = String.valueOf(bracketvalues[Integer.parseInt(String.valueOf(equationChar[i+1]))]);
                    //Grabs the index of the value from the nested bracket solution e.g. ?0? -> prior calculated from deepest nested brackets
                    i = i+2;
                }
                else
                    value+=String.valueOf(equationChar[i]);
            }
            numbers.add(Double.valueOf(value));
            bracketvalues[y] = Calculate(numbers, operators);
        }
        return bracketvalues[bracketvalues.length-1];
    }
    /*
     * Calculates the single bracketvalues starting from the most nested one
     * therefore checking for powers and factorial, then multiplication and last addition
     */
    public Double Calculate(List<Double> numbers, List<String> operators){
        CalcData calcData = new CalcData();
        calcData.numbers = numbers;
        calcData.operators = operators;

        calcData = RecursivePower(calcData);
        calcData = RecursiveMultiplication(calcData);
        calcData = RecursiveAddition(calcData);
        return calcData.numbers.get(0);
    }
    //going over the operators and recursivly solve all multiplication
    public  CalcData RecursiveMultiplication(CalcData calcData){
        for(int i = 0; i < calcData.operators.size(); i++){            
            if("*".equals(calcData.operators.get(i))) {                
                calcData.numbers.set(i, calcData.numbers.get(i) * calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RecursiveMultiplication(calcData);
            }
            else if("/".equals(calcData.operators.get(i))){
                calcData.numbers.set(i, calcData.numbers.get(i) / calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RecursiveMultiplication(calcData);
            }
        }
        return calcData;
    }
    //going over the operators and recursivly solve all addition equations
    public  CalcData RecursiveAddition(CalcData calcData){
        for(int i = 0; i < calcData.operators.size(); i++){
            if("+".equals(calcData.operators.get(i))) {                
                calcData.numbers.set(i, calcData.numbers.get(i) + calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RecursiveAddition(calcData);
            }
            else if("-".equals(calcData.operators.get(i))){
                calcData.numbers.set(i, calcData.numbers.get(i) - calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RecursiveAddition(calcData);
            }
        }
        return calcData;
    }
    //going over the operators and recursivly solve all power and factorial equations
    public CalcData RecursivePower(CalcData calcData){
        for(int i = 0; i<calcData.operators.size(); i++){ 
            if("^".equals(calcData.operators.get(i))){                          
                calcData.numbers.set(i, Math.pow(calcData.numbers.get(i), calcData.numbers.remove(i+1)));
                calcData.operators.remove(i);
                RecursivePower(calcData);
            }     
            else if("!".equals(calcData.operators.get(i))){
                double factorial = calcData.numbers.get(i);
                double value = 1;
                if(calcData.numbers.get(i)== 0){
                    calcData.numbers.set(i, value);
                }
                else{
                    for(int j = (int)factorial; j > 0; j--){
                        value = value* j;
                    }
                    calcData.numbers.set(i, value);
                    calcData.numbers.remove(i+1);
                }
                calcData.operators.remove(i);
            }
            
        }
        return calcData;
    }
    public  class CalcData{
        List<Double> numbers;
        List<String> operators;
    };

    /*
     * 
     * 
     * Just for debugging... (simpler syntax)
     * 
     * 
     */
    public static void Print(Object e ){
        System.out.println(String.valueOf(e));
    }

}
