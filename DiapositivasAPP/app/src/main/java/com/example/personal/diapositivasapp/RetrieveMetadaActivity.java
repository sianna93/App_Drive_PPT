package com.example.personal.diapositivasapp;

/**
 * Created by Sianna-chan on 26/01/2017.
 */

import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;


/**
 * An activity to retrieve the metadata of a file.
 */
public class RetrieveMetadaActivity extends BaseDemoActivity {

    public static final String EXISTING_FILE_ID =         "CAESLDFlOGZCT2FSVU5KTjJBaUtiQ2hCZ1NESGNid2NrM1Y2OWRMclpOcXZKT2RnGI4sIOb4weOsVigA";
    public static final String EXISTING_FILE_ID_RESORCE = "CAESLDFlOGZCT2FSVU5KTjJBaUtiQ2hCZ1NESGNid2NrM1Y2OWRMclpOcXZKT2RnGI4sIOb4weOsVigA";
    public static final String EXISTING_FILE_ID2 = "CAESLDFTejhJVWlWcktMR1h5MXBSVWtudW5BM1hST0RyVnU4bFR5ZXVMUkl0Q0JFGIYCIOb4weOsVigA";
    @Override
    public void onConnected(Bundle connectionHint) {
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), EXISTING_FILE_ID)
                .setResultCallback(idCallback);
    }

    final private ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFile file = driveId.asDriveFile();
            file.getMetadata(getGoogleApiClient())
                    .setResultCallback(metadataCallback);
        }
    };

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