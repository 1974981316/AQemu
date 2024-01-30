package com.swk.aqemu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.swk.aqemu.databinding.FragmentImageFactoryBinding;

public class ImageFactoryFragment extends Fragment {
    FragmentImageFactoryBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        
        binding = FragmentImageFactoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
  
}