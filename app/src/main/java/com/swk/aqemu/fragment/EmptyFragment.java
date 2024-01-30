package com.swk.aqemu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.color.MaterialColors;
import com.swk.aqemu.databinding.FragmentEmptyBinding;

public class EmptyFragment extends Fragment {
    FragmentEmptyBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        
        binding = FragmentEmptyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    
}