package com.iot.smarthome;

/**
 * Created by Marco on 11/12/2016.
 */

public class Constants {

    public static final String GET_LIST_ITEMS = "get_list_items";
    public static final int GOOGLE_API_CLIENT_TIMEOUT_S = 100; // 10 seconds
    public static final String GOOGLE_API_CLIENT_ERROR_MSG =
            "Failed to connect to GoogleApiClient (error code = %d)";

    // Max # of attractions to show at once
    public static final int MAX_ITEMS = 4;


    // Resize images sent to Wear to 400x400px
    public static final int WEAR_IMAGE_SIZE = 400;

    // Except images that can be set as a background with parallax, set width 640x instead
    public static final int WEAR_IMAGE_SIZE_PARALLAX_WIDTH = 640;


    // The minimum bottom inset percent to use on a round screen device
    public static final float WEAR_ROUND_MIN_INSET_PERCENT = 0.08f;

    public static final String STATUS = "status";

    public static final String LIGHT_STATUS = "/light-status";
    public static final String CHANGE_LIGHT_STATUS = "/change-light-status";


    public static final String EXTRA_STATUS = "extra_status";
    public static final String REQUEST_MESSAGE = "/light";

    public static final String ACTION_TEXT_CHANGED = "changed";
    public static final String BROADCAST_STATUS = "broadcast_status";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final String ON = "On";
    public static final String OFF = "Off";
}
