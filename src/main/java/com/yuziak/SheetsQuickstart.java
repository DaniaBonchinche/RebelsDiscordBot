package com.yuziak;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "RebelsDisBot";

    private static final String CREDENTIALS_FILE_PATH_SERV_ACC = "/serviceAccount.json";

    final static public String sheetRange = "BotTest!";

    private static Set<String> googleOAuth2Scopes() {
        Set<String> googleOAuth2Scopes = new HashSet<>();
        googleOAuth2Scopes.add(SheetsScopes.SPREADSHEETS);
        return Collections.unmodifiableSet(googleOAuth2Scopes);
    }

    private static Sheets getSheets() throws IOException, GeneralSecurityException {
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH_SERV_ACC);
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), GoogleCredential.fromStream(in).createScoped(googleOAuth2Scopes()))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static int getUserPos(String name) throws IOException, GeneralSecurityException {
        final String spreadsheetId = "1iy1WQleEMfo_GRXpwrEzf_RtWcxFrvE-p0AKUU1IsRY";

        final String range = sheetRange + "B2:K";
        Sheets service = getSheets();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        int userPos = Integer.MAX_VALUE;
        int tempPos = 2;
        if (values == null || values.isEmpty()) {
            return 0;
        } else {
            for (List row : values) {
                if (row.get(0).toString().equals(name)) {
                    userPos = tempPos;
                } else {
                    tempPos++;
                }
            }
        }
        if (userPos == Integer.MAX_VALUE) {
            return 0;
        } else {
            return userPos;
        }
    }

    public static String updateMember(String name, String gameClass, Integer ap, Integer def, Integer accuracy, Integer horseDef, String ckrockType, String horseType) throws IOException, GeneralSecurityException {
        String pattern = "dd.MM.yyyy hh:mm:ss";
        String updateDate = new SimpleDateFormat(pattern).format(new Date());

        final String spreadsheetId = "1iy1WQleEMfo_GRXpwrEzf_RtWcxFrvE-p0AKUU1IsRY";

        Sheets service = getSheets();
        int userPos = getUserPos(name);
        if (userPos == 0) {
            return ("User not found");
        } else {
            List<ValueRange> data = new ArrayList<>();
            data.add(new ValueRange().setRange(sheetRange + "B" + userPos).setValues(Arrays.asList(Arrays.asList(name))));
            data.add(new ValueRange().setRange(sheetRange + "C" + userPos).setValues(Arrays.asList(Arrays.asList(gameClass))));
            try {
                if (ap <= 100 || ap > 330) {
                    return "ApNull";
                }
            } catch (NullPointerException e) {
            }
            data.add(new ValueRange().setRange(sheetRange + "D" + userPos).setValues(Arrays.asList(Arrays.asList(ap))));
            try {
                if (def <= 100 || def > 600) {
                    return "DefNull";
                }
            } catch (NullPointerException e) {
            }
            data.add(new ValueRange().setRange(sheetRange + "F" + userPos).setValues(Arrays.asList(Arrays.asList(def))));
            data.add(new ValueRange().setRange(sheetRange + "A" + userPos).setValues(Arrays.asList(Arrays.asList(updateDate))));
            try {
                if (horseDef <= 0 || horseDef > 250) {
                    return "HorseDefNull";
                }
            } catch (NullPointerException e) {
            }
            data.add(new ValueRange().setRange(sheetRange + "H" + userPos).setValues(Arrays.asList(Arrays.asList(horseDef))));
            data.add(new ValueRange().setRange(sheetRange + "I" + userPos).setValues(Arrays.asList(Arrays.asList(ckrockType))));
            data.add(new ValueRange().setRange(sheetRange + "J" + userPos).setValues(Arrays.asList(Arrays.asList(horseType))));
            try {
                if (accuracy <= 250 || accuracy > 500) {
                    return "AccuracyNull";
                }
            } catch (NullPointerException e) {
            }
            data.add(new ValueRange().setRange(sheetRange + "E" + userPos).setValues(Arrays.asList(Arrays.asList(accuracy))));
            BatchUpdateValuesRequest body = new BatchUpdateValuesRequest()
                    .setValueInputOption("RAW")
                    .setData(data);
            BatchUpdateValuesResponse result =
                    service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
            System.out.println(userPos);
            System.out.printf("%d cells updated.", result.getTotalUpdatedCells());
            return ("Successful update");
        }
    }
}