package com.andr.view.frament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/8/6
 *     desc  : new class
 * </pre>
 */
public abstract class BaseLazyLoadFragment extends Fragment
{
    View rootView;

    /**当前Fragment是否首次可见，默认是首次可见**/
    private boolean mIsFirstVisible = true;
    /**当前Fragment的View是否已经创建**/
    private boolean isViewCreated = false;
    /**当前Fragment的可见状态，一种当前可见，一种当前不可见**/
    private boolean currentVisibleState = false;
//    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);

        }
//        unbinder = ButterKnife.bind(this, rootView);
        isViewCreated=true;//在onCreateView执行完毕，将isViewCreated改为true;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView(rootView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {//Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
                disPatchFragment(true);
            } else if (!isVisibleToUser && currentVisibleState) {//Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
                disPatchFragment(false);
            }
        }
    }



    /**返回子Fragment的布局id**/
    public abstract  int getLayoutId();

    /**初始化View的方法**/
    public abstract  void initView(View rootView);




    @Override
    public void onStart() {
        super.onStart();

        //isHidden()是Fragment是否处于隐藏状态和isVisible()有区别
        //getUserVisibleHint(),Fragement是否可见
        if(!isHidden()&& getUserVisibleHint()){//如果Fragment没有隐藏且可见
            //执行分发的方法,三种结果对应自Fragment的三个回调，对应的操作，Fragment首次加载，可见，不可见
            disPatchFragment(true);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if(!mIsFirstVisible){
            //表示点击home键又返回操作,设置可见状态为ture
            if(!isHidden()&& !getUserVisibleHint() && currentVisibleState){
                disPatchFragment(true);
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if(!isHidden()&& getUserVisibleHint()){
            disPatchFragment(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false;
        mIsFirstVisible = true;
//        unbinder.unbind();
    }


    /**
     *
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    public  void   disPatchFragment(boolean visible){
        String aa =getClass().getSimpleName();
        //如果父Fragment不可见,则不向下分发给子Fragment
        if(visible && isParentFragmentVsible())return;

        // 如果当前的 Fragment 要分发的状态与 currentVisibleState 相同(都为false)我们就没有必要去做分发了。
        if (currentVisibleState == visible) {
            return;
        }

        currentVisibleState=visible;
        if(visible){//Fragment可见
            if(mIsFirstVisible){//可见又是第一次
                mIsFirstVisible=false;//改变首次可见的状态
                onFragmentFirst();
            }//可见但不是第一次
            onFragmentVisble();
            //可见状态的时候内层 fragment 生命周期晚于外层 所以在 onFragmentResume 后分发
            dispatchChildFragmentVisibleState(true);
        }else {//不可见
            onFragmentInVisible();
            dispatchChildFragmentVisibleState(false);
        }
    };


    /**
     * 重新分发给子Fragment
     * @param visible
     */
    private void dispatchChildFragmentVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = childFragmentManager.getFragments();
        if(fragments != null){
            if (!fragments.isEmpty()) {
                for (Fragment child : fragments) {
                    if (child instanceof BaseLazyLoadFragment && !child.isHidden() && child.getUserVisibleHint()) {
                        ((BaseLazyLoadFragment) child).disPatchFragment(visible);
                    }
                }
            }
        }

    }

    //Fragemnet首次可见的方法
    public abstract void onFragmentFirst();
    //Fragemnet可见的方法
    public abstract void onFragmentVisble();
    //Fragemnet不可见的方法
    public abstract void onFragmentInVisible();


    /**
     *判断多层嵌套的父Fragment是否显示
     */
    private boolean isParentFragmentVsible() {
        BaseLazyLoadFragment fragment = (BaseLazyLoadFragment) getParentFragment();
        return fragment != null && !fragment.getCurrentVisibleState();
    }

    private boolean getCurrentVisibleState() {
        return currentVisibleState;
    }
}
