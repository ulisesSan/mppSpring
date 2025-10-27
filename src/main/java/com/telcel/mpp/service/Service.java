package com.telcel.mpp.service;

import java.io.InputStream;

public interface Service {

    void ReadMpp();

    void uploadDocument(InputStream inputStream);
}
