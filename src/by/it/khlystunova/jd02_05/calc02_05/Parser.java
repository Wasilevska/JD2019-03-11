package by.it.khlystunova.jd02_05.calc02_05;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {

    private static Map<String,Integer> mapPriority=new HashMap<String,Integer>(){
        {
            this.put("=",0);
            this.put("+",1);
            this.put("-",1);
            this.put("*",2);
            this.put("/",2);
        }
    };

    Var calc(String expression) throws CalcException {
        expression = expression.replace(" ", "");
        List<String> operations=new ArrayList<>();
        List<String> operands= new ArrayList<>(
                  Arrays.asList(expression.split(Patterns.OPERATION))
            );//сплитим по операциям и заносим это в массив операций
        Pattern pattern=Pattern.compile(Patterns.OPERATION);
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find())
            operations.add(matcher.group());//заносим в массив операций найденую операцию.

        while (operations.size()>0) {
            int indexPriorityOperation = getIndexOperation(operations);
            String operation = operations.remove(indexPriorityOperation);
            String leftPart = operands.remove(indexPriorityOperation);
            String rightPart = operands.remove(indexPriorityOperation);
            Var oneOperationResult = oneOperation(leftPart, operation, rightPart);
            operands.add(indexPriorityOperation,oneOperationResult.toString());
        }
        return Var.createVar(operands.get(0));

    }

    private int getIndexOperation(List<String> operations) {
        int priorityValue=-1;
        int index=-1;
        for (int i = 0; i < operations.size(); i++) {
            String op = operations.get(i);
            int current=mapPriority.get(op);
            if (current>priorityValue){
                priorityValue=current;
                index=i;
            }
        }
        return index;//получили индекс самой приоритеной операции
    }

    private Var oneOperation(String strLeftPart, String operation, String strRightPart)
                                                          throws CalcException {
        Var rightPart = Var.createVar(strRightPart);
        if (operation.equals("=")) {
            Var.saveVar(strLeftPart, rightPart);
            return rightPart;
        }
        Var leftPart = Var.createVar(strLeftPart);
        if (leftPart == null || rightPart == null)
            throw new CalcException(ConsoleRunner.manager.getString(Msg.EXEPTION));
        //ok find op
        switch (operation) {
            case "+":
                return leftPart.add(rightPart);
            case "-":
                return leftPart.sub(rightPart);
            case "*":
                return leftPart.mul(rightPart);
            case "/":
                return leftPart.div(rightPart);
            default:
                throw new CalcException(ConsoleRunner.manager.getString(Msg.EXEPTION));
        }

    }




}
