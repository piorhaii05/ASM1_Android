package com.example.shopping_online.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopping_online.Fragment.Cart_Fragment;
import com.example.shopping_online.Fragment.Contact_Fragment;
import com.example.shopping_online.Fragment.Home_Fragment;
import com.example.shopping_online.Fragment.ListLike_Fragment;

import org.checkerframework.checker.units.qual.C;

// Thiết lập Adapter extends FragmentStateAdapter
public class ViewPager_Adapter extends FragmentStateAdapter {
    public Home_Fragment homeFragment;
    public ListLike_Fragment listLikeFragment;
    public Cart_Fragment cartFragment;
    public Contact_Fragment contactFragment;

    public ViewPager_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        homeFragment = new Home_Fragment();
        listLikeFragment = new ListLike_Fragment();
        cartFragment = new Cart_Fragment();
        contactFragment = new Contact_Fragment();
    }

    // Trả về số thứ tự của các Fragment
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return homeFragment;
            case 1:
                return listLikeFragment;
            case 2:
                return cartFragment;
            case 3:
                return contactFragment;
            default:
                return homeFragment;
        }
    }

    // Trả về số fragment có trên tablayout
    @Override
    public int getItemCount() {
        return 4;
    }

}
