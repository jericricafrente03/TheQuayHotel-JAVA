package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Activity;


import android.content.ComponentName;
import android.content.Intent;


import androidx.fragment.app.Fragment;

import com.ph.bittelasia.Model.VideoInfo;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshListItemClickedListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannel;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.Model.MeshArrayListCallBack;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment.TheQuayChannelFragment;

import java.util.ArrayList;

@Layout(R.layout.sg_activity_tv)
@ActivitySetting()
public class TheQuayTV extends TheQuayActivity implements MeshListItemClickedListener, MeshArrayListCallBack<MeshChannel>
{

    //====================================Variable==================================================
    //------------------------------------Constant--------------------------------------------------
    public static final String TAG  = TheQuayTV.class.getSimpleName();
    public  static ArrayList<MeshChannel>        channelList;
    //----------------------------------------------------------------------------------------------
    //------------------------------------Instance--------------------------------------------------
    private  TheQuayChannelFragment       channelFragment;
    private  int                          channelID=-1;
    private   ArrayList<VideoInfo>        list;

    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //===================================Life Cycle=================================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){}
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //======================================TheQuayActivity=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getActivityName() {
        return TAG;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=====================================Fragments================================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_main,tag = "channels",order=1)
    public Fragment attachChannels()
    {
        try {
            channelFragment = new TheQuayChannelFragment();
            channelFragment.setClickedListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return channelFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //===============================MeshListItemClickedListener====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClicked(Object o) {
        try {
            if (o instanceof MeshChannel) {

                MeshChannel channel = (MeshChannel) o;
//            Intent intent = new Intent(this, TheQuayPlayerActivity.class);
//            intent.putExtra(TheQuayPlayerActivity.URI,channel.getChannel_uri());
//            intent.putExtra(TheQuayPlayerActivity.TITLE,channel.getChannel_title());
//            intent.putExtra(TheQuayPlayerActivity.ID,channel.getId());
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivityForResult(intent,100);
                channelID = channel.getId();

                if (channel.getChannel_title() != null) {
                    final Intent intent = new Intent(this, SantagrandPlayerActivity.class);
                    intent.putExtra(SantagrandPlayerActivity.URI, channel.getChannel_uri());
                    intent.putExtra(SantagrandPlayerActivity.TITLE, channel.getChannel_title());
                    intent.putExtra(SantagrandPlayerActivity.ID, channel.getId());
                    startActivity(intent);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //===================================MeshArrayListCallBack======================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void meshArrayList(ArrayList<MeshChannel> list) {
        try {
            if (list != null && list.size() > 0) {
                if (channelList == null)
                    channelList = new ArrayList<>();
                else
                    channelList.clear();
                for (MeshChannel ch : list) {
                    if (channelID == ch.getId()) {
//                    Intent intent = new Intent(this, TheQuayPlayerActivity.class);
//                    intent.putExtra(TheQuayPlayerActivity.URI,ch.getChannel_uri());
//                    intent.putExtra(TheQuayPlayerActivity.TITLE,ch.getChannel_title());
//                    intent.putExtra(TheQuayPlayerActivity.ID,ch.getId());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivityForResult(intent,100);
                        if (ch.getChannel_title() != null) {
                            final Intent intent = new Intent(this, SantagrandPlayerActivity.class);
                            intent.putExtra(SantagrandPlayerActivity.URI, ch.getChannel_uri());
                            intent.putExtra(SantagrandPlayerActivity.TITLE, ch.getChannel_title());
                            intent.putExtra(SantagrandPlayerActivity.ID, ch.getId());
                            startActivity(intent);
                        }
                        channelID = ch.getId();
                        break;
                    }
                    channelList.add(ch);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
