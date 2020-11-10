package com.example.ee408project;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private StoreDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new StoreDatabase(this);
        try {
            dbHelper.open();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void onClickListener(View view){
        Intent nextScreen = new Intent(this, CloseUp.class);
        startActivity(nextScreen);
    }

}