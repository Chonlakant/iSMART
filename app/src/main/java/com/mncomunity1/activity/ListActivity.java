package com.mncomunity1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import com.mncomunity1.adapter.ListCourseRecyclerAdapter;
import com.mncomunity1.event.ActivityResultBus;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.event.ArticlesReceivedEvent;
import com.mncomunity1.event.ArticlesRequestedEvent;
import com.mncomunity1.event.EnnigyReceivedEvent;
import com.mncomunity1.event.EnningRequestedEvent;
import com.mncomunity1.event.IsoReceivedEvent;
import com.mncomunity1.event.IsoRequestedEvent;
import com.mncomunity1.event.LogisiticsRequestedEvent;
import com.mncomunity1.event.LogisticsReceivedEvent;
import com.mncomunity1.event.MaintenanceReceivedEvent;
import com.mncomunity1.event.MaintenanceRequestedEvent;
import com.mncomunity1.event.ManagemantReceivedEvent;
import com.mncomunity1.event.ManagementRequestedEvent;
import com.mncomunity1.event.NewsReceivedEvent;
import com.mncomunity1.event.NewsRequestedEvent;
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
import com.mncomunity1.model.Post;

public class ListActivity extends AppCompatActivity {
    ProgressBar progressBar3;
    private Toolbar toolbar;
    ListCourseRecyclerAdapter myCourseRecyclerAdapter;
    ListCourseRecyclerAdapter newsRecyclerAdapter;
    ListCourseRecyclerAdapter enningyRecyclerAdapter;
    ListCourseRecyclerAdapter successRecyclerAdapter;
    ListCourseRecyclerAdapter maintenRecyclerAdapter;

    ListCourseRecyclerAdapter pdRecyclerAdapter;
    ListCourseRecyclerAdapter saRecyclerAdapter;
    ListCourseRecyclerAdapter qtRecyclerAdapter;
    ListCourseRecyclerAdapter maRecyclerAdapter;
    ListCourseRecyclerAdapter isRecyclerAdapter;
    ListCourseRecyclerAdapter pcRecyclerAdapter;
    ListCourseRecyclerAdapter whRecyclerAdapter;
    ListCourseRecyclerAdapter tipRecyclerAdapter;

    ArrayList<Post> list = new ArrayList<>();
    ArrayList<Post> listNews = new ArrayList<>();
    ArrayList<Post> listEnningy = new ArrayList<>();
    ArrayList<Post> listSuccess = new ArrayList<>();

    ArrayList<Post> listpd = new ArrayList<>();
    ArrayList<Post> listsa = new ArrayList<>();
    ArrayList<Post> listqt = new ArrayList<>();
    ArrayList<Post> listma = new ArrayList<>();
    ArrayList<Post> listis = new ArrayList<>();
    ArrayList<Post> listpc = new ArrayList<>();
    ArrayList<Post> listwh = new ArrayList<>();
    ArrayList<Post> listtip = new ArrayList<>();
    ArrayList<Post> listMainten = new ArrayList<>();
    RecyclerView recList;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.VISIBLE);
        cat = getIntent().getStringExtra("cat");
        if (cat.equals("0")) {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("News");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
            Log.e("cat", cat);
            ApiBus.getInstance().postQueue(new NewsRequestedEvent("aa"));
        }
        if (cat.equals("1")) {
            Log.e("cat", cat);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Articles");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
            ApiBus.getInstance().postQueue(new ArticlesRequestedEvent("dd"));
        }
        if (cat.equals("3")) {
            Log.e("cat", cat);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Enery Saving");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
            ApiBus.getInstance().postQueue(new EnningRequestedEvent("dd"));
        }

        if (cat.equals("5")) {
            Log.e("cat", cat);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Tip / Technique");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
            ApiBus.getInstance().postQueue(new TipRequestedEvent("dd"));
        }

        if (cat.equals("4")) {
            Log.e("cat", cat);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Success Story");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
            ApiBus.getInstance().postQueue(new SuccessRequestedEvent("aa"));
        }
        if (cat.equals("MT")) {
            ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Maintenance");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }

        if (cat.equals("PD")) {
            ApiBus.getInstance().postQueue(new ProductionRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Production");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("SA")) {
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Safety");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("QC")) {
            ApiBus.getInstance().postQueue(new QualityRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Quality");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("WH")) {
            ApiBus.getInstance().postQueue(new LogisiticsRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Warehouse and Logistics");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("MA")) {
            ApiBus.getInstance().postQueue(new ManagementRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Management");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("IS")) {
            ApiBus.getInstance().postQueue(new IsoRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("ISO");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("PC")) {
            ApiBus.getInstance().postQueue(new PurchaseRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Purchase");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
        if (cat.equals("SM")) {
            ApiBus.getInstance().postQueue(new SaleRequestedEvent());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Sale and Marketing");
                toolbar.setTitleTextColor(Color.BLACK);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }


        recList = (RecyclerView) findViewById(R.id.cardList_main);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }

    @Subscribe
    public void GetArticles(final ArticlesReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            progressBar3.setVisibility(View.GONE);
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                list.add(event.getPost());
                myCourseRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), list);
                recList.setAdapter(myCourseRecyclerAdapter);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.e("เเเเเเ", event.getPost().getPost().get(0).getTitle());
                    Intent i = new Intent(getApplicationContext(), ListLayer1Activity.class);
                    i.putExtra("link", list.get(position).getPost().get(position).getLink());
                    i.putExtra("cat", "0");
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetNews(final NewsReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listNews.add(event.getPost());
                newsRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listNews);
                recList.setAdapter(newsRecyclerAdapter);

                newsRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String link = listNews.get(position).getPost().get(position).getLink();
                        String title = listNews.get(position).getPost().get(position).getTitle();
                        Log.e("bbbb", link+title);
                        Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                        i.putExtra("link", link);
                        i.putExtra("title",title);
                        startActivity(i);
                    }
                });
            }


        }

    }

    @Subscribe
    public void GeEnningy(final EnnigyReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listEnningy.add(event.getPost());
                enningyRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listEnningy);
                recList.setAdapter(enningyRecyclerAdapter);
            }

            enningyRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listEnningy.get(position).getPost().get(position).getLink();
                    String title = listEnningy.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GeSuccess(final SuccessReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listSuccess.add(event.getPost());
                successRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listSuccess);
                recList.setAdapter(successRecyclerAdapter);
            }

            successRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listSuccess.get(position).getPost().get(position).getLink();
                    String title = listSuccess.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetMainten(final MaintenanceReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listMainten.add(event.getPost());
                maintenRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listMainten);
                recList.setAdapter(maintenRecyclerAdapter);
            }

            maintenRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listMainten.get(position).getPost().get(position).getLink();
                    String title = listMainten.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }


    }

    @Subscribe
    public void GetProduction(final ProductionReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listpd.add(event.getPost());
                pdRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listpd);
                recList.setAdapter(pdRecyclerAdapter);
            }

            pdRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listpd.get(position).getPost().get(position).getLink();
                    String title = listpd.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }

    }


    @Subscribe
    public void GetLogistics(final LogisticsReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listwh.add(event.getPost());
                whRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listwh);
                recList.setAdapter(whRecyclerAdapter);
            }
            whRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listwh.get(position).getPost().get(position).getLink();
                    String title = listwh.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });


        }

    }

    @Subscribe
    public void GetSafety(final SafetyReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listsa.add(event.getPost());
                saRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listsa);
                recList.setAdapter(saRecyclerAdapter);
            }

            saRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listsa.get(position).getPost().get(position).getLink();
                    String title = listsa.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetQuality(final QualityReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listqt.add(event.getPost());
                qtRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listqt);
                recList.setAdapter(qtRecyclerAdapter);
            }

            qtRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listqt.get(position).getPost().get(position).getLink();
                    String title = listqt.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }

    }

    @Subscribe
    public void GetManagement(final ManagemantReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listma.add(event.getPost());
                maRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listma);
                recList.setAdapter(maRecyclerAdapter);
            }

            maRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listma.get(position).getPost().get(position).getLink();
                    String title = listma.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });
        }

    }

    @Subscribe
    public void GetIso(final IsoReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listis.add(event.getPost());
                isRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listis);
                recList.setAdapter(isRecyclerAdapter);
            }
            isRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listis.get(position).getPost().get(position).getLink();
                    String title = listis.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });
        }

    }

    @Subscribe
    public void GetPurchase(final PurchaseReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listpc.add(event.getPost());
                pcRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listpc);
                recList.setAdapter(pcRecyclerAdapter);
            }

            pcRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listpc.get(position).getPost().get(position).getLink();
                    String title = listpc.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }
    }

    @Subscribe
    public void GetSale(final SaleReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listsa.add(event.getPost());
                saRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listsa);
                recList.setAdapter(saRecyclerAdapter);
            }

            saRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listsa.get(position).getPost().get(position).getLink();
                    String title = listsa.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link+title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });
        }

    }



    @Subscribe
    public void GetTip(final TipReceivedEvent event) {
        if (event != null) {
            progressBar3.setVisibility(View.GONE);
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listtip.add(event.getPost());
                tipRecyclerAdapter = new ListCourseRecyclerAdapter(getApplicationContext(), listtip);
                recList.setAdapter(tipRecyclerAdapter);
            }

            tipRecyclerAdapter.SetOnItemVideiosClickListener(new ListCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String link = listtip.get(position).getPost().get(position).getLink();
                    String title = listtip.get(position).getPost().get(position).getTitle();
                    Log.e("bbbb", link + title);
                    Intent i = new Intent(getApplicationContext(), ListWebViewActivity.class);
                    i.putExtra("link", link);
                    i.putExtra("title",title);
                    startActivity(i);
                }
            });

        }


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
