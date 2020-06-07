/*
 * Copyright (c) 2020 fdt <frederic.dt.twh@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package onl.fdt.java.android.fdtsvideofeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import onl.fdt.java.android.fdtsvideofeed.api.Controller;
import onl.fdt.java.android.fdtsvideofeed.api.payload.VideoEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        TextView loadingText = findViewById(R.id.loading_text);

        Call<List<VideoEntity>> call = Controller.getVideoEntityCall();

        DataAdapter adapter = new DataAdapter(this);

        LOGGER.info("MainActivity");

        call.enqueue(new Callback<List<VideoEntity>>() {

            @Override
            public void onResponse(Call<List<VideoEntity>> call, Response<List<VideoEntity>> response) {
                LOGGER.info("Body" + response.body());

                adapter.setVideoEntities(response.body());
                viewPager2.setAdapter(adapter);
                ((ViewGroup) loadingText.getParent()).removeView(loadingText);


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VideoEntity>> call, Throwable throwable) {
                LOGGER.log(Level.SEVERE, "Error when calling Controller.getVideoEntityCall", throwable);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                adapter.pauseAllPlayers();
            }
        });


    }
}
