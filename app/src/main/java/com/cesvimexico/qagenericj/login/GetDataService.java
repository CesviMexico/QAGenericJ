package com.cesvimexico.qagenericj.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetDataService {

    @FormUrlEncoded
    @POST("/realms/cesvi/protocol/openid-connect/token")
    Call<AccessToken> getAccessToken(
            @Field("client_id") String client_id,
            @Field("grant_type") String grant_type,
            @Field("client_secret") String client_secret,
            @Field("scope") String scope,
            @Field("username") String username,
            @Field("password") String password
    );

}
