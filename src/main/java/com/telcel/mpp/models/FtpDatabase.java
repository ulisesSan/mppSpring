package com.telcel.mpp.models;

import lombok.Data;

@Data
public class FtpDatabase {
        private String uuid;
        private String docName;
        private String ftpRoute;
        private String uploadDate;
        private String milestoneName;
        private String progressPercentage;
}
