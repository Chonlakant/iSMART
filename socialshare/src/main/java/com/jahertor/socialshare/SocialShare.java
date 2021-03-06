package com.jahertor.socialshare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by javier on 24/04/15.
 */
public class SocialShare {
    private Activity activity;
    private ArrayList<Integer> socialNetworks;
    private SocialImages socialImages;

    private String subject, message, imagePath;

    /**
     * Custom social networks and custom images
     * @param activity My activity
     * @param iWantThisNetworks I want to display this list of social networks
     * @param socialImages Custom social images, see SocialImages constructor
     */
    public SocialShare(Activity activity, ArrayList<Integer> iWantThisNetworks, SocialImages socialImages) {
        this.activity = activity;
        this.socialNetworks = iWantThisNetworks;
        this.socialImages = socialImages;
    }

    /**
     * All social networks and custom images
     * @param activity My activity
     * @param socialImages Custom social images, see SocialImages constructor
     */
    public SocialShare(Activity activity, SocialImages socialImages) {
        this.activity = activity;
        this.socialImages = socialImages;
        addAllSocial();
    }

    /**
     * Custom social networks, default images
     * @param activity My activity
     * @param iWantThisNetworks I want to display this list of social networks
     */
    public SocialShare(Activity activity, ArrayList<Integer> iWantThisNetworks) {
        this.activity = activity;
        this.socialNetworks = iWantThisNetworks;
        this.socialImages = new SocialImages();
    }

    /**
     * Default constructor:
     * All networks and default images
     * @param activity My activity
     */
    public SocialShare(Activity activity) {
        this.activity = activity;
        socialImages = new SocialImages();
        addAllSocial();
    }

    private void addAllSocial() {
        this.socialNetworks = new ArrayList<>();
        this.socialNetworks.add(SocialNetwork.WHATSAPP);
        this.socialNetworks.add(SocialNetwork.FACEBOOK);
        this.socialNetworks.add(SocialNetwork.TWITTER);
        this.socialNetworks.add(SocialNetwork.INSTAGRAM);
        this.socialNetworks.add(SocialNetwork.PLUS_GOOGLE);
        this.socialNetworks.add(SocialNetwork.TELEGRAM);
        this.socialNetworks.add(SocialNetwork.GMAIL);
        this.socialNetworks.add(SocialNetwork.LINKEDIN);
        this.socialNetworks.add(SocialNetwork.VINE);
        this.socialNetworks.add(SocialNetwork.HANGOUTS);
        this.socialNetworks.add(SocialNetwork.PINTEREST);
        this.socialNetworks.add(SocialNetwork.LINE);
        this.socialNetworks.add(SocialNetwork.SNAPCHAT);
    }

    /**
     * Check what social networks has installed the user
     *
     * @return List of social Networks installed
     */
    public ArrayList<Integer> getUserNetworks() {
        final ArrayList<Integer> userNetworks = new ArrayList<>();
        // go over social networks
        for (Integer i : socialNetworks) {
            if (testById(i)) // user have this network
                userNetworks.add(i); // add it
        }
        return userNetworks;
    }

    /**
     * Display default share UI
     */
    public View getDefaultShareUI() {
        // get user networks (With id + image)
        final ArrayList<SocialNetwork> items = getSocialNetworkList();

        // build gridview
        final Resources r = activity.getResources();
        int dp64 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
        final View v = View.inflate(activity, R.layout.intent_share, null);

        final GridView gridview = (GridView) v.findViewById(R.id.grid);
        gridview.setAdapter(new SocialAdapter(activity, items, dp64));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SocialNetwork object = items.get(position);
                shareIntentById(object.getId());
            }
        });

        if (items.size() == 0)
            v.findViewById(R.id.no_results).setVisibility(View.VISIBLE);

        return v;
    }

    public ArrayList<SocialNetwork> getSocialNetworkList() {
        final ArrayList<SocialNetwork> items = new ArrayList<>();
        final ArrayList<Integer> userNetworks = getUserNetworks();
        for (Integer i : userNetworks) {
            items.add(new SocialNetwork(i, socialImages.getImageById(i)));
        }
        return items;
    }

    /****************************
     *
     * Intents for share content
     *
     ****************************/

    public void shareIntentById(int id) {
        switch (id) {
            case SocialNetwork.WHATSAPP:
                shareIntent(SocialNetwork.WHATSAPP_PACKAGE);
                break;
            case SocialNetwork.FACEBOOK:
                shareIntent(SocialNetwork.FACEBOOK_PACKAGE);
                break;
            case SocialNetwork.TWITTER:
                shareIntent(SocialNetwork.TWITTER_PACKAGE);
                break;
            case SocialNetwork.INSTAGRAM:
                shareIntent(SocialNetwork.INSTAGRAM_PACKAGE);
                break;
            case SocialNetwork.PLUS_GOOGLE:
                shareIntent(SocialNetwork.PLUS_GOOGLE_PACKAGE);
                break;
            case SocialNetwork.TELEGRAM:
                shareIntent(SocialNetwork.TELEGRAM_PACKAGE);
                break;
            case SocialNetwork.GMAIL:
                shareIntent(SocialNetwork.GMAIL_PACKAGE);
                break;
            case SocialNetwork.LINKEDIN:
                shareIntent(SocialNetwork.LINKEDIN_PACKAGE);
                break;
            case SocialNetwork.VINE:
                shareIntent(SocialNetwork.VINE_PACKAGE);
                break;
            case SocialNetwork.HANGOUTS:
                shareIntent(SocialNetwork.HANGOUTS_PACKAGE);
                break;
            case SocialNetwork.PINTEREST:
                shareIntent(SocialNetwork.PINTEREST_PACKAGE);
                break;
            case SocialNetwork.LINE:
                shareIntent(SocialNetwork.LINE_PACKAGE);
                break;
            case SocialNetwork.SNAPCHAT:
                shareIntent(SocialNetwork.SNAPCHAT_PACKAGE);
                break;
        }
    }

    public void shareIntent(final String packageName) {
        if (imagePath != null && !imagePath.isEmpty()) {
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setPackage(packageName);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (subject != null && !subject.isEmpty())
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            if (message != null && !message.isEmpty())
                intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            intent.putExtra(Intent.EXTRA_STREAM, imagePath);
            activity.startActivity(Intent.createChooser(intent ,"Share"));
        } else {
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setPackage(packageName);
            intent.setType("text/plain");
            if (subject != null && !subject.isEmpty())
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            if (message != null && !message.isEmpty())
                intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(Intent.createChooser(intent ,"Share"));
        }
    }

    /****************************
     *
     * Conditions for check if
     * social networks are installed
     *
     ****************************/

    private boolean testById(int id) {
        switch (id) {
            case SocialNetwork.WHATSAPP:
                return testSocial(SocialNetwork.WHATSAPP_PACKAGE);
            case SocialNetwork.FACEBOOK:
                return testSocial(SocialNetwork.FACEBOOK_PACKAGE);
            case SocialNetwork.TWITTER:
                return testSocial(SocialNetwork.TWITTER_PACKAGE);
            case SocialNetwork.INSTAGRAM:
                return testSocial(SocialNetwork.INSTAGRAM_PACKAGE);
            case SocialNetwork.PLUS_GOOGLE:
                return testSocial(SocialNetwork.PLUS_GOOGLE_PACKAGE);
            case SocialNetwork.TELEGRAM:
                return testSocial(SocialNetwork.TELEGRAM_PACKAGE);
            case SocialNetwork.GMAIL:
                return testSocial(SocialNetwork.GMAIL_PACKAGE);
            case SocialNetwork.LINKEDIN:
                return testSocial(SocialNetwork.LINKEDIN_PACKAGE);
            case SocialNetwork.VINE:
                return testSocial(SocialNetwork.VINE_PACKAGE);
            case SocialNetwork.HANGOUTS:
                return testSocial(SocialNetwork.HANGOUTS_PACKAGE);
            case SocialNetwork.PINTEREST:
                return testSocial(SocialNetwork.PINTEREST_PACKAGE);
            case SocialNetwork.LINE:
                return testSocial(SocialNetwork.LINE_PACKAGE);
            case SocialNetwork.SNAPCHAT:
                return testSocial(SocialNetwork.SNAPCHAT_PACKAGE);
            default:
                return false;
        }
    }
    private boolean testSocial(String packageName) {
        try{
            activity.getPackageManager().
                    getApplicationInfo(packageName, 0 );
            return true;
        } catch( PackageManager.NameNotFoundException e ){
            return false;
        }
    }


    /************
     * GETTERS & SETTERS
     ************/
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
