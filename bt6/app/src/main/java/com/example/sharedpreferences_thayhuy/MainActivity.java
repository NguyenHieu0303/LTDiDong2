package com.example.sharedpreferences_thayhuy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String SHARED_PREFERENCE_NAME = "SettingGame";
    private CheckBox cbVolume;
    private CheckBox cbSoundEffect;
    private SeekBar sbVolume;
    private SeekBar sbSoundEffect;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbVolume = (CheckBox) findViewById(R.id.cb_volume);
        cbSoundEffect = (CheckBox) findViewById(R.id.cb_sound_effect);
        sbVolume = (SeekBar) findViewById(R.id.sb_volume);
        sbSoundEffect = (SeekBar) findViewById(R.id.sb_sound_effect);
        btnSave = (Button) findViewById(R.id.btn_save);

        cbVolume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sbVolume.setVisibility(View.VISIBLE);
                else
                    sbVolume.setVisibility(View.INVISIBLE);
            }
        });

        cbSoundEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sbSoundEffect.setVisibility(View.VISIBLE);
                else
                    sbSoundEffect.setVisibility(View.INVISIBLE);
            }
        });

        // Lấy giá trị từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean isVolume = sharedPreferences.getBoolean("volume", false);
        boolean isSoundEffect = sharedPreferences.getBoolean("sound_effect", false);
        int volumeValue = sharedPreferences.getInt("volume_value", 0);
        int soundEffectValue = sharedPreferences.getInt("sound_effect_value", 0);
        cbVolume.setChecked(isVolume);
        cbSoundEffect.setChecked(isSoundEffect);

        if (isVolume)
            sbVolume.setProgress(volumeValue);
        else
            sbVolume.setVisibility(View.INVISIBLE);

        if (isSoundEffect)
            sbSoundEffect.setProgress(soundEffectValue);
        else
            sbSoundEffect.setVisibility(View.INVISIBLE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("volume", cbVolume.isChecked());
                editor.putBoolean("sound_effect", cbSoundEffect.isChecked());

                if (cbVolume.isChecked())
                    editor.putInt("volume_value", sbVolume.getProgress());
                else
                    editor.putInt("volume_value", 0);

                if (cbSoundEffect.isChecked())
                    editor.putInt("sound_effect_value", sbSoundEffect.getProgress());
                else
                    editor.putInt("sound_effect_value", 0);

                editor.apply();


                Toast.makeText(MainActivity.this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
