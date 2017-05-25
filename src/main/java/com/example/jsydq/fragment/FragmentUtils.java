package com.example.jsydq.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lionheart on 2016/6/3.
 */
public class FragmentUtils {


    public static Map<Integer,BaseFragment> fragments = new HashMap<>();
    public static BaseFragment getFragment(int position){
        BaseFragment baseFragment = fragments.get(position);
        if(baseFragment==null){
            if(position==0){
                baseFragment =new  ShelfFragment();
            }else if(position ==1){
                baseFragment =new CityFragment();
            }else if(position ==2){
                baseFragment =new  FindFragment();
            }
            fragments.put(position,baseFragment);
        }
        return baseFragment;
    }
}
