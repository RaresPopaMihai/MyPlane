package com.example.myplane.fragments;

import androidx.fragment.app.Fragment;

public abstract class AbstractFragment extends Fragment {

    protected String URL_STRING;

    public abstract void searchRelevantInformation(String search);
}
