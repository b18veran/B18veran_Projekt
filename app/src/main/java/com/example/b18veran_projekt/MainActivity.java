package com.example.b18veran_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.b18veran_projekt.Gymnaster;
import com.example.b18veran_projekt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {
    private String[] Extra_Message;
    public static final String EXTRA_MESSAGE = "Test";
    public static final String EXTRA_MESSAGE2 = "Test2";
    // Create ArrayLists from the raw data above and use these lists when populating your ListView.
    private ArrayList<String> listData;
    private ArrayAdapter<Gymnaster> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gymnastik");

        new FetchData().execute();
        setContentView(R.layout.activity_main);


        adapter= new ArrayAdapter<Gymnaster>(this, R.layout. list_item_textview, R.id.list_item_textview);

        ListView my_listview=(ListView) findViewById(R.id.my_listview);
        my_listview.setAdapter(adapter);

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //Skapar en variabel som hämtar datan som ska fram i toasten.
                String test = adapter.getItem(i).info();
                Intent intent = new Intent (getApplicationContext(),GymnastDetails.class);
                String allt = adapter.getItem(i).info();
                String allt2 = adapter.getItem(i).ovningar();
                intent.putExtra(EXTRA_MESSAGE,allt);
                intent.putExtra(EXTRA_MESSAGE2,allt2);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater(). inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            new FetchData().execute();
            adapter.clear();
            return true;
        }
        if(id == R.id.action_settings){
            //Här har jag gjort en egen designad toast.
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Målgrupp: Tränare samt gymnaster på grundnivå.");

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();



            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/b18veran/Mobilapplikations-programmering/Gymnast_Data.php");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            Log.d("veronica", "DataFetched:"+o);

            try {
                JSONArray veronicaarray = new JSONArray(o);

                for(int i=0; i < veronicaarray.length(); i++){
                    Log.d("veronica", "element 0"+veronicaarray.get(i).toString());
                    JSONObject veronicaobject = veronicaarray.getJSONObject(i);
                    Log.d("veronica", veronicaobject.getString("ID"));
                    Log.d("veronica", veronicaobject.getString("name"));
                    Log.d("veronica", veronicaobject.getString("location"));
                    Log.d("veronica", veronicaobject.getString("company"));
                    Log.d("veronica", veronicaobject.getString("auxdata"));
                    Log.d("veronica", veronicaobject.getString("category"));

                    Gymnaster m = new Gymnaster(veronicaobject.getString("ID"),veronicaobject.getString("name"), veronicaobject.getString("location"),
                            veronicaobject.getString("company"), veronicaobject.getString("auxdata"), veronicaobject.getString("category"));
                    Log.d("veronica", m.toString());
                    adapter.add(m);
                }
            } catch (JSONException e) {
                Log.e("veronica","E:"+e.getMessage());
            }
        }
    }
}

