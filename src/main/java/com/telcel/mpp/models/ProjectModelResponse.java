package com.telcel.mpp.models;

import java.util.List;

import lombok.Data;

@Data
public class ProjectModelResponse {
        private List<MppModel> mpp;
        private double persentageComplete;
}
