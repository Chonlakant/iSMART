package ismart.is.com.ismart.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;

import ismart.is.com.ismart.R;
import ismart.is.com.ismart.adapter.AllCourseRecyclerAdapter;
import ismart.is.com.ismart.adapter.CourseListviewAdapter;
import ismart.is.com.ismart.event.ActivityResultBus;
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

public class AllCourseActivity extends AppCompatActivity {

    CourseListviewAdapter courseListviewAdapter;
    private Toolbar toolbar;
    AllCourseRecyclerAdapter myCourseRecyclerAdapter;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterProduction;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterMain;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterSafetyt;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterQuality;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterLogistics;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterManagement;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterIso;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterSale;
    AllCourseRecyclerAdapter myCourseRecyclerAdapterPurchase;
    ArrayList<Post> list = new ArrayList<>();
    ArrayList<Post> listProduct = new ArrayList<>();
    ArrayList<Post> listMian = new ArrayList<>();
    ArrayList<Post> listSafetyt = new ArrayList<>();
    ArrayList<Post> listQuality = new ArrayList<>();
    ArrayList<Post> listLogistics = new ArrayList<>();
    ArrayList<Post> listManagement = new ArrayList<>();
    ArrayList<Post> listIso = new ArrayList<>();
    ArrayList<Post> listPurchase = new ArrayList<>();
    ArrayList<Post> listSale = new ArrayList<>();
    RecyclerView recList, cardList_production, cardList_safety, cardList_quality, cardList_logistics, cardList_management, cardList_iso, cardList_purchase, cardList;
    TextView see_more_main, see_more_production, see_more_safety, see_more_quaity, see_more_logictics, see_more_management, see_more_iso,
            see_more_purchase, see_more_sale;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }
    String cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cat = getIntent().getStringExtra("cat");
        if(cat.equals("2")){
            ApiBus.getInstance().postQueue(new LogisiticsRequestedEvent());
            ApiBus.getInstance().postQueue(new QualityRequestedEvent());
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
            ApiBus.getInstance().postQueue(new ProductionRequestedEvent());
            ApiBus.getInstance().postQueue(new MaintenanceRequestedEvent());
            ApiBus.getInstance().postQueue(new ManagementRequestedEvent());
            ApiBus.getInstance().postQueue(new IsoRequestedEvent());
            ApiBus.getInstance().postQueue(new PurchaseRequestedEvent());
            ApiBus.getInstance().postQueue(new SafetyRequestedEvent());
            ApiBus.getInstance().postQueue(new SaleRequestedEvent());
        }


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("All Course");
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
        recList = (RecyclerView) findViewById(R.id.cardList_main);
        cardList_production = (RecyclerView) findViewById(R.id.cardList_production);
        cardList_safety = (RecyclerView) findViewById(R.id.cardList_safety);
        cardList_quality = (RecyclerView) findViewById(R.id.cardList_quality);
        cardList_logistics = (RecyclerView) findViewById(R.id.cardList_logistics);
        cardList_management = (RecyclerView) findViewById(R.id.cardList_management);
        cardList_iso = (RecyclerView) findViewById(R.id.cardList_iso);
        cardList_purchase = (RecyclerView) findViewById(R.id.cardList_purchase);
        cardList = (RecyclerView) findViewById(R.id.cardList);

        see_more_main = (TextView) findViewById(R.id.see_more_main);
        see_more_production = (TextView) findViewById(R.id.see_more_production);
        see_more_safety = (TextView) findViewById(R.id.see_more_safety);
        see_more_quaity = (TextView) findViewById(R.id.see_more_quaity);
        see_more_logictics = (TextView) findViewById(R.id.see_more_logictics);
        see_more_management = (TextView) findViewById(R.id.see_more_management);
        see_more_iso = (TextView) findViewById(R.id.see_more_iso);
        see_more_purchase = (TextView) findViewById(R.id.see_more_purchase);
        see_more_sale = (TextView) findViewById(R.id.see_more_sale);


        recList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager7 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager8 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recList.setLayoutManager(layoutManager);
        cardList_production.setLayoutManager(layoutManager1);
        cardList_safety.setLayoutManager(layoutManager2);
        cardList_quality.setLayoutManager(layoutManager3);
        cardList_logistics.setLayoutManager(layoutManager4);
        cardList_management.setLayoutManager(layoutManager5);
        cardList.setLayoutManager(layoutManager6);
        cardList_purchase.setLayoutManager(layoutManager7);
        cardList_iso.setLayoutManager(layoutManager8);

        myCourseRecyclerAdapter = new AllCourseRecyclerAdapter(getApplicationContext(), list);
        myCourseRecyclerAdapterProduction = new AllCourseRecyclerAdapter(getApplicationContext(), listProduct);

        myCourseRecyclerAdapterProduction = new AllCourseRecyclerAdapter(getApplicationContext(), listProduct);
        myCourseRecyclerAdapterSafetyt = new AllCourseRecyclerAdapter(getApplicationContext(), listSafetyt);
        myCourseRecyclerAdapterQuality = new AllCourseRecyclerAdapter(getApplicationContext(), listQuality);
        myCourseRecyclerAdapterLogistics = new AllCourseRecyclerAdapter(getApplicationContext(), listLogistics);
        myCourseRecyclerAdapterManagement = new AllCourseRecyclerAdapter(getApplicationContext(), listManagement);
        myCourseRecyclerAdapterIso = new AllCourseRecyclerAdapter(getApplicationContext(), listIso);
        myCourseRecyclerAdapterSale = new AllCourseRecyclerAdapter(getApplicationContext(), listSale);
        myCourseRecyclerAdapterMain = new AllCourseRecyclerAdapter(getApplicationContext(), listMian);
        myCourseRecyclerAdapterPurchase = new AllCourseRecyclerAdapter(getApplicationContext(), listPurchase);

        myCourseRecyclerAdapterProduction.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("listProduct", Parcels.wrap(listProduct));
                startActivity(i);
            }
        });

        see_more_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "0");
                startActivity(i);
            }
        });

        see_more_production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
            }
        });

        see_more_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "2");
                startActivity(i);
            }
        });

        see_more_quaity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "3");
                startActivity(i);
            }
        });

        see_more_logictics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "4");
                startActivity(i);
            }
        });

        see_more_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "5");
                startActivity(i);
            }
        });

        see_more_iso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "6");
                startActivity(i);
            }
        });

        see_more_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "7");
                startActivity(i);
            }
        });

        see_more_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeeAllCourseActivity.class);
                i.putExtra("key", "8");
                startActivity(i);
            }
        });

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
    public void GetLogistics(final LogisticsReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listLogistics.add(event.getPost());
                cardList_logistics.setAdapter(myCourseRecyclerAdapterLogistics);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetQuality(final QualityReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listQuality.add(event.getPost());
                cardList_quality.setAdapter(myCourseRecyclerAdapterQuality);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetSafety(final SafetyReceivedEvent event) {
        if (event != null) {
            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listSafetyt.add(event.getPost());
                cardList_safety.setAdapter(myCourseRecyclerAdapterSafetyt);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetProduction(final ProductionReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listProduct.add(event.getPost());
                cardList_production.setAdapter(myCourseRecyclerAdapterProduction);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetMainten(final MaintenanceReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listMian.add(event.getPost());
                recList.setAdapter(myCourseRecyclerAdapterMain);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetManagement(final ManagemantReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listManagement.add(event.getPost());
                cardList_management.setAdapter(myCourseRecyclerAdapterManagement);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetIso(final IsoReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listIso.add(event.getPost());
                cardList_iso.setAdapter(myCourseRecyclerAdapterIso);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetPurchase(final PurchaseReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listPurchase.add(event.getPost());
                cardList_purchase.setAdapter(myCourseRecyclerAdapterPurchase);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }

    @Subscribe
    public void GetSale(final SaleReceivedEvent event) {
        if (event != null) {
            Log.e("bbbb", event.getPost().getPost().get(0).getTitle());

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                listSale.add(event.getPost());
                cardList.setAdapter(myCourseRecyclerAdapterSale);
            }

            myCourseRecyclerAdapter.SetOnItemVideiosClickListener(new AllCourseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
