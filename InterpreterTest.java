import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterpreterTest {
    public void main(String[] args) {
        String test = Interpreter("    ");
    }
    final private String[] Operators = new String[]{"+", "-", "*", "/"};
    List<String> operators = new ArrayList<String>();
    List<Float> numbers = new ArrayList<Float>();   
    //Interprets the final String to find the solution of the given math equation 
    public String Interpreter (String mainString){
        String solution = "";
        //Example 3535+36*3535-324/46

        String test = "3535+36*3535-324/46";
        test += "+"; //initiate placeholder removed later ---->
        char[] testCharaters = test.toCharArray();
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
}
