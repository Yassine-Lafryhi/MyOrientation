package com.myorientation;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Statistics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.statistics);
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


        ArrayList fls = new ArrayList();
        ArrayList nstudents = new ArrayList();
        try {
            ResultSet fil = Database.executeQuery("SELECT \"id_filiere\",\"nomfiliere\" FROM FILIERE wHERE \"id_etablissement\" = (SELECT  FK_ETABLISSEMENT FROM ADMIN WHERE \"email\" ='" + LoginPage.adminEmail + "')");


            int i = 0;
            while (fil.next()) {

                ResultSet numberOfStudents = Database.executeQuery("select COUNT(\"cne\")\n" +
                        "from ETUDIANT\n" +
                        "where \"id_filiere\" = " + fil.getString(1));

                fls.add(fil.getString(2));

                numberOfStudents.next();
                nstudents.add(new Entry(Integer.parseInt(numberOfStudents.getString(1)), i));


                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        PieChart pieChart = findViewById(R.id.piechart);


        PieDataSet dataSet2 = new PieDataSet(nstudents, "Les filières");


        PieData data2 = new PieData(fls, dataSet2);
        pieChart.setData(data2);
        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.animateXY(5000, 5000);


        BarChart chart = findViewById(R.id.barchart);

        ArrayList flsBar = new ArrayList();
        ArrayList nstudents12Bar = new ArrayList();
        try {
            ResultSet fil2 = Database.executeQuery("SELECT \"id_filiere\",\"nomfiliere\" FROM FILIERE wHERE \"id_etablissement\" = (SELECT  FK_ETABLISSEMENT FROM ADMIN WHERE \"email\" ='" + LoginPage.adminEmail + "')");


            int i = 0;
            while (fil2.next()) {

                ResultSet numberOfStudents = Database.executeQuery("select COUNT(\"cne\")" +
                        "from ETUDIANT\n" +
                        "where \"id_filiere\" = " + fil2.getString(1) + " and \"noteBac\" >= 12");

                flsBar.add(fil2.getString(2));

                numberOfStudents.next();
                nstudents12Bar.add(new BarEntry(Integer.parseInt(numberOfStudents.getString(1)), i));


                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        BarDataSet bardataset = new BarDataSet(nstudents12Bar, "");
        chart.animateY(5000);
        BarData data = new BarData(flsBar, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        chart.setData(data);


/**








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


}
