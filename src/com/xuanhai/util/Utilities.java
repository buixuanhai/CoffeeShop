/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.util;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Utilities {

    public static Logger getLogger() {
        return LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
    
    public static void Log(Level level, String message){
        getLogger().log(level, message);
    }
}
