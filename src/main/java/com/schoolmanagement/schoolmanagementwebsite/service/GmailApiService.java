// // package com.schoolmanagement.schoolmanagementwebsite.service;

// // import com.google.api.client.auth.oauth2.Credential;
// // import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
// // import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
// // import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// // import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
// // import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
// // import com.google.api.client.http.javanet.NetHttpTransport;
// // import com.google.api.client.json.gson.GsonFactory;
// // import com.google.api.client.util.store.FileDataStoreFactory;

// // import com.google.api.services.gmail.Gmail;
// // import com.google.api.services.gmail.GmailScopes;
// // import com.google.api.services.gmail.model.Message;

// // import jakarta.mail.Session;
// // import jakarta.mail.internet.InternetAddress;
// // import jakarta.mail.internet.MimeMessage;

// // import org.apache.commons.codec.binary.Base64;
// // import org.springframework.stereotype.Service;

// // import java.io.ByteArrayOutputStream;
// // import java.io.File;
// // import java.io.InputStream;
// // import java.io.InputStreamReader;
// // import java.util.Collections;
// // import java.util.List;
// // import java.util.Properties;

// // @Service
// // public class GmailApiService {

// //     private static final String APPLICATION_NAME =
// //             "ZYNTaks Education";

// //     private static final GsonFactory JSON_FACTORY =
// //             GsonFactory.getDefaultInstance();

// //     private static final List<String> SCOPES =
// //             Collections.singletonList(GmailScopes.GMAIL_SEND);

// //     private static final String CREDENTIALS_FILE_PATH =
// //             "/credentials.json";

// //     private Gmail getGmailService() throws Exception {

// //         final NetHttpTransport httpTransport =
// //                 GoogleNetHttpTransport.newTrustedTransport();

// //         InputStream inputStream =
// //                 GmailApiService.class
// //                         .getResourceAsStream(CREDENTIALS_FILE_PATH);

// //         if (inputStream == null) {
// //             throw new RuntimeException(
// //                     "credentials.json not found in src/main/resources"
// //             );
// //         }

// //         GoogleClientSecrets clientSecrets =
// //                 GoogleClientSecrets.load(
// //                         JSON_FACTORY,
// //                         new InputStreamReader(inputStream)
// //                 );

// //         File tokenDirectory =
// //                 new File("tokens");

// //         GoogleAuthorizationCodeFlow flow =
// //                 new GoogleAuthorizationCodeFlow.Builder(
// //                         httpTransport,
// //                         JSON_FACTORY,
// //                         clientSecrets,
// //                         SCOPES
// //                 )
// //                         .setDataStoreFactory(
// //                                 new FileDataStoreFactory(tokenDirectory)
// //                         )
// //                         .setAccessType("offline")
// //                         .build();

// //         LocalServerReceiver receiver =
// //         new LocalServerReceiver.Builder()
// //                 .setPort(8889)
// //                 .build();

// //         Credential credential =
// //                 new AuthorizationCodeInstalledApp(
// //                         flow,
// //                         receiver
// //                 ).authorize("user");

// //         return new Gmail.Builder(
// //                 httpTransport,
// //                 JSON_FACTORY,
// //                 credential
// //         )
// //                 .setApplicationName(APPLICATION_NAME)
// //                 .build();
// //     }

// //     public void sendEmail(
// //             String to,
// //             String subject,
// //             String body
// //     ) throws Exception {

// //         Gmail gmailService = getGmailService();

// //         Properties properties = new Properties();

// //         Session session =
// //                 Session.getInstance(properties, null);

// //         MimeMessage email =
// //                 new MimeMessage(session);

// //         email.setFrom(
// //                 new InternetAddress(
// //                         "zyntakseducation@gmail.com"
// //                 )
// //         );

// //         email.addRecipient(
// //                 jakarta.mail.Message.RecipientType.TO,
// //                 new InternetAddress(to)
// //         );

// //         email.setSubject(subject);

// //         email.setText(body);

// //         ByteArrayOutputStream buffer =
// //                 new ByteArrayOutputStream();

// //         email.writeTo(buffer);

// //         byte[] rawMessageBytes =
// //                 buffer.toByteArray();

// //         String encodedEmail =
// //                 Base64.encodeBase64URLSafeString(
// //                         rawMessageBytes
// //                 );

// //         Message message =
// //                 new Message();

// //         message.setRaw(encodedEmail);

// //         gmailService
// //                 .users()
// //                 .messages()
// //                 .send("me", message)
// //                 .execute();
// //     }
// // }


// package com.schoolmanagement.schoolmanagementwebsite.service;

// import com.google.api.client.auth.oauth2.Credential;
// import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
// import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
// import com.google.api.client.http.javanet.NetHttpTransport;
// import com.google.api.client.json.gson.GsonFactory;
// import com.google.api.client.util.store.FileDataStoreFactory;

// import com.google.api.services.gmail.Gmail;
// import com.google.api.services.gmail.GmailScopes;
// import com.google.api.services.gmail.model.Message;

// import jakarta.mail.Session;
// import jakarta.mail.internet.InternetAddress;
// import jakarta.mail.internet.MimeMessage;

// import org.apache.commons.codec.binary.Base64;
// import org.springframework.stereotype.Service;

// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.util.Collections;
// import java.util.List;
// import java.util.Properties;

// @Service
// public class GmailApiService {

//     private static final String APPLICATION_NAME =
//             "ZYNTaks Education";

//     private static final GsonFactory JSON_FACTORY =
//             GsonFactory.getDefaultInstance();

//     private static final List<String> SCOPES =
//             Collections.singletonList(
//                     GmailScopes.GMAIL_SEND
//             );

//     private static final String CREDENTIALS_FILE_PATH =
//             "/credentials.json";

//     // =====================================================
//     // GET GMAIL SERVICE
//     // =====================================================

//     private Gmail getGmailService() throws Exception {

//         // -------------------------------------------------
//         // 1. Google HTTP Transport
//         // -------------------------------------------------

//         final NetHttpTransport httpTransport =
//                 GoogleNetHttpTransport.newTrustedTransport();

//         // -------------------------------------------------
//         // 2. Load credentials.json
//         // -------------------------------------------------

//         InputStream inputStream =
//                 GmailApiService.class
//                         .getResourceAsStream(
//                                 CREDENTIALS_FILE_PATH
//                         );

//         if (inputStream == null) {

//             throw new RuntimeException(
//                     "credentials.json not found in src/main/resources"
//             );
//         }

//         GoogleClientSecrets clientSecrets =
//                 GoogleClientSecrets.load(
//                         JSON_FACTORY,
//                         new InputStreamReader(inputStream)
//                 );

//         // -------------------------------------------------
//         // 3. Token directory
//         // -------------------------------------------------

//         File tokenDirectory =
//                 new File("tokens");

//         if (!tokenDirectory.exists()) {
//             tokenDirectory.mkdirs();
//         }

//         // -------------------------------------------------
//         // 4. OAuth Flow
//         // -------------------------------------------------

//         GoogleAuthorizationCodeFlow flow =
//                 new GoogleAuthorizationCodeFlow.Builder(
//                         httpTransport,
//                         JSON_FACTORY,
//                         clientSecrets,
//                         SCOPES
//                 )
//                         .setDataStoreFactory(
//                                 new FileDataStoreFactory(
//                                         tokenDirectory
//                                 )
//                         )
//                         .setAccessType("offline")
//                         .build();

//         // -------------------------------------------------
//         // 5. LOAD EXISTING SAVED CREDENTIAL
//         // -------------------------------------------------

//         Credential credential = flow.loadCredential("user");

// System.out.println("GMAIL CREDENTIAL = " + credential);

//         if (credential == null) {

//             throw new RuntimeException(
//                     "Gmail OAuth credential not found. " +
//                     "Please authorize Gmail API once."
//             );
//         }

//         // -------------------------------------------------
//         // 6. Refresh access token if required
//         // -------------------------------------------------

//         if (credential.getExpiresInSeconds() != null
//                 && credential.getExpiresInSeconds() <= 60) {

//             boolean refreshed =
//                     credential.refreshToken();

//             if (!refreshed) {

//                 throw new RuntimeException(
//                         "Unable to refresh Gmail OAuth token."
//                 );
//             }
//         }

//         // -------------------------------------------------
//         // 7. Create Gmail Service
//         // -------------------------------------------------

//         return new Gmail.Builder(
//                 httpTransport,
//                 JSON_FACTORY,
//                 credential
//         )
//                 .setApplicationName(
//                         APPLICATION_NAME
//                 )
//                 .build();
//     }

//     // =====================================================
//     // SEND EMAIL
//     // =====================================================

//     public void sendEmail(
//             String to,
//             String subject,
//             String body
//     ) throws Exception {

//         // -------------------------------------------------
//         // Get Gmail API service
//         // -------------------------------------------------

//         Gmail gmailService =
//                 getGmailService();

//         // -------------------------------------------------
//         // Create MIME email
//         // -------------------------------------------------

//         Properties properties =
//                 new Properties();

//         Session session =
//                 Session.getInstance(
//                         properties,
//                         null
//                 );

//         MimeMessage email =
//                 new MimeMessage(session);

//         // -------------------------------------------------
//         // Sender
//         // -------------------------------------------------

//         email.setFrom(
//                 new InternetAddress(
//                         "zyntakseducation@gmail.com"
//                 )
//         );

//         // -------------------------------------------------
//         // Receiver
//         // -------------------------------------------------

//         email.addRecipient(
//                 jakarta.mail.Message.RecipientType.TO,
//                 new InternetAddress(to)
//         );

//         // -------------------------------------------------
//         // Subject
//         // -------------------------------------------------

//         email.setSubject(subject);

//         // -------------------------------------------------
//         // Body
//         // -------------------------------------------------

//         email.setText(body);

//         // -------------------------------------------------
//         // Convert email to bytes
//         // -------------------------------------------------

//         ByteArrayOutputStream buffer =
//                 new ByteArrayOutputStream();

//         email.writeTo(buffer);

//         byte[] rawMessageBytes =
//                 buffer.toByteArray();

//         // -------------------------------------------------
//         // Gmail requires Base64 URL-safe encoding
//         // -------------------------------------------------

//         String encodedEmail =
//                 Base64.encodeBase64URLSafeString(
//                         rawMessageBytes
//                 );

//         // -------------------------------------------------
//         // Gmail Message
//         // -------------------------------------------------

//         Message message =
//                 new Message();

//         message.setRaw(encodedEmail);

//         // -------------------------------------------------
//         // SEND
//         // -------------------------------------------------

//         gmailService
//                 .users()
//                 .messages()
//                 .send(
//                         "me",
//                         message
//                 )
//                 .execute();
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class GmailApiService {

    private static final String APPLICATION_NAME =
            "ZYNTaks Education";

    private static final GsonFactory JSON_FACTORY =
            GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES =
            Collections.singletonList(GmailScopes.GMAIL_SEND);

    private static final String CREDENTIALS_FILE_PATH =
            "/credentials.json";

    private Gmail getGmailService() throws Exception {

        final NetHttpTransport httpTransport =
                GoogleNetHttpTransport.newTrustedTransport();

        InputStream inputStream =
                GmailApiService.class
                        .getResourceAsStream(CREDENTIALS_FILE_PATH);

        if (inputStream == null) {
            throw new RuntimeException(
                    "credentials.json not found in src/main/resources"
            );
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JSON_FACTORY,
                        new InputStreamReader(inputStream)
                );

        // Always use project root/tokens
        File tokenDirectory =
                new File(
                        System.getProperty("user.dir"),
                        "tokens"
                );

        System.out.println(
                "GMAIL TOKEN DIRECTORY = "
                        + tokenDirectory.getAbsolutePath()
        );

        if (!tokenDirectory.exists()) {
            boolean created = tokenDirectory.mkdirs();

            System.out.println(
                    "TOKEN DIRECTORY CREATED = " + created
            );
        }

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        JSON_FACTORY,
                        clientSecrets,
                        SCOPES
                )
                        .setDataStoreFactory(
                                new FileDataStoreFactory(
                                        tokenDirectory
                                )
                        )
                        .setAccessType("offline")
                        .build();

        // First try existing credential
        Credential credential =
                flow.loadCredential("user");

        System.out.println(
                "GMAIL CREDENTIAL = " + credential
        );
        System.out.println("REFRESH TOKEN = " + credential.getRefreshToken());
        System.out.println(
        "REFRESH TOKEN EXISTS = "
                + (credential != null
                && credential.getRefreshToken() != null)
);

        // If credential does not exist, authorize ONCE
        if (credential == null) {

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "GMAIL OAUTH AUTHORIZATION REQUIRED"
            );

            System.out.println(
                    "Opening Google authorization..."
            );

            System.out.println(
                    "=========================================="
            );
            

            LocalServerReceiver receiver =
                    new LocalServerReceiver.Builder()
                            .setPort(8889)
                            .build();

            credential =
                    new AuthorizationCodeInstalledApp(
                            flow,
                            receiver
                    ).authorize("user");

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "GMAIL OAUTH AUTHORIZATION SUCCESSFUL"
            );

            System.out.println(
                    "Credential saved in: "
                            + tokenDirectory.getAbsolutePath()
            );

            System.out.println(
                    "=========================================="
            );
        }

        // Refresh access token when required
        if (credential.getExpiresInSeconds() != null
                && credential.getExpiresInSeconds() <= 60) {

            boolean refreshed =
                    credential.refreshToken();

            if (!refreshed) {
                throw new RuntimeException(
                        "Unable to refresh Gmail OAuth token."
                );
            }
        }

        return new Gmail.Builder(
                httpTransport,
                JSON_FACTORY,
                credential
        )
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void sendEmail(
            String to,
            String subject,
            String body
    ) throws Exception {

        Gmail gmailService =
                getGmailService();

        Properties properties =
                new Properties();

        Session session =
                Session.getInstance(
                        properties,
                        null
                );

        MimeMessage email =
                new MimeMessage(session);

        email.setFrom(
                new InternetAddress(
                        "zyntakseducation@gmail.com"
                )
        );

        email.addRecipient(
                jakarta.mail.Message.RecipientType.TO,
                new InternetAddress(to)
        );

        email.setSubject(subject);

        email.setText(body);

        ByteArrayOutputStream buffer =
                new ByteArrayOutputStream();

        email.writeTo(buffer);

        byte[] rawMessageBytes =
                buffer.toByteArray();

        String encodedEmail =
                Base64.encodeBase64URLSafeString(
                        rawMessageBytes
                );

        Message message =
                new Message();

        message.setRaw(encodedEmail);

        gmailService
                .users()
                .messages()
                .send("me", message)
                .execute();

        System.out.println(
                "GMAIL EMAIL SENT SUCCESSFULLY TO = "
                        + to
        );
    }
}