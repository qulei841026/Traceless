package com.assassin.traceless.core.external;

import android.util.Log;

import com.assassin.traceless.annotations.weaving.Burying;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BuryingDispatcher
 * Created by Le-q on 2018/3/19.
 */
public class BuryingDispatcher {

    private static final String BURYING_PREFIX = "Burying";
    private static final String SCHEMA_SUFFIX = "Schema";
    private static final String ASSIST_SUFFIX = "Assist";

    private static final String TAG = "BuryingDispatcher";

    private static final Map<String, Object> schemas = new LinkedHashMap<>();
    private static final Map<String, BuryingTransfer> assists = new LinkedHashMap<>();

    public static void dispatcher(final ProceedingJoinPoint joinPoint, Burying burying) {
        Log.d(TAG, "[dispatcher]->" + joinPoint.getSignature().toString());

        String assistName = BURYING_PREFIX + burying.name() + ASSIST_SUFFIX;
        String schemaName = BURYING_PREFIX + burying.name() + SCHEMA_SUFFIX;

        try {
            BuryingTransfer transfer = assists.get(assistName);
            if (transfer == null) {
                Class<?> xClass = Class.forName(assistName);
                transfer = (BuryingTransfer) xClass.newInstance();
                assists.put(assistName, transfer);
            }

            Object schema = schemas.get(schemaName);

            if (schema == null) {
                schema = burying.clazz().newInstance();
                schemas.put(schemaName, schema);
            }

            if (transfer != null && schema != null) {
                transfer.process(joinPoint.getTarget(), joinPoint.getSignature().toString(),
                        joinPoint.getArgs(), schema);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
