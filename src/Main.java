import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static List <Integer> arabic = new ArrayList<>();
    static List <String> roman = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }

    public static String calc(String input) throws IOException {
        Collections.addAll(arabic, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 40, 50, 90, 100);
        Collections.addAll(roman, "I", "II", "III", "IV", "V", "VI", "VII",
                "VIII", "IX", "X", "XL", "L", "XC", "C");


        String inputValue = input;
        inputValue = inputValue.replaceAll("\\s", "");
        
        isValidRoman(inputValue);
        isOperationInvalid(inputValue);
        isInteger(inputValue);
        isRomanInRange(inputValue);
        isOneType(inputValue);

        String operation = isOperation(inputValue);
        String[] numMatrix = inputValue.split("[+-/*]");

        Pattern pattern = Pattern.compile("[IVX]");
        Matcher matcher = pattern.matcher(inputValue);
        if (matcher.find()) {
            int num1 = convertToArabic(numMatrix[0]);
            int num2 = convertToArabic(numMatrix[1]);

            int result = calculation(num1, num2, operation);

            if(result <= 0){
                throw new IOException("т.к. римские числа не могут быть меньше I");
            }

            return (convertToRoman(result));
        } else {
            int num1 = Integer.parseInt(numMatrix[0]);
            int num2 = Integer.parseInt(numMatrix[1]);

            if (num1 > 10 || num1 < 1 || num2 > 10 || num2 <1) {
                throw new IOException("т.к. числа на ввод должны быть от 1 до 10 включительно");
            }

            return calculation(num1, num2, operation).toString();
        }
    }

    public static Integer calculation(int num1, int num2, String operation) {
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "/" -> num1 / num2;
            case "*" -> num1 * num2;
            default -> null;
        };
    }

    public static Integer convertToArabic(String string){

        return  arabic.get(roman.indexOf(string));
    }

    public static String convertToRoman(int number) {
        StringBuilder result = new StringBuilder();
        int[] arabicNumbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        int index;

        for (int i : arabicNumbers) {
            while (number >= i) {
                index = arabic.indexOf(i);
                result.append(roman.get(index));
                number -= i;
            }
        }

        return result.toString();
    }

    public static String isOperation(String string) throws IOException {
        Pattern pattern = Pattern.compile("[+\\-/*]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new IOException("т.к. строка не является математической операцией");
        }
    }

    public static void isInteger(String string) throws IOException{
        Pattern pattern = Pattern.compile("[,.]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            throw new IOException("т.к. калькулятор умеет работать только с целыми числами");
        }
    }

    public static void isValidRoman(String string) throws IOException{
        Pattern pattern = Pattern.compile("[ivxlc]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            throw new IOException("т.к. римские числа должны записываться заглавными латинскими буквами");
        }
    }

    public static void isRomanInRange(String string) throws IOException{
        Pattern pattern = Pattern.compile("(X+[IVXLC])|[LC]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            throw new IOException("т.к. числа на ввод должны быть от I до X включительно");
        }
    }

    public static void isOneType(String string) throws IOException{
        Pattern pattern = Pattern.compile("([IVX][+-/*][\\d])|([\\d][+-/*][IVX])");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            throw new IOException("т.к. используются одновременно разные системы счисления");
        }
    }

    public static void isOperationInvalid(String string) throws IOException{
        int x = string.length();
        int y = string.replaceAll("[+\\-/*]", "").length();

        if(x-y > 1){
            throw new IOException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
    }
}