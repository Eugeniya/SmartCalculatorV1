package com.example.smartcalculatorv1;

/**
 Класс для вычислений
 */

public class Calculator {

    protected char Operation;				//поле для операции '+', '-', '*', '/'
    private boolean OperationIsChechked;    //поле, определяющее выбрана ли операция
    private boolean IsSolve;			//вычеслено значение, неоходимо для того что бы результатом можно было воспользоваться для последующего вычисления
    private boolean IsSign;				//знак числа
    private boolean IsPersent;			//признак %
    private double operand1;
    private double operand2;
    private double result;
    private boolean FirstDigitAfterOperation;	//первый символ после операции

    public Calculator()
    {
        // TODO Auto-generated constructor stub
        Operation 			= '0';
        OperationIsChechked = false;
        IsSolve 			= false;
        IsSign				= false;
        IsPersent 			= false;
        operand1 			= 0;
        operand2 			= 0;
        result 				= 0;
        FirstDigitAfterOperation = false;
    }

    public boolean getOperationIsChechked()
    {
        return this.OperationIsChechked;
    }

    public void setOperationIsChechked(boolean oper)
    {
        this.OperationIsChechked = oper;
    }

    public boolean getIsSolve()
    {
        return this.IsSolve;
    }

    public void setIsSolve(boolean is)
    {
        this.IsSolve = is;
    }

    public boolean getIsSign()
    {
        return this.IsSign;
    }

    public void setIsSign(boolean is)
    {
        this.IsSign = is;
    }

    public boolean getIsPersent()
    {
        return  this.IsPersent;
    }

    public void setIsPersent(boolean isPersent) {
        IsPersent = isPersent;
    }

    public double getOperand1() {
        return operand1;
    }

    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public boolean getFirstDigitAfterOperation() {
        return FirstDigitAfterOperation;
    }

    public void setFirstDigitAfterOperation(boolean firstDigitAfterOperation) {
        FirstDigitAfterOperation = firstDigitAfterOperation;
    }
}