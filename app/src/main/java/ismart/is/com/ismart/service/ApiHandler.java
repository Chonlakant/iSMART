package ismart.is.com.ismart.service;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import ismart.is.com.ismart.event.ApiBus;
import ismart.is.com.ismart.event.IsoReceivedEvent;
import ismart.is.com.ismart.event.IsoRequestedEvent;
import ismart.is.com.ismart.event.LogisticsReceivedEvent;
import ismart.is.com.ismart.event.LogisiticsRequestedEvent;
import ismart.is.com.ismart.event.MaintenanceReceivedEvent;
import ismart.is.com.ismart.event.MaintenanceRequestedEvent;
import ismart.is.com.ismart.event.ManagemantReceivedEvent;
import ismart.is.com.ismart.event.ManagementRequestedEvent;
import ismart.is.com.ismart.event.ProductionReceivedEvent;
import ismart.is.com.ismart.event.ProductionRequestedEvent;
import ismart.is.com.ismart.event.PurchaseReceivedEvent;
import ismart.is.com.ismart.event.PurchaseRequestedEvent;
import ismart.is.com.ismart.event.QualityReceivedEvent;
import ismart.is.com.ismart.event.QualityRequestedEvent;
import ismart.is.com.ismart.event.SafetyReceivedEvent;
import ismart.is.com.ismart.event.SafetyRequestedEvent;
import ismart.is.com.ismart.event.SaleReceivedEvent;
import ismart.is.com.ismart.event.SaleRequestedEvent;
import ismart.is.com.ismart.model.Post;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ApiHandler {

    public Context context;
    private ApiService api;
    private ApiBus apiBus;

    public ApiHandler(Context context, ApiService api,
                      ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }


    @Subscribe
    public void onGetLogistics(final LogisiticsRequestedEvent event) {
        api.getLogistics(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new LogisticsReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetQuality(final QualityRequestedEvent event) {
        api.getQuality(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new QualityReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetSafety(final SafetyRequestedEvent event) {
        api.getSafet(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new SafetyReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetProduction(final ProductionRequestedEvent event) {
        api.getProduction(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new ProductionReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetMainten(final MaintenanceRequestedEvent event) {
        api.getMainten(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new MaintenanceReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetManagement(final ManagementRequestedEvent event) {
        api.getManagment(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new ManagemantReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onGetIso(final IsoRequestedEvent event) {
        api.getIso(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new IsoReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onPurchase(final PurchaseRequestedEvent event) {
        api.getPurchase(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new PurchaseReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }

    @Subscribe
    public void onSale(final SaleRequestedEvent event) {
        api.getSale(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if (post.getPost() != null) {
//                    for(int i = 0; i < post.getPost().size();i++){
//                        ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
//                    }
                    Log.e("ddddd", post.getPost().get(0).getDetails());
                    ApiBus.getInstance().postQueue(new SaleReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getLocalizedMessage());
                Log.e("error", error.getUrl());
            }
        });
    }


}
