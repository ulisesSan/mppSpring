package com.telcel.mpp.models;

import lombok.Data;

@Data
public class MppModel {
    private String taskName;
    private String startDate;
    private String endDate;
    private String duration;
    private String percentageComplete;
    private String predecessor;
    private String predecessors;
    private Integer hierarchyLevel;
}
