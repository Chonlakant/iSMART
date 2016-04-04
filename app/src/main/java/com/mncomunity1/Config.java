package com.mncomunity1;

public interface Config {

	
	// CONSTANTS
	static final String YOUR_SERVER_URL =  "http://mn-community.com/web/register.php";
	
	static final String YOUR_SERVER_URL_CHECK =  "http://mn-community.com/web/check_register.php";
	
	 static final String SERVER_ACICLE =  "http://ipro-training.com/";
	 static final String SERVER_COM =  "http://mn-community.com/web/";
	
	// YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
    static final String GOOGLE_SENDER_ID = "423097242723";  // Place here your Google project id

    /**
     * Tag used on log messages.
     */
    //static final String TAG = "GCM Android Example";
    static final String TAG = "PMII";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.mncomunity1.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
  //  static final String EXTRA_MESSAGE = "price";
    
    static final String EXTRA_CODE = "code";
		
	
}
