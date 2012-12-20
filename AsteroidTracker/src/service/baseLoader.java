/*
 * @(#)baseLoader
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

import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class baseLoader extends AsyncTaskLoader<List>{

    public baseLoader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List loadInBackground() {
        return null;
    }

}
