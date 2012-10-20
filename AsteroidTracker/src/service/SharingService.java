/*
 * @(#)SharingService
 *
 * Copyright 2012 by Constant Contact Inc.,
 * Waltham, MA 02451, USA
 * Phone: (781) 472-8100
 * Fax: (781) 472-8101
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Constant Contact, Inc. created for Constant Contact, Inc.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Constant Contact, Inc.
 * 
 * History
 *
 * Date         Author      Comments
 * ====         ======      ========
 *
 * 
 **/
package service;

import android.content.Intent;
import android.net.Uri;

public class SharingService {

    public Intent createShareIntent(String headline, String message)
    {

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, headline);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        return intent;
    }

    public Intent createShareIntent() {
        Intent I= new Intent(Intent.ACTION_SEND);
        I.setType("text/plain");
        I.putExtra(android.content.Intent.EXTRA_SUBJECT, "TEST - Disregard");
        I.putExtra(android.content.Intent.EXTRA_TEXT, Uri.parse("http://test.com"));
        return I;
    }
}
