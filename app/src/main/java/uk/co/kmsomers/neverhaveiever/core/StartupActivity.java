package uk.co.kmsomers.neverhaveiever.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.kmsomers.neverhaveiever.R;
import uk.co.kmsomers.neverhaveiever.views.category_select.CategorySelectActivity;

/**
 * Start up activity in case I feel a login is needed or potentially for any work that needs to be done
 * to initialise the app
 */
public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        startActivity(new Intent(StartupActivity.this, CategorySelectActivity.class));
        finish();
    }
}
