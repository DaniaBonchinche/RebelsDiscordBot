package com.yuziak;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "RebelsDisBot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) {
        try {
            updateMember("Yuzless", "Guardian", 305, 380, 370, null, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static int getUserPos(String name) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1iy1WQleEMfo_GRXpwrEzf_RtWcxFrvE-p0AKUU1IsRY";

        final String range = "Восставшие!A2:K";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
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

    public static String updateMember(String name, String gameClass, Integer ap, Integer def, Integer accuracy, String role, Integer horseDef, String ckrockType, String horseType) throws IOException, GeneralSecurityException {
        String pattern = "dd.MM.yyyy";
        String updateDate = new SimpleDateFormat(pattern).format(new Date());

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1iy1WQleEMfo_GRXpwrEzf_RtWcxFrvE-p0AKUU1IsRY";

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        int userPos = getUserPos(name);
        if (userPos == 0) {
            return ("User not found");
        } else {
            System.out.println(userPos);
            List<List<Object>> val = Arrays.asList(
                    Arrays.asList(name, gameClass, ap, def, role, updateDate, horseDef, horseType, accuracy)
            );
            ValueRange body9 = new ValueRange()
                    .setValues(val);
            UpdateValuesResponse result12 =
                    service.spreadsheets().values().update(spreadsheetId, "A" + userPos, body9)
                            .setValueInputOption("USER_ENTERED")
                            .execute();

            List<ValueRange> data = new ArrayList<>();
            data.add(new ValueRange().setRange("A" + userPos).setValues(Arrays.asList(Arrays.asList(name))));
            data.add(new ValueRange().setRange("B" + userPos).setValues(Arrays.asList(Arrays.asList(gameClass))));
            data.add(new ValueRange().setRange("C" + userPos).setValues(Arrays.asList(Arrays.asList(ap))));
            data.add(new ValueRange().setRange("D" + userPos).setValues(Arrays.asList(Arrays.asList(def))));
            data.add(new ValueRange().setRange("F" + userPos).setValues(Arrays.asList(Arrays.asList(role))));
            data.add(new ValueRange().setRange("G" + userPos).setValues(Arrays.asList(Arrays.asList(updateDate))));
            data.add(new ValueRange().setRange("H" + userPos).setValues(Arrays.asList(Arrays.asList(horseDef))));
            data.add(new ValueRange().setRange("I" + userPos).setValues(Arrays.asList(Arrays.asList(ckrockType))));
            data.add(new ValueRange().setRange("J" + userPos).setValues(Arrays.asList(Arrays.asList(horseType))));
            data.add(new ValueRange().setRange("K" + userPos).setValues(Arrays.asList(Arrays.asList(accuracy))));

            BatchUpdateValuesRequest body = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(data);
            BatchUpdateValuesResponse result =
                    service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
            System.out.printf("%d cells updated.", result.getTotalUpdatedCells());
            return ("Successful update");
        }
    }
}