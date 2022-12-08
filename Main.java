import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.io.*;

public class Main
{
   public static boolean bgCheck = true;
   public static TreeMap<String, ArrayList<Character>> code = new TreeMap<>();
   public static void main(String[] Args) throws FileNotFoundException
   {
      //Scanner sc = new Scanner(System.in);
      FileOutputStream f = new FileOutputStream("Output.txt");
      System.setOut(new PrintStream(f));
      String num1,Expression;
      int L;
      num1 = JOptionPane.showInputDialog("TYPE YOUR CODE");
      System.out.println();
      Expression = num1;
      L = Expression.length();
      System.out.println("INPUT:");
      System.out.println(Expression);
      System.out.println();
      Tokenizer(Expression,L);
      int count =Balancedbrackets(Expression,L);
      System.out.println();
            System.out.println("------Checking for Balanced Brackets------");
            if (count== 0)
            {
               System.out.println("No brackets Found");
               parser(Expression);
            }
            else if (count % 2 == 0)
            {
               System.out.println("Number of Brackets are Balanced ");
               parser(Expression);
            }
            else
            {
               System.out.println("Number of Brackets are Not Balanced ");
               System.out.println("So Abstract Syntax Tree cannot be Created");
            }
      System.out.println();
      BinaryCode(Expression);

   }//end of main


   public static void Tokenizer(String Expression,int L)
      {
         int i;
         System.out.println("------Breaking your code into Tokens------");
         System.out.println();
         char C='\0';
         System.out.print("Token   ");
         System.out.println("Type");
         for (i = 0; i < L; i++)
         {
            C = Expression.charAt(i);
            if (Character.isDigit(C))
            {
               System.out.print(" "+C+"     ");
               System.out.println("Integer");

            }
            else if ( (C >= 'a' && C <= 'z') || (C >= 'A' && C <= 'Z'))
            {
               System.out.print(" "+C+"     ");
               System.out.println("Alphabet");
            }
            else if (C == '+' || C == '-' || C == '*' || C == '/' || C == '!' || C == '$' || C == '&' || C == '#')
            {
               System.out.print(" "+C+"     ");
               System.out.println("Operator");
            }
            else if (C == '(' || C == ')' || C == '{' || C == '}' || C == '[' || C == ']')
            {
               System.out.print(" "+C+"     ");
               System.out.println("Parentheses");
            }
            else
            {
               System.out.print(" "+C+"     ");
               System.out.println("Invalid Character");
            }
         }
      }//end of method Tokenizer


   public static void BinaryCode(String Expression)
   {
      StringBuilder sb = new StringBuilder();
      char[] chars = Expression.toCharArray();
      System.out.println("------Binary Code------");
      for (char c : chars)
      {
         String binary = Integer.toBinaryString(c);
         String formatted = String.format("%8s", binary);
         String output = formatted.replaceAll(" ", "0");
         System.out.print(output + " ");
      }
   }//end of BinaryCode

   public static int Balancedbrackets(String Expression,int L)
   {
      char C='\0';
      int count=0,i;
      for (i = 0; i < L; i++)
      {
         C = Expression.charAt(i);
         if (C == '(' || C == ')' || C == '{' || C == '}' || C == '[' || C == ']')
            count++;
      }
      return count;
   }//end of BalancedBrackets


   public static String checkParam(Character A)
   {
      Character[] num = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

      Character[] alp = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
              'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
              'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

      Character[] ope = new Character[]{'+', '-', '*', '/'};

      if(A.equals('(') || A.equals(')')){
         return "paren";
      }
      else if((Arrays.stream(num).toList()).contains(A)){
         return "number";
      }
      else if((Arrays.stream(alp).toList()).contains(A)){
         return "literal";
      }
      else if((Arrays.stream(ope).toList()).contains(A)){
         return "operator";
      }
      else{
         return "other";
      }
   }

   public static void parser(String Expression){
      try{

         char[] inputChar = Expression.toCharArray();

         Stack<String> stacks = new Stack<>();

         Character[] operators = new Character[]{'+', '-', '*', '/'};
         Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

         ArrayList<Character> store = new ArrayList<>();
         int storeCursor = 0;

         int checker = 0;
         for(int i = 0; i < Expression.length(); i++){

            if((Arrays.stream(operators).toList()).contains(inputChar[i])){
               store.add(inputChar[i]);
               storeCursor++;
            }

            else if(inputChar[i] == '('){
               stacks.push(Character.toString(inputChar[i]));
               if (i == 0) {
               }
               else if ((Arrays.stream(operators).toList()).contains(inputChar[i-1])) {
                  checker++;
               }

            }

            else if(inputChar[i] == ')'){
               stacks.push(Character.toString(inputChar[i]));
               if(i == inputChar.length - 1){
               }
               else if ((Arrays.stream(operators).toList()).contains(inputChar[i+1]) ){
                  checker++;
               }

               if(checker != 0 && checker % 2 == 0 && !store.isEmpty() ){
                  stacks.push(Character.toString(store.get(storeCursor-1)));
                  store.remove(storeCursor-1);
                  storeCursor--;
                  checker--;
               }
            }
            else{
               if(i == inputChar.length - 1){
                  stacks.push(Character.toString(inputChar[i]));
               }
               else if((Arrays.stream(numbers).toList()).contains(inputChar[i+1])){
                  while((Arrays.stream(numbers).toList()).contains(inputChar[i+1])) {
                     String insert = inputChar[i] + Character.toString(inputChar[i+1]);
                     stacks.push(insert);
                     i++;
                  }
                  checker++;
               }
               else {
                  stacks.push(Character.toString(inputChar[i]));
                  checker++;
               }


               if(checker != 0 && checker % 2 == 0){
                  stacks.push(Character.toString(store.get(storeCursor-1)));
                  store.remove(storeCursor-1);
                  storeCursor--;
                  checker--;
               }
            }

         }
         if(!store.isEmpty()){
            stacks.push(Character.toString(store.get(storeCursor-1)));
         }


         System.out.println("\nAbstract Syntax Tree for '" + Expression + "': ");
         System.out.println("{\n" +
                 "\ttype: 'Program',\n" +
                 "\tbody: [{\n");

         Iterator<String> stackIterator = stacks.iterator();

         String tab = "\t\t";

         while(stackIterator.hasNext()){
            String s = stackIterator.next();

            if(checkParam(s.charAt(0)).equals("number")){
               System.out.println(tab + "type: '" + checkParam(s.charAt(0)) + "'" + "\n" + tab + "Value: '" + s + "'");
            }
            else if(checkParam(s.charAt(0)).equals("operator")){
               System.out.print(tab + "type: '" + checkParam(s.charAt(0)) + "'" + "\n" + tab);
               switch (s) {
                  case "+" -> s = "add";
                  case "-" -> s = "subtract";
                  case "*" -> s = "multiply";
                  case "/" -> s = "divide";
               }
               System.out.println("Operation: '" + s + "'");
            }
            else if(checkParam(s.charAt(0)).equals("paren") && s.charAt(0) == '('){
               System.out.println(tab + "{\n");
               tab = tab + "\t";
            }
            else if(checkParam(s.charAt(0)).equals("paren") && s.charAt(0) == ')'){
               tab = tab.substring(1);
               System.out.println(tab + "}\n");
            }

         }
         tab = tab.substring(1);
         System.out.println(tab + "}]\n}");
      }
      catch (Exception e){
         System.out.println("\n ------------------ ");
         System.out.println("| ERROR DETECTED!! |");
         System.out.println(" ------------------ \n");

         System.out.println("\n\n==>FAILED TO COMPILE.<==");
         boolean bgCheck = false;
      }
   }
}//end of class
