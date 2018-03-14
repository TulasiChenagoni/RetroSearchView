package com.mansopresk.retrosearch;

import com.mansopresk.retrosearch.model.DocumentCategories;
import com.mansopresk.retrosearch.model.JsonResponse;

import java.util.List;

import retrofit.Callback;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mansopresk10 on 3/13/2018.
 */

public interface RequestInterface
{
    @GET("/mobileassignment/repository/doc_categories")

    Call<DocumentCategories> getJSON(Callback<JsonResponse> callback);

    Call<List<DocumentCategories>> getJSON();
}
