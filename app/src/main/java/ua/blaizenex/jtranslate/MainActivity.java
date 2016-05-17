package ua.blaizenex.jtranslate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    TextView tvTranslationTextField;
    EditText edHandle_input ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTranslationTextField = (TextView)findViewById(R.id.tvTranslationTextField);
        edHandle_input = (EditText)findViewById(R.id.edHandle_input);

        //reading dict
        try {
            readToHashMap();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //Button clear text area block
            Button btnClearTextArea = (Button)findViewById(R.id.btnClearTextArea);
            btnClearTextArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edHandle_input.setText("");
                }
            });
        //Button clear text area block <END>

        //Button view txt block
        Button btnViewTXT = (Button)findViewById(R.id.btnViewTXT);
        btnViewTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startSelection();
            }
        });
        //Button view txt block <END>

        //Spinner block
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(this);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.translate_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        //Spinner block <END>
    }

    private void startSelection() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        Uri startDir = Uri.fromFile(new File("/sdcard"));
        intent.setDataAndType(startDir,"*.txt");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void readToHashMap() throws IOException {
        Map<String, String> eng_por = new HashMap<String, String>();
        Map<String, String> por_eng = new HashMap<String, String>();
        String line = "";
        String line2 = "";
        InputStream is = this.getResources().openRawResource(R.raw.dict);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            while ((line = reader.readLine()) != null) {
               line2 = reader.readLine();
                eng_por.put(line,line2);
                por_eng.put(line2,line);
            }
        }
        is.close();
        String res = por_eng.get("errôneo") + " : " + eng_por.get("erroneous");
        tvTranslationTextField.setText(res);
    }
}
