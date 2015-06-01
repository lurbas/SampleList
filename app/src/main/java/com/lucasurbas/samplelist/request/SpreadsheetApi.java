package com.lucasurbas.samplelist.request;

import com.lucasurbas.samplelist.constant.Url;
import com.lucasurbas.samplelist.model.Items;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by M30 on 2015-05-31.
 */
public interface SpreadsheetApi {

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET(Url.PATH_CCC)
    void getSpreadsheet(@Query(Url.QUERY_KEY) String key,
                          @Query(Url.QUERY_SINGLE) String single,
                          @Query(Url.QUERY_GID) String gid,
                          @Query(Url.QUERY_OUTPUT) String output,
                          Callback<Items> cb);
}
