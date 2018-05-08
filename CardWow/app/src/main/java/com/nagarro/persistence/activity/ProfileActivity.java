package com.nagarro.persistence.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nagarro.persistence.R;
import com.nagarro.persistence.database.AppDatabase;
import com.nagarro.persistence.databinding.ActivityMainBinding;
import com.nagarro.persistence.entity.User;
import com.nagarro.persistence.utils.DatabaseInitializer;

import java.util.List;

/**
 * Created by Zachary on 4/26/2018.
 */

public class ProfileActivity extends AppCompatActivity {
    EditText linked_link;
    EditText first_name;
    EditText last_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linked_link = (EditText)findViewById(R.id.input_linkedin);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);

        /**Do you have to bind all the activities like this???**/
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
    }

    /**Displays Toasts for the changes saved*/
    public void onChangesSaved(View view){
        Context context;
        context = getApplicationContext();
        CharSequence text = "Changes Saved";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    /**Changes the value of the users first name, link,  and last name*/
    public void onSubmit(View view){
        String link_text = (linked_link.getText()).toString();
        String first_text = (first_name.getText()).toString();
        String last_text =  (last_name.getText()).toString();
        List<User> user_List = DatabaseInitializer.getUserList(AppDatabase.getAppDatabase(this));
        for(int i = 0; i<user_List.size();i++){
            if(user_List.get(i).getFirstName()=="Zachary"){
                user_List.get(i).setLink(link_text);
                user_List.get(i).setFirstName(first_text);
                user_List.get(i).setLastName(last_text);
                DatabaseInitializer.updateChange(AppDatabase.getAppDatabase(this), user_List.get(i));
                Toast.makeText(getApplicationContext(), "Some of your profile information has been changed", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }

}
