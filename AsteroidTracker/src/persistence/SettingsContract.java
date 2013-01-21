/*
 * @(#)SettingsContract
 *
 * Copyright 2013 by Constant Contact Inc.,
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
package persistence;

import android.provider.BaseColumns;

public abstract class SettingsContract  implements BaseColumns {

    public static final String TABLE_NAME = "settings";
    public static final String COLUMN_NAME_SEETING_ID = "setting_id";
    public static final int COLUMN_NOTIFICATIONS_ENABLED = 1;

    public SettingsContract(){};
}
