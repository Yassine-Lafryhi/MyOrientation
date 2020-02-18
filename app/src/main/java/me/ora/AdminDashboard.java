package me.ora;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_dashboard);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font6.ttf");


        RelativeLayout StartLayout = findViewById(R.id.Layout);

        ArrayList<View> clds = getAllChildren(StartLayout);
        for (int i = 0; i < clds.size(); i += 1) {

            if (clds.get(i) instanceof TextView) {
                ((TextView) clds.get(i)).setTypeface(custom_font);
            }

            if (clds.get(i) instanceof Button) {
                ((Button) clds.get(i)).setTypeface(custom_font);
            }
        }


        RelativeLayout Menu1 = findViewById(R.id.Menu1);
        RelativeLayout Menu2 = findViewById(R.id.Menu2);
        RelativeLayout Menu3 = findViewById(R.id.Menu3);
        RelativeLayout Menu4 = findViewById(R.id.Menu4);


        Button logOut = findViewById(R.id.LogOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AdminDashboard.this, LoginPage.class));
                finish();


            }
        });


        Menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AdminDashboard.this, Statistics.class));



            }
        });


        Menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AdminDashboard.this, StudentsList.class));



            }
        });


        Menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AdminDashboard.this, PrincipalList.class));



            }
        });


        Menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AdminDashboard.this, WaitingList.class));



            }
        });


        /** final ImageView splash = findViewById(R.id.logo);
         Display display = getWindowManager().getDefaultDisplay();
         float h = display.getHeight();
         TranslateAnimation animation = new TranslateAnimation(0, 0, -200, 40); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
         animation.setDuration(1500); // animation duration
         //animation.setRepeatCount(1); // animation repeat count
         //animation.setRepeatMode(2); // repeat animation (left to right, right to
         // left )
         // animation.setFillAfter(true);

         splash.startAnimation(animation); // start animation
         **/


        /**BarChart chart = findViewById(R.id.barchart);

         ArrayList NoOfEmp = new ArrayList();

         NoOfEmp.add(new BarEntry(945f, 0));
         NoOfEmp.add(new BarEntry(1040f, 1));
         NoOfEmp.add(new BarEntry(1133f, 2));
         NoOfEmp.add(new BarEntry(1240f, 3));
         NoOfEmp.add(new BarEntry(1369f, 4));
         NoOfEmp.add(new BarEntry(1487f, 5));
         NoOfEmp.add(new BarEntry(1501f, 6));
         NoOfEmp.add(new BarEntry(1645f, 7));
         NoOfEmp.add(new BarEntry(1578f, 8));
         NoOfEmp.add(new BarEntry(1695f, 9));

         ArrayList year = new ArrayList();

         year.add("2008");
         year.add("2009");
         year.add("2010");
         year.add("2011");
         year.add("2012");
         year.add("2013");
         year.add("2014");
         year.add("2015");
         year.add("2016");
         year.add("2017");

         BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
         chart.animateY(5000);
         BarData data = new BarData( year, bardataset);
         bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
         chart.setData(data);





         PieChart pieChart = findViewById(R.id.piechart);
         ArrayList NoOfEmp2 = new ArrayList();

         NoOfEmp2.add(new Entry(945f, 0));
         NoOfEmp2.add(new Entry(1040f, 1));
         NoOfEmp2.add(new Entry(1133f, 2));
         NoOfEmp2.add(new Entry(1240f, 3));
         NoOfEmp2.add(new Entry(1369f, 4));
         NoOfEmp2.add(new Entry(1487f, 5));
         NoOfEmp2.add(new Entry(1501f, 6));
         NoOfEmp2.add(new Entry(1645f, 7));
         NoOfEmp2.add(new Entry(1578f, 8));
         NoOfEmp2.add(new Entry(1695f, 9));
         PieDataSet dataSet2 = new PieDataSet(NoOfEmp2, "Number Of Employees");

         ArrayList year2 = new ArrayList();

         year2.add("2008");
         year2.add("2009");
         year2.add("2010");
         year2.add("2011");
         year2.add("2012");
         year2.add("2013");
         year2.add("2014");
         year2.add("2015");
         year2.add("2016");
         year2.add("2017");


         PieData data2 = new PieData(year2, dataSet2);
         pieChart.setData(data2);
         dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
         pieChart.animateXY(5000, 5000);



         LineChart lineChart = findViewById(R.id.LineChart);
         ArrayList NoOfEmp3 = new ArrayList();
         NoOfEmp3.add(new Entry(945f, 0));
         NoOfEmp3.add(new Entry(1040f, 1));
         NoOfEmp3.add(new Entry(1133f, 2));
         NoOfEmp3.add(new Entry(1240f, 3));
         NoOfEmp3.add(new Entry(1369f, 4));
         NoOfEmp3.add(new Entry(1487f, 5));
         NoOfEmp3.add(new Entry(1501f, 6));
         NoOfEmp3.add(new Entry(1645f, 7));
         NoOfEmp3.add(new Entry(1578f, 8));
         NoOfEmp3.add(new Entry(1695f, 9));
         LineDataSet dataSet3 = new LineDataSet(NoOfEmp3, "Number Of Employees");
         ArrayList year3 = new ArrayList();
         year3.add("2008");
         year3.add("2009");
         year3.add("2010");
         year3.add("2011");
         year3.add("2012");
         year3.add("2013");
         year3.add("2014");
         year3.add("2015");
         year3.add("2016");
         year3.add("2017");
         LineData data3 = new LineData(year3, dataSet3);
         lineChart.setData(data3);
         dataSet3.setColors(ColorTemplate.JOYFUL_COLORS);
         lineChart.animateXY(5000, 5000);




         RadarChart radarChart = findViewById(R.id.RadarChart);
         ArrayList NoOfEmp4 = new ArrayList();
         NoOfEmp4.add(new Entry(945f, 0));
         NoOfEmp4.add(new Entry(1040f, 1));
         NoOfEmp4.add(new Entry(1133f, 2));
         RadarDataSet dataSet4 = new RadarDataSet(NoOfEmp4, "Number Of Employees");
         ArrayList year4 = new ArrayList();
         year4.add("2008");
         year4.add("2009");
         year4.add("2010");
         RadarData data4 = new RadarData(year4, dataSet4);
         radarChart.setData(data4);
         dataSet4.setColors(ColorTemplate.JOYFUL_COLORS);
         radarChart.animateXY(5000, 5000);
         **/

        //Database.connect();

/**
 final EditText email = findViewById(R.id.Email);
 final EditText password = findViewById(R.id.Password);


 Button logIn = findViewById(R.id.LogIn);
 logIn.setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View v) {

Toast.makeText(getApplicationContext(), "Connecting ...", Toast.LENGTH_LONG).show();

try {
ResultSet resultSet = Database.executeQuery("SELECT \"cin\" FROM \"ETUDIANT\" WHERE \"cne\" = '" + email.getText().toString().split("@")[0] + "'");
if (resultSet.next()) {
String retrievedPassword = resultSet.getString(1);
if (password.getText().toString().equals(retrievedPassword)) {
Toast.makeText(getApplicationContext(), "Connected !", Toast.LENGTH_LONG).show();
}
} else {
Toast.makeText(getApplicationContext(), "The informations are incorrect !", Toast.LENGTH_LONG).show();

}


} catch (SQLException e) {
e.printStackTrace();
}


}
});
 **/

    }


    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));
            result.addAll(viewArrayList);
        }
        return result;
    }


    @Override
    public void onBackPressed() {

    }


}
