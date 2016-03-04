package com.mncomunity1.service;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.ArticlesReceivedEvent;
import com.mncomunity1.event.ArticlesRequestedEvent;
import com.mncomunity1.event.EnnigyReceivedEvent;
import com.mncomunity1.event.EnningRequestedEvent;
import com.mncomunity1.event.FeedReceivedEvent;
import com.mncomunity1.event.FeedRequestedEvent;
import com.mncomunity1.event.IsoReceivedEvent;
import com.mncomunity1.event.IsoRequestedEvent;
import com.mncomunity1.event.LogisticsReceivedEvent;
import com.mncomunity1.event.LogisiticsRequestedEvent;
import com.mncomunity1.event.MaintenanceReceivedEvent;
import com.mncomunity1.event.MaintenanceRequestedEvent;
import com.mncomunity1.event.ManagemantReceivedEvent;
import com.mncomunity1.event.ManagementRequestedEvent;
import com.mncomunity1.event.NewsReceivedEvent;
import com.mncomunity1.event.NewsRequestedEvent;
import com.mncomunity1.event.PhotoReceivedEvent;
import com.mncomunity1.event.PhotoRequestedEvent;
import com.mncomunity1.event.ProductionReceivedEvent;
import com.mncomunity1.event.ProductionRequestedEvent;
import com.mncomunity1.event.PurchaseReceivedEvent;
import com.mncomunity1.event.PurchaseRequestedEvent;
import com.mncomunity1.event.QualityReceivedEvent;
import com.mncomunity1.event.QualityRequestedEvent;
import com.mncomunity1.event.SafetyReceivedEvent;
import com.mncomunity1.event.SafetyRequestedEvent;
import com.mncomunity1.event.SaleReceivedEvent;
import com.mncomunity1.event.SaleRequestedEvent;
import com.mncomunity1.event.SuccessReceivedEvent;
import com.mncomunity1.event.SuccessRequestedEvent;
import com.mncomunity1.event.TipReceivedEvent;
import com.mncomunity1.event.TipRequestedEvent;
import com.mncomunity1.event.TraingReceivedEvent;
import com.mncomunity1.event.TraingRequestedEvent;
import com.mncomunity1.model.Post;
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

                    ApiBus.getInstance().postQueue(new SafetyReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
//                Log.e("error", error.getLocalizedMessage());
//                Log.e("error", error.getUrl());
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

                    ApiBus.getInstance().postQueue(new ProductionReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
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

    @Subscribe
    public void onArticles(final ArticlesRequestedEvent event) {
        api.getArticles(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new ArticlesReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onNews(final NewsRequestedEvent event) {
        api.getNews(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new NewsReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onTip(final TipRequestedEvent event) {
        api.getTip(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new TipReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onTraing(final TraingRequestedEvent event) {
        api.getTraing(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new TraingReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onEning(final EnningRequestedEvent event) {
        api.getEnnigy(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new EnnigyReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onSuccess(final SuccessRequestedEvent event) {
        api.getSuccess(event.getVendor(), new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                ApiBus.getInstance().postQueue(new SuccessReceivedEvent(post));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onTrain(final TraingRequestedEvent event) {
        api.getTrain(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                for (int i = 1; i < post.getPost().size(); i++) {
                    ApiBus.getInstance().postQueue(new TraingReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onFeed(final FeedRequestedEvent event) {
        api.getFeed(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                for (int i = 1; i < post.getPost().size(); i++) {
                    ApiBus.getInstance().postQueue(new FeedReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    @Subscribe
    public void onPhoto(final PhotoRequestedEvent event) {
        api.getPhoto(new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                for (int i = 1; i < post.getPost().size(); i++) {
                    ApiBus.getInstance().postQueue(new PhotoReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
