package com.smartcity.qiuchenly.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcity.qiuchenly.Base.SharedContext;
import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.DataModel.userManageModel;
import com.smartcity.qiuchenly.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: qiuchenly
 * Date   : 09/11/2017
 * Usage :
 * Lasted:2017 11 09
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 09 , on 23:23
 */

public class mContentRecyclerViewAdapter extends
        RecyclerView.Adapter<mContentRecyclerViewAdapter.VH> implements View.OnClickListener, View
        .OnLongClickListener {

  userManageModel lists;

  Context con;

  //开始设计点击checkBox保存数据
  Map<Integer, Boolean> checkBoxItems;

  //设置接口实例
  private onItemClickListener onItemClickListener;

  public void addListData(userManageModel lists) {
    this.lists=lists;
  }

  public mContentRecyclerViewAdapter(userManageModel lists) {
    this.lists = lists;
    con = SharedContext.getContext();
    checkBoxInit();
  }

  void checkBoxInit() {
    checkBoxItems = new HashMap<>();
    //默认全部取消选中
    for (int a = 0; a < lists.data.length; a++) {
      checkBoxItems.put(a, false);
    }
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanage_item, null);
    v.setOnClickListener(this);
    v.setOnLongClickListener(this);
    VH vh = new VH(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(final VH h, final int p) {
    userManageModel.datas user = lists.data[p];
    h.index.setText(p + 1 + "");//fix INDEX bug
    h.payMent.setText("充值");
    h.carID.setText(user.carID);
    h.carMaster.setText("车主:" + user.carMaster);
    h.total.setText("余额:" + user.totalMoney + "元"+(user.totalMoney < Utils.getMoneyLimitValue()
            ?"\n[余额不足]":""));
    h.total.setTextColor(Color.parseColor((user.totalMoney >= Utils.getMoneyLimitValue()
            ?"#FFFFFF":"#FFCC00")));
    h.user_manage_index_image.setImageBitmap(getCarImg(user.carTypePic));
    h.selectPayAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBoxItems.put(h.getAdapterPosition(), isChecked);
      }
    });
    h.root.setTag(p);
    if (checkBoxItems.get(p) == null) {
      checkBoxItems.put(p, false);
    }
    h.selectPayAll.setChecked(checkBoxItems.get(p));
  }

  Bitmap getCarImg(int a) {
    int id;
    switch (a) {
      case 1:
        id = R.drawable.bwm;
        break;
      case 2:
        id = R.drawable.china;
        break;
      case 3:
        id = R.drawable.sanling;
        break;
      case 4:
        id = R.drawable.mazoa;
        break;
      default:
        //一般不会出现这种情况，后端如果数据出错此处做容错处理。
        id = R.drawable.bwm;
        break;
    }
    InputStream stream = con.getResources().openRawResource(id);
    Bitmap bit = BitmapFactory.decodeStream(stream);
    return bit;
  }


  @Override
  public int getItemCount() {
    return lists.data.length;
  }

  @Override
  public void onClick(View v) {
    onItemClickListener.setOnClickListener(v, (Integer) v.getTag());
  }

  public void setItemChecked(int position) {
    if (checkBoxItems.get(position)) {
      checkBoxItems.put(position, false);
    } else {
      checkBoxItems.put(position, true);
    }
    notifyItemChanged(position);
  }

  @Override
  public boolean onLongClick(View v) {
    checkBoxInit();
    onItemClickListener.setOnLongClickListener(v, (Integer) v.getTag());
    this.notifyDataSetChanged();
    return true;
  }

  class VH extends RecyclerView.ViewHolder {
    ImageView user_manage_index_image;
    TextView carID, carMaster, index, total;
    CheckBox selectPayAll;
    Button payMent;
    View root;

    public VH(View v) {
      super(v);
      root = v;
      total = v.findViewById(R.id.user_manage_items_total);
      user_manage_index_image = v.findViewById(R.id.user_manage_items_ico);
      carID = v.findViewById(R.id.user_manage_items_carID);
      carMaster = v.findViewById(R.id.user_manage_items_carUser);
      index = v.findViewById(R.id.user_manage_items_index);
      selectPayAll = v.findViewById(R.id.user_manage_items_check);
      payMent = v.findViewById(R.id.user_manage_items_payment);
    }
  }


  public void setOnItemClickListener(onItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }


  public interface onItemClickListener {
    void setOnClickListener(View v, int position);

    void setOnLongClickListener(View v, int position);
  }

}
