package com.schoolmanagement.schoolmanagementwebsite.backup;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackupScheduler {

    private final DatabaseBackupService backupService;

    public BackupScheduler(DatabaseBackupService backupService) {
        this.backupService = backupService;
    }

    /**
     * Daily backup
     *
     * 02:00 AM IST
     *
     * Spring server timezone is explicitly set to Asia/Kolkata.
     */
    // @Scheduled(
    //         cron = "0 0 2 * * *",
    //         zone = "Asia/Kolkata"
    // )
    @Scheduled(
        cron = "0 */5 * * * *",
        zone = "Asia/Kolkata"
)
    public void dailyBackup() {

        System.out.println(
                "⏰ Daily backup scheduler triggered..."
        );

        backupService.createBackup();
    }
}