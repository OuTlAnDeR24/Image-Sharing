package com.example.ashish.imagesharing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZAgFQIcanJ2ZFropucEp9jgEpglDw7zDJuzKqKLD", "nDl6yGlc5xPqrMUllaSXiH87paeLPuwcYOhY2VMF");

        mButton = (Button) findViewById(R.id.myButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject testObject = new ParseObject("TestObject");
                testObject.put("foo", "bar");
                testObject.saveInBackground();

            }
        });

    }

}
