package tdd;

public class MathOperations {
    public static int factorial(int value){
        if (value == 0) return 1;
        if(value < 0) return 0;
        int result = value;
        int factor = value-1;
        while (factor>1){
            result *= factor--;
        }
        return result;
    }

    public static int multiply(int a, int b){
        return a*b;
    }
    public static int devide(int a, int b){
        return a/b;
    }
}
