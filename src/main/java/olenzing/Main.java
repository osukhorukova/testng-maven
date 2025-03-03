package olenzing;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Boolean checkNumberIsSquare(int number) {
        if (number < 0){
            throw new IllegalArgumentException("Number should be grater than 0");
        }
        for (int i = 0; i * i <= number; i++) {
            if (i * i == number) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}