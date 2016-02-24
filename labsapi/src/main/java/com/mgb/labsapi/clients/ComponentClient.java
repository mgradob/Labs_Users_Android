package com.mgb.labsapi.clients;

import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentClient {

    @GET("/{lab}/component/")
    Observable<ArrayList<Component>> getAllComponents(@Header(ApiConstants.AUTHORIZATION) String token,
                                                      @Path(ApiConstants.LAB) String lab);

    @GET("/{lab}/component/")
    Observable<ArrayList<Component>> getComponentsOfCategory(@Header(ApiConstants.AUTHORIZATION) String token,
                                                             @Path(ApiConstants.LAB) String lab,
                                                             @Query(ApiConstants.ID_CATEGORY_FK) int categoryFk);

    @GET("/{lab}/component/{id_component}")
    Observable<Component> getComponent(@Header(ApiConstants.AUTHORIZATION) String token,
                                       @Path(ApiConstants.LAB) String lab,
                                       @Path(ApiConstants.ID_COMPONENT) int componentId);
}
