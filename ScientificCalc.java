
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;



public class ScientificCalc {
    public String InputString;
    final private String[] Operators = new String[]{"+", "-", "*", "/", "^", "!"};
    public Double ScientificCalc(){
        String[] bracketStrings;
        Double[] bracketvalues;
        //String test = "22*3-345*3/2^4-45*5/3";
        bracketStrings = SeperateBrackets(InputString);
        bracketvalues = new Double[bracketStrings.length]; 
        Print("Bracketvalues lenght: " + bracketvalues.length);    
        return solveEquation(bracketStrings, bracketvalues);
    }
    //(352646*567-(356-7))
    public String[] SeperateBrackets(String inputString){
        String[] Strings_to_resolve;
        //
        inputString = inputString.replace("!", "!1");
        if(!inputString.contains("(")&&!inputString.contains(")")){
            Strings_to_resolve = new String[1];
            Strings_to_resolve[0] = inputString;
            Print("skipped");            
            return Strings_to_resolve;
        }                
        //Cleaning up the inputString:
        //Adds a multiplication sign bewteen a number and an open bracket: e.g 45+4(3-5) -> 45+4*(3-5)
        for(int i = 0; i<10; i++){
            inputString = inputString.replace(i+"(", i+"*(");
        }
        inputString = inputString.replace(")(", ")*1*(");

        char[] inputChar = inputString.toCharArray();        
        boolean root = false;
        String rootvalue = "";
        String rootreplacement = "";
        for(int i = 0; i < inputChar.length; i++){
            if("√".equals(String.valueOf(inputChar[i])))
                root = true;    
            if(root == true){
                rootvalue += String.valueOf(inputChar[i]);
                if(")".equals(String.valueOf(inputChar[i]))){
                    rootreplacement = rootvalue.replace("√", "");
                    inputString = inputString.replace(rootvalue, rootreplacement + "^0.5");
                    inputString = inputString.replace("√", "");
                    rootvalue = "";
                    root = false;
                }
            }   
        }
        inputChar = inputString.toCharArray();
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
        if(numOfBracketsClose != numOfBracketsOpen){
            return Strings_to_resolve;
        }

        ///(475645*454646*?0?) -> ?1?
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
                    Print("newString " + newstring + " InputString " + inputString);
                    int count = CountMatches(inputString, newstring);    
                    //int count = 0;
                    Print("InputString: " + inputString);
                    Print("Count: " + count);
                    if(count > 1)
                        inputString = inputString.replaceFirst(Pattern.quote(newstring), "?"+j+"?");
                    else
                        inputString = inputString.replace(newstring, "?"+j+"?");
                    break;
                }
            }
        }
        Strings_to_resolve[numOfBracketsClose] = inputString;
        for(String string: Strings_to_resolve){
            Print(string);
        }
        return Strings_to_resolve;
    }

    private Double solveEquation(String[] bracketString, Double[] bracketvalues){
        //Create Numberstring
        /*
         * First String in bracketString is the deepest nested Bracketequation -> no values : e.g. "?0?"
         * 
         * [0] 46547+356
         * [1] (3536*?0?-4)
         * ?1?
         */

         //38425825*45258746+286-2532
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
            //5! -> 5!1
            numbers.add(Double.valueOf(value));
            bracketvalues[y] = Calculate(numbers, operators);
            Print("Bracketvalue: " + bracketvalues[y]);
        }
        Print("Solution: " + bracketvalues[bracketvalues.length-1]);
        return bracketvalues[bracketvalues.length-1];
    }
    /*
     * 
     * 
     */
    public Double Calculate(List<Double> numbers, List<String> operators){
        CalcData calcData = new CalcData();
        calcData.numbers = numbers;
        calcData.operators = operators;

        calcData = RekursivePower(calcData);
        calcData = RekursiveMultiplikation(calcData);
        calcData = RekursiveAddition(calcData);
        return calcData.numbers.get(0);
    }
    //3353466
    //
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
                RekursiveAddition(calcData);
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
    public int CountMatches(String input, String countString){
        int count = 0;
        while(input.contains(countString)){
            input = input.replaceFirst(Pattern.quote(countString), "");
            Print("Input: " + input);
            count++;
        }
        return count;
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
