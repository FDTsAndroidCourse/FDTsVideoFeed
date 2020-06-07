package onl.fdt.java.android.fdtsvideofeed.api;

import java.util.List;

import onl.fdt.java.android.fdtsvideofeed.api.payload.VideoEntity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    private static final String BASE_URL = "https://beiyou.bytedance.com/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static Video video;

    public static Call<List<VideoEntity>> getVideoEntityCall() {
        if (video == null) {
            video = retrofit.create(Video.class);
        }

        Call<List<VideoEntity>> call = video.invokeVideo();
        return call;
    }

}
