package com.example.personal.diapositivasapp;

/**
 * Created by Sianna-chan on 26/01/2017.
 */

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * An activity to illustrate how to pick a file with the
 * opener intent.
 */
public class PickFileWithOpenerActivity extends BaseDemoActivity {

    private static final String TAG = "PickFile";

    private static final int REQUEST_CODE_OPENER = 1;

    private GoogleApiClient mGoogleApiClient;
    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[] { "text/plain", "text/html","application/vnd.google-apps.presentation" })
                .build(getGoogleApiClient());

        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (SendIntentException e) {
            Log.w(TAG, "Unable to send intent", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    showMessage("Selected file's ID: " + driveId.getResourceId());
                    Log.e("ID FILE: ", driveId.toString());
                    /*DriveFile file = Drive.DriveApi.getFile(mGoogleApiClient,
                            DriveId.decodeFromString(driveId.toString()));
                    file.getMetadata(mGoogleApiClient).setResultCallback(metadataRetrievedCallback);*/

                    DriveFile file = driveId.asDriveFile();
                    file.getMetadata(getGoogleApiClient())
                            .setResultCallback(metadataCallback);
                }
                //finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    final private ResultCallback<DriveResource.MetadataResult> metadataCallback = new
            ResultCallback<DriveResource.MetadataResult>() {
                @Override
                public void onResult(DriveResource.MetadataResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while trying to fetch metadata");
                        return;
                    }
                    Metadata metadata = result.getMetadata();
                    showMessage("Metadata successfully fetched. Title: " + metadata.getTitle());
                }
            };

}