package com.yangli907.tipsCalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TipsCalcActivity extends Activity {
	private double amount = 0.0;
	private double tips = 0.0;
	private int taxRate = 6;
	private int tipsRate = 15;
	private double totalAmount = 0.0;
	private int persons = 1;
	private int inputNum = 0;
	private java.text.DecimalFormat df =new DecimalFormat("#,###,##0.00");
	private TextView tv_tax;
	private TextView tv_tips;
	private TextView tv_persons;
	private TextView tv_tipsAmount;
	private TextView tv_totalDue;
	private TextView tv_eachDue;
	private Button btn_rateConfig;
	private LinearLayout rateConfigLayout;
	private Boolean isOpen = false;
	Animation animation;
	private Vibrator myVib;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getInputNum() {
		return inputNum;
	}

	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv_tax = (TextView)findViewById(R.id.taxField);
        tv_tax.setText(String.valueOf(Math.round(getTaxRate())));
        tv_tips = (TextView)findViewById(R.id.tipsField);
        tv_tips.setText(String.valueOf(getTipsRate()));
        tv_persons = (TextView)findViewById(R.id.personField);
        tv_persons.setText(String.valueOf(getPersons()));
        tv_tipsAmount = (TextView)findViewById(R.id.tipsAmount);
        tv_totalDue = (TextView)findViewById(R.id.totalAmount);
        tv_eachDue = (TextView)findViewById(R.id.eachAmount);
        btn_rateConfig = (Button)findViewById(R.id.btn_options);
        rateConfigLayout = (LinearLayout)findViewById(R.id.rateConfigLayout);
        
    	animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        animation.setStartOffset(100);
        animation.setFillAfter(true);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        //rateConfigLayout.setVisibility(View.GONE);
    }
    
    public void onEnter(View view){
    	myVib.vibrate(50);
    	setTips(calcTips());
    	setTotalAmount(getTips()+getAmount()/100.);
    	animation.start();
    	tv_totalDue.setAnimation(animation);
    	tv_totalDue.setText(String.valueOf(df.format(getTotalAmount())));
    	tv_tipsAmount.setText(String.valueOf(df.format(getTips())));
    	tv_tipsAmount.setAnimation(animation);
    	tv_eachDue.setText(String.valueOf(df.format(getTotalAmount()/getPersons())));
    	tv_eachDue.setAnimation(animation);
    	//Toast.makeText(this, String.valueOf(getTips()), Toast.LENGTH_SHORT).show();
    	/*Intent intent = new Intent(TipsCalcActivity.this, SetParameter.class);
    	startActivity(intent);*/
    }
    public void onClickUpDown(View view){
    	int taxSetting = Integer.valueOf(tv_tax.getText().toString());
    	int tipsSetting = Integer.valueOf(tv_tips.getText().toString());
    	int persons = Integer.valueOf(tv_persons.getText().toString());
    	myVib.vibrate(10);
    	switch(view.getId()){
    	case(R.id.uTax):
    		setTaxRate(taxSetting+1);
    		tv_tax.setText(String.valueOf(getTaxRate()));
    		break;
    	case(R.id.dTax):
    		if(taxSetting>=1)
    			setTaxRate(taxSetting-1);
    		tv_tax.setText(String.valueOf(getTaxRate()));
    		break;
    	case(R.id.uRate):
    		setTipsRate(tipsSetting+5);
    		tv_tips.setText(String.valueOf(getTipsRate()));
    		break;
    	case(R.id.dRate):
    		if(tipsSetting>=5)
    			setTipsRate(tipsSetting-5);
    		tv_tips.setText(String.valueOf(getTipsRate()));
    		break;
    	case(R.id.uPerson):
    		setPersons(persons+1);
    		tv_persons.setText(String.valueOf(getPersons()));
    		break;
    	case(R.id.dPerson):
    		if(persons>=1)
    			setPersons(persons-1);
    		tv_persons.setText(String.valueOf(getPersons()));
    		break;
   		default:
    	}
    }
    public void onClickNum(View view){
    	myVib.vibrate(10);
    	switch(view.getId()){
    	case(R.id.button1):
    		updateDisplayNum(1);
    		break;
    	case(R.id.button2):
    		updateDisplayNum(2);
			break;
    	case(R.id.button3):
    		updateDisplayNum(3);
    		break;
    	case(R.id.button4):
    		updateDisplayNum(4);
			break;
    	case(R.id.button5):
    		updateDisplayNum(5);
    		break;
    	case(R.id.button6):
    		updateDisplayNum(6);
			break;
    	case(R.id.button7):
    		updateDisplayNum(7);
    		break;
    	case(R.id.button8):
    		updateDisplayNum(8);
			break;
    	case(R.id.button9):
    		updateDisplayNum(9);
    		break;
    	case(R.id.button10):
    		updateDisplayNum(0);
			break;
    	case(R.id.buttonClear):
    		setAmount(0);
    		clearDisplayNum();
		default:
			updateDisplayNum(0);
    		break;
    	}
    }
    
    public void updateDisplayNum(int inputNum){
    	setInputNum(inputNum);
    	setAmount((getAmount()*10+getInputNum()));
    	TextView amountText = (TextView)findViewById(R.id.amountText);
    		amountText.setText(String.valueOf(df.format(getAmount()/100)));
    }
    
    public void clearDisplayNum(){
    	TextView amountText = (TextView)findViewById(R.id.amountText);
    	amountText.setText(String.valueOf(df.format(0.00)));
    	tv_tipsAmount.setText(String.valueOf(df.format(0.00)));
    	tv_totalDue.setText(String.valueOf(df.format(0.00)));
    	tv_eachDue.setText(String.valueOf(df.format(0.00)));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, Menu.FIRST + 1 , 2, "About");
    	menu.add(Menu.NONE, Menu.FIRST + 2 , 5, "Quit");
    	// TODO Auto-generated method stub
    	return super.onCreateOptionsMenu(menu);
    }
    
    public double calcTips()
    {
    	double tipsAmount = (getAmount()/(100+Integer.valueOf(tv_tax.getText().toString())))*(double)Integer.valueOf(tv_tips.getText().toString())/100.; 
		return tipsAmount;
    	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case Menu.FIRST + 1:
    			Toast.makeText(this, "For any feedback or suggestions, please contact developer at yangli907@163.com.", Toast.LENGTH_LONG).show();
    			break;
    		case Menu.FIRST + 2:
    			System.exit(1);
    			break;
    	}
    			// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }
    
    public void onClickMoreOptions(View view) {
        TranslateAnimation anim = null;

        isOpen = !isOpen;

        if (isOpen) {
            rateConfigLayout.setVisibility(View.VISIBLE);
            btn_rateConfig.setText("Hide Options");
            anim = new TranslateAnimation(rateConfigLayout.getWidth(), 0.0f, 0.0f, 0.0f);
        } else {
            anim = new TranslateAnimation(0.0f, rateConfigLayout.getWidth(), 0.0f, 0.0f);
            btn_rateConfig.setText("Show Options");
            anim.setAnimationListener(collapseListener);
        }

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(1.0f));
        rateConfigLayout.startAnimation(anim);
    }

    Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
        	rateConfigLayout.setVisibility(View.GONE);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    };



	public double getTips() {
		return tips;
	}

	public void setTips(double tips) {
		this.tips = tips;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	public int getTipsRate() {
		return tipsRate;
	}

	public void setTipsRate(int tipsRate) {
		this.tipsRate = tipsRate;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}