package banyan.com.mpchartatempt;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String TAG = "Complaints";
    public static RequestQueue queue;
    ProgressDialog pDialog;

    String month = "";
    int plan = 0;
    int achive = 0;
    int value = 0;

    ArrayList<BarEntry> Arraylist_plan = null;
    ArrayList<BarEntry> Arraylist_achive = null;

    ArrayList<BarEntry> valueSet1 = null;
    ArrayList<BarEntry> valueSet2 = null;

    BarDataSet barDataSet1;
    BarDataSet barDataSet2;

    BarEntry v1e1,v2e1;

    ArrayList<BarDataSet> dataSets;

    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (BarChart) findViewById(R.id.chart);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        pDialog.setCancelable(false);
        queue = Volley.newRequestQueue(MainActivity.this);
        GetMyTask();
    }

    /*****************************
     * GET My Task
     ***************************/

    public void GetMyTask() {

        String tag_json_obj = "http://epictech.in/school_book/android/dummy.php";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                tag_json_obj, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("Schedule");

                        Arraylist_plan = new ArrayList<BarEntry>();
                        Arraylist_achive = new ArrayList<BarEntry>();

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            month = obj1.getString("month");
                            plan = obj1.getInt("plan");
                            achive = obj1.getInt("achive");

                            Arraylist_plan = new ArrayList<>();
                            v1e1 = new BarEntry(plan, i);
                            Arraylist_plan.add(v1e1);

                            Arraylist_achive = new ArrayList<>();
                            v2e1 = new BarEntry(achive, i);
                            Arraylist_achive.add(v2e1);

                            BarData data = new BarData(getXAxisValues(), getDataSet());
                            chart.setData(data);
                            chart.setDescription("My Chart");
                            chart.animateXY(2000, 2000);
                            chart.invalidate();

                        }

                        System.out.println("TEST : " + Arraylist_plan);
                        System.out.println("TEST : " + Arraylist_achive);

                    } else if (success == 0) {


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // stopping swipe refresh
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                pDialog.hide();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    private ArrayList<BarDataSet> getDataSet() {

        barDataSet1 = new BarDataSet(Arraylist_plan, "Brand 1");
        barDataSet1.setColor(Color.rgb(30,144,255));
        barDataSet2 = new BarDataSet(Arraylist_achive, "Brand 2");
        barDataSet2.setColors(ColorTemplate.JOYFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        return dataSets;

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
}
