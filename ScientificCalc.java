
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ScientificCalc {
    public String InputString;
    final private String[] Operators = new String[]{"+", "-", "*", "/", "^"};
    public void main(String[] args) {
        String[] bracketStrings;
        Double[] bracketvalues;
        //String test = "22*3-345*3/2^4-45*5/3";
        bracketStrings = SeperateBrackets(InputString);
        bracketvalues = new Double[bracketStrings.length];
        solveEquation(bracketStrings, bracketvalues);
        
    }
    public Double ScientificCalc(){
        String[] bracketStrings;
        Double[] bracketvalues;
        //String test = "22*3-345*3/2^4-45*5/3";
        bracketStrings = SeperateBrackets(InputString);
        bracketvalues = new Double[bracketStrings.length];
        
        return solveEquation(bracketStrings, bracketvalues);
    }

    public String[] SeperateBrackets(String inputString){
        String[] Strings_to_resolve;
        if(!inputString.contains("(")&&!inputString.contains(")")){
            Strings_to_resolve = new String[1];
            Strings_to_resolve[0] = inputString;
            Print("skipped");
            return Strings_to_resolve;
        }
        char[] inputChar = inputString.toCharArray();
        int numOfBracketsOpen = 0;
        int numOfBracketsClose = 0;
        for(int i = 0; i < inputChar.length; i++){
            if("(".equals(String.valueOf(inputChar[i])))
                numOfBracketsOpen++;
            else if(")".equals(String.valueOf(inputChar[i])))
                numOfBracketsClose++;
        }
        Strings_to_resolve = new String[numOfBracketsClose+1];
        if(numOfBracketsClose != numOfBracketsOpen){
            System.err.printf("Int: %d, String: %s, UpperHex: %X", 23, "Test", 42);
            return Strings_to_resolve;
        }
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
                    inputString = inputString.replace(newstring, "?"+j+"?");
                    break;
                }
            }
        }
        Strings_to_resolve[numOfBracketsClose] = inputString;
        int i = 0;
        for (String s : Strings_to_resolve) {            
            Print("bra_" +i+ "_ " + s);
            i++;
            
        }
        return Strings_to_resolve;
    }

    private Double solveEquation(String[] bracketString, Double[] bracketvalues){
        //Create Numberstring
        /*
         * First String in bracketString is the deepest nested Bracketequation -> no values : e.g. "?0?"
         */
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
        Print("Solution: " + bracketvalues[bracketvalues.length-1]);
        return bracketvalues[bracketvalues.length-1];
    }

    public Double Calculate(List<Double> numbers, List<String> operators){
        CalcData calcData = new CalcData();
        calcData.numbers = numbers;
        calcData.operators = operators;

        calcData = RekursivePower(calcData);
        calcData = RekursiveMultiplikation(calcData);
        calcData = RekursiveAddition(calcData);
        return calcData.numbers.get(0);
    }
    public  CalcData RekursiveMultiplikation(CalcData calcData){
        for(int i = 0; i < calcData.operators.size(); i++){
            if("*".equals(calcData.operators.get(i))) {                
                calcData.numbers.set(i, calcData.numbers.get(i) * calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RekursiveMultiplikation(calcData);
            }
            else if("/".equals(calcData.operators.get(i))){
                calcData.numbers.set(i, calcData.numbers.get(i) / calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RekursiveMultiplikation(calcData);
            }
        }
        return calcData;
    }
    public  CalcData RekursiveAddition(CalcData calcData){
        for(int i = 0; i < calcData.operators.size(); i++){
            if("+".equals(calcData.operators.get(i))) {                
                calcData.numbers.set(i, calcData.numbers.get(i) + calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                calcData = RekursiveAddition(calcData);
            }
            else if("-".equals(calcData.operators.get(i))){
                calcData.numbers.set(i, calcData.numbers.get(i) - calcData.numbers.remove(i+1));
                calcData.operators.remove(i);
                RekursiveAddition(calcData);
            }
        }
        return calcData;
    }
    public CalcData RekursivePower(CalcData calcData){
        for(int i = 0; i<calcData.operators.size(); i++){            
            if("^".equals(calcData.operators.get(i))){                          
                calcData.numbers.set(i, Math.pow(calcData.numbers.get(i), calcData.numbers.remove(i+1)));
                calcData.operators.remove(i);
                RekursivePower(calcData);
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
     * Just for debugging...
     * 
     * 
     */
    public static void Print(Object e ){
        System.out.println(String.valueOf(e));
    }

}
