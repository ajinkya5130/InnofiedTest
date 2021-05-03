/*
 * Copyright (c) 13/10/20 12:50 PM Ajinkya K. Android Dev.
 */

package com.ajinkya.assignmentapp.api_call;

import com.ajinkya.assignmentapp.models.UsersInfoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET(ApiUrl.GET_USERS)
    Call<UsersInfoModel> getUsersList(@Query("page") int page_number, @Query("per_page") int per_size);
}
