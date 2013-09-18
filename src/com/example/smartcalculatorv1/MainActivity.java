package com.example.smartcalculatorv1;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView Result;
	private TextView Log;
	private ScrollView Scroll;
	private Calculator calc;

	//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //при смене ориентации экрана сохраняем данные
		calc = (Calculator) getLastNonConfigurationInstance();
		if(calc == null)
		{
			calc = new Calculator();
		}
				
		// находим элементы
		Result = (TextView) findViewById(R.id.textView_Result);
		Log = (TextView) findViewById(R.id.textView_Log);
		Scroll = (ScrollView) findViewById(R.id.scrollViewForResult);
		
		//Result.setTextIsSelectable(true);
		
		//задаем шрифт
	    Typeface tf_log = Typeface.createFromAsset(this.getAssets(), "fonts/DS-DIGIB.TTF");
	    Typeface tf_result = Typeface.createFromAsset(getAssets(), "fonts/DS-DIGI.TTF");
	    Result.setTypeface(tf_result);
	    Log.setTypeface(tf_log);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	  protected void onSaveInstanceState(Bundle outState) 
	  {
		    super.onSaveInstanceState(outState);
		    outState.putString("resultState", this.Result.getText().toString());
		    outState.putString("logState", this.Log.getText().toString());
	  }
	 
	  @Override
	 protected void onRestoreInstanceState(Bundle savedInstanceState)
	 {
		    super.onRestoreInstanceState(savedInstanceState);
		    this.Result.setText(savedInstanceState.getString("resultState"));
		    this.Log.setText(savedInstanceState.getString("logState"));		    
	 }
	
	 @Override
	public Object onRetainNonConfigurationInstance() 
	{
	   return calc;
	}
	
	//функция обработки нажатия кнопки вычисления результата
	public void ResultCalculation(View v)
	{
		//если не выбрана оперция, показываем первый операнд
		if(!calc.getOperationIsChechked())
		{			
			return;
		}
		
		//считываем второй операнд
		if(!calc.getIsPersent())
		{
			calc.setOperand2(Double.parseDouble(Result.getText().toString()));
		}
		
		switch (calc.Operation)
		{
		case '+':
			calc.setResult(calc.getOperand1() + calc.getOperand2());
			break;
		case '-':
			calc.setResult(calc.getOperand1() - calc.getOperand2());
			break;
		case '/':
			calc.setResult(calc.getOperand1() / calc.getOperand2());
			break;
		case '*':
			//если стоит признак процента, считаем процент от числа
			if(calc.getIsPersent())
				calc.setResult(calc.getOperand2());
			else
				calc.setResult(calc.getOperand1() * calc.getOperand2());
			break;
			default:
				break;
		}
		
		//отображаем результат
		Log.setText(Double.toString(calc.getOperand1()) + "\n" +  Character.toString(calc.Operation) +
						"\n" + Double.toString(calc.getOperand2()) + "\n" + "=" +
						"\n" + Double.toString(calc.getResult()) + "\n" + "________________" +
						"\n" + Log.getText().toString());
		Scroll.fullScroll(View.FOCUS_UP);
		
		//если число получилось целое, убираем ".0" 
		String temp = Double.toString(calc.getResult());
		if((temp.substring(temp.length() - 2, temp.length()).equals(".0")))
		{
			temp = temp.substring(0, temp.length()-2);
		}
		
		Result.setText(temp);
		
		//обнуляем флаги и переменные
		calc.setIsPersent(false);
		calc.setOperationIsChechked(false);
		calc.Operation = '0';
		calc.setIsSolve(true);
		
		calc.setOperand1(0);
		calc.setOperand2(0);
	}	
	
	//функция ввода числа, перехватывает нажатия на кнопки с цифрами и "."
	public void DigitEnter(View v)
	{	
		//если это первое число, введенное после выбора операции или обработки и отображения результата предыдущего вычисления
		//то обнуляем Result, длят корректного ввода второго числа
		if(calc.getFirstDigitAfterOperation() || calc.getIsSolve())
		{
			Result.setText("0");
			calc.setFirstDigitAfterOperation(false);
			calc.setIsSolve(false);
		}
		
		//проверка на задвоение ввода "."
		if(v.getId() == R.id.Button_Point && Result.getText().toString().indexOf('.') != -1)
			return;
		
		Button b = (Button)v;

		//если первая цифра ноль и набрана вторая тоже ноль, то оставляем его
		if (v.getId() == R.id.Button_0 && Result.getText().toString().equals("0"))
			return;
		
		//если это первая цифра которая вводим, то убираем первоначальный ноль.
		if(Result.getText().toString().equals("0") && v.getId() != R.id.Button_Point)
		{		
			Result.setText(b.getText().toString());
			return;			
		}	

		Result.setText(Result.getText() + b.getText().toString());
	}

	//функция очистки результата
	public void Clear(View v)
	{
		Result.setText("0");
		calc.setOperationIsChechked(false);
		calc.Operation = '0';
		calc.setIsSign(false);
		calc.setIsSolve(false);
		calc.setFirstDigitAfterOperation(false);
	}
	
	//функция обрабатывающая выбранную операцию
	public void OperationDefenition(View v)
	{
		//считываем первый операнд из тестового поля
		if(calc.getOperand1() != 0 && calc.Operation != '0')
		{
			calc.setOperand2(Double.parseDouble(Result.getText().toString()));
			this.ResultCalculation(v);
		}
		
		calc.setOperand1(Double.parseDouble(Result.getText().toString()));
		
		Button b = (Button)v;
		calc.Operation = b.getText().charAt(0);
		calc.setOperationIsChechked(true);

		//утсанавливаем логическую переменную для дальнейшего ввода числе
		calc.setFirstDigitAfterOperation(true);

	}
	
	//обработка нажатия кнопки "квадратный корень"
	public void Sqare(View v)
	{
		//считываем первый операнд из тестового поля
		calc.setOperand1(Double.parseDouble(Result.getText().toString()));
		calc.setResult(Math.sqrt(calc.getOperand1()));
		calc.setIsSolve(true);
		
		Button b = (Button)v;
		calc.Operation = b.getText().charAt(0);
				
		//выволим результат в окно лога
		Log.setText(Character.toString(calc.Operation) + " " + Double.toString(calc.getOperand1()) + "\n" +
				"=" + "\n" + Double.toString(calc.getResult()) + "\n" + "________________" +
				"\n" + Log.getText().toString());

		Scroll.fullScroll(View.FOCUS_UP);
		
		//если число получилось целое, убираем ".0" 
		String temp = Double.toString(calc.getResult());
		if((temp.substring(temp.length() - 2, temp.length()).equals(".0")))
		{
			temp = temp.substring(0, temp.length()-2);
		}
		
		Result.setText(temp);		
	}
	
	//обработка нажатия кнопки, отвещающей за знак числа.
	public void Sign(View v)
	{
		// если не введено ни одно число, то есть в окне резульата 0, то знак не меняем
		if(Result.getText().toString().equals("0"))
			return;
		
		//убираем знак '-'
		if(calc.getIsSign())
		{
			Result.setText(Result.getText().toString().substring(1));
			calc.setIsSign(false);
			return;
		}
		Result.setText("-" + Result.getText().toString());
		calc.setIsSign(true);
	}
	
	//обработка нажатия кнопки '%'
	public void Persent(View v)
	{
		calc.setIsPersent(true);
		calc.setOperand2(Double.parseDouble(Result.getText().toString()));
		calc.setOperand2((calc.getOperand1() * calc.getOperand2())/100);
		this.ResultCalculation(v);
	}
	
}
